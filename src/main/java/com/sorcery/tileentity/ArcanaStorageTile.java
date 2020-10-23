package com.sorcery.tileentity;

import com.sorcery.Sorcery;
import com.sorcery.arcana.ArcanaStorage;
import com.sorcery.arcana.IArcanaStorage;
import com.sorcery.particle.ParticleEffectContext;
import com.sorcery.particle.ParticleEffects;
import com.sorcery.particle.Particles;
import com.sorcery.utils.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.LongArrayNBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class ArcanaStorageTile extends TileEntity implements ITickableTileEntity
{

    // static vars
    protected ArcanaStorage arcanaStorage = new ArcanaStorage(10000);
    protected int transferRate = 1000;
    protected int arcanaTransferTicks = 20;
    // -- the max distance this tile will interact with other
    public double interactionRange = 8;
    // -- where the pulse should start from in relation to blockpos
    protected Vector3d arcanaPulseOffset = new Vector3d(0.5, 1, 0.5);
    // -- to offset processing, so all tiles aren't synced
    protected int worldTickOffset = 0;


    // data to maintain, other than arcana cap data
    // -- BlockPositions of all other arcana storage tiles
    protected Set<BlockPos> otherArcanaStorageTiles = new HashSet<>();
    // -- BlockPositions of interfering arcanaStorageTiles
    protected Set<BlockPos> negativeInterferingTiles = new HashSet<>();
    protected Set<BlockPos> positiveInterferingTiles = new HashSet<>();
    // -- BlockPosition of current transfer target
    protected BlockPos arcanaTransferTargetPos = null;
    // -- BlockPositions of tiles sending arcana to this one, needed for load order management and graph traversal
    protected Set<BlockPos> arcanaTransferSources = new HashSet<>();
    // -- BlockPosition of tile overriding interference
    protected BlockPos tileOverridingInterference = null;
    // -- BlockPositions of tiles in Grid
    protected Set<BlockPos> gridMonoliths = new HashSet<>();

    protected boolean firstTick = true;



    // Ephemeral vars, only after first tick
    protected boolean interference = false;

    protected ArcanaStorageTile arcanaTransferTarget = null;
    protected Vector3d arcanaPulseTarget = null;


    public ArcanaStorageTile(TileEntityType<?> tileEntityTypeIn)
    {
        super(tileEntityTypeIn);
    }

    @Override
    public void tick()
    {
        // First tick
        if (this.firstTick)
        {
            this.handleFirstTick();
            this.firstTick = false;
        }

        long worldTicks = this.getOffsetWorldTicks();

        // Shared Stuff
        if (worldTicks % 10 == 0)
        {
            if (this.arcanaTransferTargetPos != null && this.arcanaTransferTarget == null)
            {
                this.updateArcanaTransferTarget();
            }
        }

        // Client Side Stuff, mostly particle handling
        if (world.isRemote)
        {
            if (worldTicks % 10 == 0)
            {
                if (this.arcanaPulseTarget != null && !this.beingInterfered())
                {
                    ParticleEffects.sendTo(new ParticleEffectContext(world, Particles.getParticleSet(11, 40), this.getOwnPulseTarget(), this.arcanaPulseTarget, 1, 1, 0, 40));
                }
            }
        }

        // Server side stuff, arcana sending etc.
        if (!world.isRemote)
        {
            if (worldTicks % this.arcanaTransferTicks == 0)
            {
                if (this.arcanaTransferTarget != null && !this.beingInterfered())
                {
                    int arcanaReceived = this.arcanaTransferTarget.receiveArcana(Math.min(this.transferRate, this.getStoredArcana()));
                    this.extractArcana(arcanaReceived);
                }
            }
        }
    }

    // to stagger processing
    public long getOffsetWorldTicks()
    {
        return this.world.getGameTime() + this.worldTickOffset;
    }

    // first thing to do once loaded and in world
    private void handleFirstTick()
    {
        // update interference status and surrounding other tiles
        this.updateTilesInRange();
        this.updateInterference();
        this.updateArcanaTransferTarget();
    }


    // Called when TE is being removed
    @Override
    public void remove()
    {
        // Call remove other tile on every other acrcana storage tile in range
        Sorcery.getLogger().debug("REMOVING TILE");
        Sorcery.getLogger().debug("Arcana Sources:" + this.arcanaTransferSources);
        Sorcery.getLogger().debug("Grid Monos:" + this.gridMonoliths);
        this.updateTilesInRange();
        BlockPos pos = this.getPos();
        if (this.gridMonoliths.contains(pos))
        {
            this.removeGrid();
        }
        for (BlockPos otherPos : this.otherArcanaStorageTiles)
        {
            ((ArcanaStorageTile)this.world.getTileEntity(otherPos)).removeOtherTile(pos);
        }
        super.remove();
    }

    private void updateTilesInRange()
    {
        Set<ArcanaStorageTile> tilesInRange = Utils.getTEInRange(this.world, this, ArcanaStorageTile.class, this.interactionRange);
        for (ArcanaStorageTile tile : tilesInRange)
        {
            this.otherArcanaStorageTiles.add(tile.pos);
            tile.addOtherTile(this.pos);
        }
    }

    private void updateInterference()
    {
        for (BlockPos pos : this.otherArcanaStorageTiles)
        {
            ArcanaStorageTile otherTile = (ArcanaStorageTile)this.world.getTileEntity(pos);
            if (otherTile == null)
            {
                return;
            }
            int interference = otherTile.checkInterference(this.pos);
            int intForOther = this.checkInterference(this.pos);
            if (interference == -1)
            {
                this.negativeInterferingTiles.add(pos);
            } else if (interference == 1)
            {
                this.positiveInterferingTiles.add(pos);
            }
            if (intForOther == -1)
            {
                otherTile.addInterferingTile(this.pos);
             } else if (intForOther == 1)
            {
                otherTile.positiveInterferingTiles.add(this.pos);
            }
        }
        this.interference = !this.negativeInterferingTiles.isEmpty();
    }

    /*
    Check to see if this tile will cause interference at the listed block pos
    Interference rules:
    1) Interference is one way
    2) Interference is based only on position
    3) Interferecence happens only horizontally
     */
    public int checkInterference(BlockPos pos)
    {
        return 0;
    }

    public void addInterferingTile(BlockPos pos)
    {
        this.negativeInterferingTiles.add(pos);
        this.interference = true;
    }

    public void addInterferenceOverride(BlockPos pos)
    {
        this.tileOverridingInterference = pos;
        this.updateAndMarkDirty();
    }

    public void removeInterferenceOverride()
    {
        this.tileOverridingInterference = null;
        this.updateAndMarkDirty();
    }

    public boolean beingInterfered()
    {
        if (this.tileOverridingInterference != null)
        {
            return false;
        } else {
            return this.interference;
        }
    }


    public void addArcanaTransferTarget(BlockPos pos)
    {
        this.arcanaTransferTargetPos = pos;
        this.updateArcanaTransferTarget();
        ((ArcanaStorageTile)this.world.getTileEntity(pos)).addTransferSource(this.pos);
        this.updateAndMarkDirty();
    }

    public void removeArcanaTransferTarget(BlockPos pos)
    {
        this.arcanaTransferTargetPos = null;
        ((ArcanaStorageTile)this.world.getTileEntity(pos)).removeTransferSource(this.pos);
        this.updateArcanaTransferTarget();
        this.updateAndMarkDirty();
    }

    private void updateArcanaTransferTarget()
    {
        if (this.arcanaTransferTargetPos != null)
        {
            if (this.world != null)
            {
                this.arcanaTransferTarget = (ArcanaStorageTile)this.world.getTileEntity(this.arcanaTransferTargetPos);
                if (this.arcanaTransferTarget != null)
                {
                    this.arcanaPulseTarget = this.arcanaTransferTarget.getOwnPulseTarget();
                }
            }
        } else {
            this.arcanaTransferTarget = null;
            this.arcanaPulseTarget = null;
        }
    }

    public void addTransferSource(BlockPos pos)
    {
        this.arcanaTransferSources.add(pos);
        this.updateAndMarkDirty();
    }

    public void removeTransferSource(BlockPos pos)
    {
        this.arcanaTransferSources.remove(pos);
        this.updateAndMarkDirty();
    }

    public void pingTransferSources()
    {
        try
        {
            for (BlockPos pos : this.arcanaTransferSources)
            {
                ((ArcanaStorageTile) this.world.getTileEntity(pos)).updateArcanaTransferTarget();
            }
        } catch (NullPointerException exception)
        {
            Sorcery.getLogger().debug("Transfer source not yet loaded");
        }
    }


    public Vector3d getOwnPulseTarget()
    {
        Vector3d basePos = new Vector3d(this.pos.getX(), this.pos.getY(), this.pos.getZ());
        return basePos.add(this.arcanaPulseOffset);
    }

    public void addOtherTile(BlockPos pos)
    {
        this.otherArcanaStorageTiles.add(pos);
        this.updateInterference();
        this.updateAndMarkDirty();
    }

   public void removeOtherTile(BlockPos pos)
   {
       Sorcery.getLogger().debug("Removing other tile at: " + pos);
       if (this.gridMonoliths.contains(pos))
       {
           this.removeGrid();
       }
       this.otherArcanaStorageTiles.remove(pos);
       this.negativeInterferingTiles.remove(pos);
       this.arcanaTransferSources.remove(pos);
       Sorcery.getLogger().debug("Checking transfer target");
       Sorcery.getLogger().debug("own TransferTargetPos= " + this.arcanaTransferTargetPos);
       Sorcery.getLogger().debug("incoming Pos= " + pos);
       // Equals doesn't work here, will look into more later
       if (this.arcanaTransferTargetPos.withinDistance(pos, 0.2))
       {
           Sorcery.getLogger().debug("Was arcana transfer target, removing");
           this.arcanaTransferTargetPos = null;
           this.arcanaTransferTarget = null;
           this.arcanaPulseTarget = null;
       }
       if (this.tileOverridingInterference == pos)
       {
           this.tileOverridingInterference = null;
       }
       this.interference = !this.negativeInterferingTiles.isEmpty();
       this.updateAndMarkDirty();
   }

   private void removeGrid()
   {
       for (BlockPos pos : this.gridMonoliths)
       {
           try
           {
               ((ArcanaStorageTile) this.world.getTileEntity(pos)).removeInterferenceOverride();
           } catch (NullPointerException e)
           {
               Sorcery.getLogger().debug("Exception in remove grid, probably harmless");
           }
       }
   }


    // Serialize transfer data
    public CompoundNBT writeTransferTag()
    {
        CompoundNBT tag = new CompoundNBT();

        // otherArcanaStorageTiles
        LongArrayNBT otherTilesNBT = Utils.blockPosSetToLongArray(this.otherArcanaStorageTiles);
        tag.put("OtherTiles", otherTilesNBT);

        // arcanaTransferTarget
        if (this.arcanaTransferTargetPos != null)
        {
            LongNBT transferTargetNBT = LongNBT.valueOf(this.arcanaTransferTargetPos.toLong());
            tag.put("TransferTarget", transferTargetNBT);
        }
        // interferingTiles
        LongArrayNBT negInterferingTilesNBT = Utils.blockPosSetToLongArray(this.negativeInterferingTiles);
        tag.put("NegInterferingTiles", negInterferingTilesNBT);

        LongArrayNBT posInterferingTilesNBT = Utils.blockPosSetToLongArray(this.positiveInterferingTiles);
        tag.put("PosInterferingTiles", posInterferingTilesNBT);

        LongArrayNBT transferSourcesNBT = Utils.blockPosSetToLongArray(this.arcanaTransferSources);
        tag.put("TransferSources", transferSourcesNBT);

        // interferenceOverride
        if (this.tileOverridingInterference != null)
        {
            LongNBT overrideNBT = LongNBT.valueOf(this.tileOverridingInterference.toLong());
            tag.put("Override", overrideNBT);
        }

        LongArrayNBT gridNBT = Utils.blockPosSetToLongArray(this.gridMonoliths);
        tag.put("GridMonos", gridNBT);

        return tag;
    }

    public void readTransferTag(CompoundNBT nbt)
    {
        if (nbt.contains("OtherTiles"))
        {
            this.otherArcanaStorageTiles = Utils.longArrayToBlockPosSet((LongArrayNBT)nbt.get("OtherTiles"));
        }
        if (nbt.contains("TransferTarget"))
        {
            BlockPos pos = BlockPos.fromLong(((LongNBT)nbt.get("TransferTarget")).getLong());
            this.arcanaTransferTargetPos = pos;
        } else {
            this.arcanaTransferTargetPos = null;
        }
        this.updateArcanaTransferTarget();
        if (nbt.contains("NegInterferingTiles"))
        {
            this.negativeInterferingTiles = Utils.longArrayToBlockPosSet((LongArrayNBT)nbt.get("NegInterferingTiles"));
        }
        if (nbt.contains("PosInterferingTiles"))
        {
            this.positiveInterferingTiles = Utils.longArrayToBlockPosSet((LongArrayNBT)nbt.get("PosInterferingTiles"));
        }

        if (nbt.contains("TransferSources"))
        {
            this.arcanaTransferSources = Utils.longArrayToBlockPosSet((LongArrayNBT)nbt.get("TransferSources"));
            this.pingTransferSources();
        }
        if (nbt.contains("Override"))
        {
            this.tileOverridingInterference = BlockPos.fromLong(((LongNBT)nbt.get("Override")).getLong());
        } else {
            this.tileOverridingInterference = null;
        }

        if (nbt.contains("GridMonos"))
        {
            this.gridMonoliths = Utils.longArrayToBlockPosSet((LongArrayNBT)nbt.get("GridMonos"));
        }
    }


    // Sync client
    // tag received by client
    public void handleUpdateTag(CompoundNBT tag)
    {
        if (tag.contains("tData"))
        {
            this.readTransferTag(tag.getCompound("tData"));
        }
        if (tag.contains("aData"))
        {
            this.arcanaStorage.deserializeNBT(tag.getCompound("aData"));
        }
    }

    // get tag to send client
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = this.write(new CompoundNBT());
        nbt.put("tData", this.writeTransferTag());
        nbt.put("aData", this.arcanaStorage.serializeNBT());
        return nbt;
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 3, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getNbtCompound());
    }

    // Read from NBT from disk
    @Override
    public void read(BlockState blockState, CompoundNBT nbt)
    {
        super.read(blockState, nbt);

        // Transfer Data
        if (nbt.contains("tData"))
        {
            this.readTransferTag(nbt.getCompound("tData"));
        }
        // Arcana Data
        if (nbt.contains("aData"))
        {
            this.arcanaStorage.deserializeNBT(nbt.getCompound("aData"));
        }
    }

    // write to NBT for disk
    @Override
    public CompoundNBT write(CompoundNBT tag)
    {
        CompoundNBT nbt = super.write(tag);
        // Transfer Data
        nbt.put("tData", this.writeTransferTag());
        // Arcana Data
        nbt.put("aData", this.arcanaStorage.serializeNBT());
        return nbt;
    }

    // Update and Save
    public void updateAndMarkDirty()
    {
        if (!this.world.isRemote())
        {
            this.markDirty();
            this.world.notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }


    // Arcana Capability Handling
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        if (cap instanceof IArcanaStorage)
        {
            return LazyOptional.of(() -> arcanaStorage).cast();
        }
        return super.getCapability(cap, side);
    }

    public int receiveArcana(int arcana)
    {
        int spareArcana = this.arcanaStorage.receiveArcana(arcana, false);
        this.updateAndMarkDirty();
        return spareArcana;
    }

    public int getStoredArcana()
    {
        return this.arcanaStorage.getArcanaStored();
    }

    public int extractArcana(int arcana)
    {
        if (this.beingInterfered())
        {
            return 0;
        }
        int arcanaExtracted = this.arcanaStorage.extractArcana(arcana, false);
        this.updateAndMarkDirty();
        return arcanaExtracted;
    }

    public int getMaxArcana()
    {
        return this.arcanaStorage.getMaxArcanaStored();
    }

    /*
    DONT USE THIS IT DOESN'T DO WHAT YOU THINK IT DOES
     */
    @Override
    public void onLoad()
    {
        // Check Interference State
        this.worldTickOffset = this.world.rand.nextInt(20);
        super.onLoad();
    }
}
