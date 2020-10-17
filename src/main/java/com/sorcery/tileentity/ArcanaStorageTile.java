package com.sorcery.tileentity;

import com.sorcery.arcana.ArcanaStorage;
import com.sorcery.arcana.IArcanaStorage;
import com.sorcery.particle.ParticleEffectContext;
import com.sorcery.particle.ParticleEffects;
import com.sorcery.particle.Particles;
import com.sorcery.utils.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.*;
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
import java.util.*;

public class ArcanaStorageTile extends TileEntity implements ITickableTileEntity
{

    // static vars
    protected ArcanaStorage arcanaStorage = new ArcanaStorage(10000);
    protected int transferRate = 100;
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
    protected Set<BlockPos> interferingTiles = new HashSet<>();
    // -- BlockPosition of current transfer target
    protected BlockPos arcanaTransferTargetPos = null;

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

        // Client Side Stuff, mostly particle handling
        if (world.isRemote)
        {
            if (worldTicks % 40 == 0)
            {
                if (this.arcanaPulseTarget != null)
                {
                    ParticleEffects.arcanaPulse(new ParticleEffectContext(world, Particles.getArcanaOrbs(), this.getOwnPulseTarget(), this.arcanaPulseTarget, 1, 1, 0, 40));
                }
            }
        }

        // Server side stuff, arcana sending etc.
        if (!world.isRemote)
        {
            if (worldTicks % 10 == 0)
            {
                if (this.arcanaTransferTarget != null)
                {
                    int arcanaReceived = this.arcanaTransferTarget.receiveArcana(this.transferRate);
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
        for (BlockPos pos : this.otherArcanaStorageTiles)
        {
            ((ArcanaStorageTile)this.world.getTileEntity(pos)).removeOtherTile(this.pos);
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
            if (otherTile.checkInterference(this.pos))
            {
                this.interferingTiles.add(pos);
            }
            if (this.checkInterference(pos))
            {
                otherTile.addInterferingTile(this.pos);
            }
        }
        this.interference = !this.interferingTiles.isEmpty();
    }

    /*
    Check to see if this tile will cause interference at the listed block pos
    Interference rules:
    1) Interference is one way
    2) Interference is based only on position
    3) Interferecence happens only horizontally
     */
    public boolean checkInterference(BlockPos pos)
    {
        return false;
    }

    public void addInterferingTile(BlockPos pos)
    {
        this.interferingTiles.add(pos);
        this.interference = true;
    }

    public void addArcanaTransferTarget(BlockPos pos)
    {
        this.arcanaTransferTargetPos = pos;
        this.updateArcanaTransferTarget();
        this.updateAndMarkDirty();
    }

    public void removeArcanaTransferTarget()
    {
        this.arcanaTransferTargetPos = null;
        this.updateArcanaTransferTarget();
        this.updateAndMarkDirty();
    }

    private void updateArcanaTransferTarget()
    {
        if (this.arcanaTransferTargetPos != null)
        {
            this.arcanaTransferTarget = (ArcanaStorageTile)this.world.getTileEntity(this.arcanaTransferTargetPos);
            this.arcanaPulseTarget = this.arcanaTransferTarget.getOwnPulseTarget();
        } else {
            this.arcanaTransferTarget = null;
            this.arcanaPulseTarget = null;
        }
        this.updateAndMarkDirty();
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
       this.otherArcanaStorageTiles.remove(pos);
       this.interferingTiles.remove(pos);
       if (this.arcanaTransferTargetPos == pos)
       {
           this.arcanaTransferTargetPos = null;
           this.arcanaTransferTarget = null;
       }
       this.interference = !this.interferingTiles.isEmpty();
       this.updateAndMarkDirty();
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
        LongArrayNBT interferingTilesNBT = Utils.blockPosSetToLongArray(this.interferingTiles);
        tag.put("InterferingTiles", interferingTilesNBT);

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
            this.arcanaTransferTargetPos = BlockPos.fromLong(((LongNBT)nbt.get("TransferTarget")).getLong());
        }
        if (nbt.contains("InterferingTiles"))
        {
            this.interferingTiles = Utils.longArrayToBlockPosSet((LongArrayNBT)nbt.get("InterferingTiles"));
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
