package com.sorcery.utils;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.sorcery.arcana.IArcanaStorage;
import com.sorcery.block.state.CrystalColor;
import com.sorcery.item.SpellScrollItem;
import com.sorcery.item.SpellbookItem;
import com.sorcery.item.WandItem;
import com.sorcery.spell.Spell;
import com.sorcery.spellcasting.ISpellcasting;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.LongArrayNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.util.*;


public class Utils {

    @CapabilityInject(ISpellcasting.class)
    static Capability<ISpellcasting> SPELLCASTING = null;

    @CapabilityInject(IArcanaStorage.class)
    static Capability<IArcanaStorage> ARCANA_STORAGE = null;


    public static final EnumMap<CrystalColor, String> CRYSTAL_COLOR_MAP = getCrystalColorMap();

    public static final Map<String, CrystalColor> COLOR_CRYSTAL_MAP = getColorCrystalMap();

    public static ISpellcasting getSpellCap(CapabilityProvider<?> capProvider)
    {
        return capProvider.getCapability(SPELLCASTING, null).orElseThrow(NullPointerException::new);
    }

    public static IArcanaStorage getArcanaCap(CapabilityProvider<?> capabilityProvider)
    {
        return capabilityProvider.getCapability(ARCANA_STORAGE, null).orElseThrow(NullPointerException::new);
    }

    public static Spell getSpellFromProvider(CapabilityProvider<?> provider)
    {
        ResourceLocation spellLoc = getSpellCap(provider).getActiveSpell();
        Spell spellOut = GameRegistry.findRegistry(Spell.class).getValue(spellLoc);
        return spellOut;
    }

    public static Spell getSpell(ResourceLocation spellLoc)
    {
        return GameRegistry.findRegistry(Spell.class).getValue(spellLoc);
    }

    public static Vector3d nBlocksAlongVector(Vector3d pos, Vector3d unitVector, float distance)
    {
       return pos.add(unitVector.getX() * distance, unitVector.getY() * distance, unitVector.getZ() * distance);
    }

    public static BlockRayTraceResult blockAlongRay(Vector3d rayStart, Vector3d rayUnitVec, double rayLength, World world, Entity entity)
    {
        Vector3d rayEnd = rayStart.add(rayUnitVec.mul(rayLength, rayLength, rayLength));
        return world.rayTraceBlocks(new RayTraceContext(rayStart, rayEnd, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity));
    }

    public static EnumMap getCrystalColorMap()
    {
        EnumMap<CrystalColor, String> map = new EnumMap<>(CrystalColor.class);
        for (CrystalColor crystalColor : CrystalColor.values())
        {
            map.put(crystalColor, crystalColor.getCrystalName());
        }
        return map;
    }

    public static Map getColorCrystalMap()
    {
        Map<String, CrystalColor> map = new HashMap<>();

        for (CrystalColor crystalColor : CrystalColor.values())
        {
            map.put(crystalColor.getCrystalName(), crystalColor);
        }
        return map;
    }

    public static List<ItemEntity> itemEntitiesInRange(World world, BlockPos pos, int range)
    {
        AxisAlignedBB aaBB = new AxisAlignedBB(pos.add(range, range, range), pos.add(-range, -range, -range));
        return world.getEntitiesWithinAABB(EntityType.ITEM, aaBB, Objects::nonNull);
    }

    public static List<Entity> entitiesInRange(World world, BlockPos pos, int range, Entity excludeEnt)
    {
        AxisAlignedBB aaBB = new AxisAlignedBB(pos.add(range, range, range), pos.add(-range, -range, -range));
        return world.getEntitiesWithinAABBExcludingEntity(excludeEnt, aaBB);
    }

    public static List<Entity> entitiesInCone(World world, BlockPos pos, Entity excludeEnt, Vector3d startPos, Vector3d lookVec, int range, double angleRads)
    {
        List<Entity> entList = entitiesInRange(world, pos, range, excludeEnt);
        List<Entity> finalList = new ArrayList<>();
        for ( Entity entity : entList)
        {
            Vector3d pathVec = entity.getPositionVec().subtract(startPos);
            Double angleBetween = Math.acos(pathVec.dotProduct(lookVec) / (pathVec.length() * lookVec.length()));

            if (angleBetween <= angleRads)
            {
                finalList.add(entity);
            }
        }
        return finalList;
    }

    public static Vector3d getSunVector(World world)
    {
        double celestialRads = world.getCelestialAngleRadians(1.0F);
        return new Vector3d(-Math.sin(celestialRads), Math.cos(celestialRads), 0);
    }

    public static Vector3d getMoonVector(World world)
    {
        double celestialRads = world.getCelestialAngleRadians(1.0F) + Math.PI;
        return new Vector3d(-Math.sin(celestialRads), Math.cos(celestialRads), 0);
    }

    public static Vector3d getStaffVector(PlayerEntity player)
    {
        BasisVector basis = new BasisVector(player.getLook(1));

        Vector3d vec = basis.addXYZ(player.getEyePosition(1), 0.25, 0, 0.1);

        return vec;
    }

    public static <T extends TileEntity> Set<T> getTEInRange(World world, @Nullable TileEntity selfTile, Class clazz, double range)
    {
        Set<T> tileEntitiesInRange = new HashSet<>();
        Predicate<TileEntity> tilePred = getTESearchPredicate(clazz, selfTile, range);
        List<TileEntity> allTE = world.loadedTileEntityList;
        for (TileEntity tileEntity : Collections2.filter(allTE, tilePred))
        {
           tileEntitiesInRange.add((T)tileEntity);
        }
        return tileEntitiesInRange;
    }

    public static Predicate<TileEntity> getTESearchPredicate(Class clazz, BlockPos pos, double range)
    {
        Predicate<TileEntity> pred = new Predicate<TileEntity>()
        {
            @Override
            public boolean apply(@Nullable TileEntity input)
            {
                // Only add selected class
                if (!clazz.isInstance(input))
                {
                    return false;
                }
                // Only add items with distance
                return pos.withinDistance(input.getPos(), range);
            }
        };
        return pred;
    }


    public static Predicate<TileEntity> getTESearchPredicate(Class clazz, TileEntity tile, double range)
    {
        Predicate<TileEntity> pred = new Predicate<TileEntity>()
        {
            final BlockPos pos = tile.getPos();

            @Override
            public boolean apply(@Nullable TileEntity input)
            {
                // Only add selected class
                if (!clazz.isInstance(input))
                {
                    return false;
                }
                // Don't add self
                if (input == tile)
                {
                    return false;
                }
                // Only add items with distance
                return pos.withinDistance(input.getPos(), range);
            }
        };
        return pred;
    }

    public static Predicate<TileEntity> getDarkMonolithSearchPredicate(Class clazz, LivingEntity entity, double range)
    {
        Predicate<TileEntity> pred = new Predicate<TileEntity>()
        {
            final BlockPos pos = entity.getPosition();

            @Override
            public boolean apply(@Nullable TileEntity input)
            {
                // Only add selected class
                if (!clazz.isInstance(input))
                {
                    return false;
                }
                // Only add items with distance
                return pos.withinDistance(input.getPos(), range);
            }
        };
        return pred;
    }

    public static Predicate<Entity> getShootableEntites(Entity excludedEntityIn)
    {
        Predicate<Entity> pred = new Predicate<Entity>()
        {
            final Entity excludedEntity = excludedEntityIn;
            @Override
            public boolean apply(@Nullable Entity input)
            {
                return input instanceof LivingEntity && input != excludedEntityIn;
            }
        };
        return pred;
    }

    public static AxisAlignedBB getRangeAABB(BlockPos pos, int horiz, int posVert, int negVert)
    {
        BlockPos pos1 = pos.add(-horiz, -negVert, -horiz);
        BlockPos pos2 = pos.add(horiz, posVert, horiz);
        return new AxisAlignedBB(pos1, pos2);
    }

    public static Vector3d getVectorFromPos(BlockPos pos){
        return new Vector3d(pos.getX(), pos.getY(), pos.getZ());
    }

    public static Vector3d randomPosInRange(Random rand, double originX, double originY, double originZ, double range)
    {
        double newX = originX + (rand.nextDouble() * range);
        double newY = originY + (rand.nextDouble() * range);
        double newZ = originZ + (rand.nextDouble() * range);
        return new Vector3d(newX, newY, newZ);
    }

    public static LongArrayNBT blockPosSetToLongArray(Set<BlockPos> blockPosSet)
    {
        List<Long> otherTilesList = new LinkedList<>();
        for (BlockPos blockPos : blockPosSet)
        {
            otherTilesList.add(blockPos.toLong());
        }
        return new LongArrayNBT(otherTilesList);
    }

    public static Set<BlockPos> longArrayToBlockPosSet(LongArrayNBT nbt)
    {
        Set<BlockPos> posSet = new HashSet<>();
        for (Long posLong : nbt.getAsLongArray())
        {
            posSet.add(BlockPos.fromLong(posLong));
        }
        return posSet;
    }

    @Nullable
    public static ItemStack getPlayerSpellbook(PlayerEntity playerEntity)
    {
        ItemStack spellbook = null;

        for (ItemStack stack : playerEntity.inventory.mainInventory)
        {
            if (stack.getItem() instanceof SpellbookItem)
            {
                spellbook = stack;
            }
        }
        return spellbook;
    }


    public static WandItem getWand(Spell spell)
    {
        return getWand(spell.getRegistryName());
    }

    public static SpellScrollItem getScroll(Spell spell)
    {
        return getScroll(spell.getRegistryName());
    }

    public static WandItem getWand(ResourceLocation spell)
    {
        Item item = GameRegistry.findRegistry(Item.class).getValue(new ResourceLocation(spell.getNamespace(), "wand_" + spell.getPath().substring(6)));
        return (WandItem)item;
    }

    public static SpellScrollItem getScroll(ResourceLocation spell)
    {
        Item item = GameRegistry.findRegistry(Item.class).getValue(new ResourceLocation(spell.getNamespace(), "scroll_" + spell.getPath().substring(6)));
        return (SpellScrollItem)item;
    }

    public static double horizontalDistance(double x1, double z1, double x2, double z2)
    {
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((z2 - z1), 2));
    }


    public static double horizontalDistance(Vector3d vec1, Vector3d vec2)
    {
        return horizontalDistance(vec1.x, vec1.z, vec2.x, vec2.z);
    }
}
