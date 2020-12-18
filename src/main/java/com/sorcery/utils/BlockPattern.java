package com.sorcery.utils;

import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;

public class BlockPattern
{
    private final List<List<String>> pattern3d;
    private final Character centralCharacter;
    private final List<Integer> centralCharacterLocation;
    private final Map<Character, Integer> intMap;
    private final Map<Integer, Character> revIntMap;
    private final Map<Block, Character> blockMap;

    public BlockPattern(List<List<String>> pattern3d, Character centralChar, Map<Character, Integer> interferenceMap, Map<Integer, Character> revIntMap, Map<Block, Character> blockMap)
    {
        this.pattern3d = pattern3d;
        this.centralCharacter = centralChar;
        this.centralCharacterLocation = this.getCentralCharacterLocation();
        this.intMap = interferenceMap;
        this.revIntMap = revIntMap;
        this.blockMap = blockMap;
    }

    public int getInterference(int x, int y, int z)
    {
        int relX = x + centralCharacterLocation.get(0);
        int relY = y + centralCharacterLocation.get(1);
        int relZ = z + centralCharacterLocation.get(2);

        try
        {
            return this.intMap.get(pattern3d.get(relY).get(relZ).charAt(relX));
        } catch (IndexOutOfBoundsException | NullPointerException exception)
        {
            return 0;
        }
    }

    public boolean isNegInterference(int x, int y, int z)
    {
        return getInterference(x, y, z) < 0;
    }

    private List<List<Integer>> getCharPositions(Character charIn)
    {
        List<List<Integer>> locations = new LinkedList<>();

        for (int i = 0; i < pattern3d.size(); i++)
        {
            List<String> patternSlice = this.pattern3d.get(i);
            for( int j = 0; j < patternSlice.size(); j++)
            {
                String line = patternSlice.get(j);
                for( int k = 0; k < line.length(); k++)
                {
                    if (line.charAt(k) == charIn)
                    {
                        locations.add(Arrays.asList(k, pattern3d.size() - (i + 1), j));
                    }
                }
            }
        }
        return locations;
    }

    private List<List<Integer>> getOffsetPositions(List<List<Integer>> positionsIn)
    {
        List<List<Integer>> offsetPositions = new LinkedList<>();

        for (List<Integer> location : positionsIn)
        {
            offsetPositions.add(Arrays.asList(location.get(0) - centralCharacterLocation.get(0), location.get(1) - centralCharacterLocation.get(1), location.get(2) - centralCharacterLocation.get(2)));
        }
        return offsetPositions;
    }

    public List<List<Integer>> getBlockLocs(Block block)
    {
        return getOffsetPositions(getCharPositions(this.blockMap.get(block)));
    }

    public List<List<Integer>> getBlockLocsRotated(Block block, Direction direction)
    {
        return rotateRelativePositions(getOffsetPositions(getCharPositions(this.blockMap.get(block))), direction);
    }

    public List<List<Integer>> getCharLocsRotated(Character charIn, Direction direction)
    {
        return rotateRelativePositions(getOffsetPositions(getCharPositions(charIn)), direction);
    }

    public List<List<Integer>> rotateRelativePositions(List<List<Integer>> positions, Direction direction)
    {
        List<List<Integer>> rotatedPositions = new LinkedList<>();

        // +Z
        if (direction == Direction.NORTH)
        {
            return positions;
        }
        // -Z
        if (direction == Direction.SOUTH)
        {
            for (List<Integer> position : positions)
            {
                rotatedPositions.add(Arrays.asList(position.get(0) * -1, position.get(1), position.get(2) * -1));
            }
        }
        // +X
        if (direction == Direction.EAST)
        {
            for (List<Integer> position : positions)
            {
                rotatedPositions.add(Arrays.asList(position.get(2) * -1, position.get(1), position.get(0)));
            }
        }
        // -X
        if (direction == Direction.WEST)
        {
            for (List<Integer> position : positions)
            {
                rotatedPositions.add(Arrays.asList(position.get(2), position.get(1), position.get(0) * -1));
            }
        }
        return rotatedPositions;
    }

    private List<List<Integer>> getIntPositions(Integer interference)
    {
        Character intChar = this.revIntMap.get(interference);
        return getOffsetPositions(getCharPositions(intChar));
    }

    public List<List<Integer>> getPosInterferenceLocs()
    {
        return getOffsetPositions(getCharPositions(this.revIntMap.get(1)));
    }

    public List<List<Integer>> getNegInterferenceLocs()
    {
        return getOffsetPositions(getCharPositions(this.revIntMap.get(-1)));
    }

    @Nullable
    private List<Integer> getCentralCharacterLocation()
    {
        List<List<Integer>> monoLocations = this.getCharPositions(this.centralCharacter);
        if (monoLocations.size() == 1)
        {
            return monoLocations.get(0);
        }
        return null;
    }

    public List<BlockPos> getBlockPositions(BlockPos centralPos, Block block)
    {
        List<BlockPos> positions = new LinkedList<>();
        for (List<Integer> loc : this.getBlockLocs(block))
        {
            positions.add(new BlockPos(centralPos.getX() + loc.get(0), centralPos.getY() + loc.get(1), centralPos.getZ() + loc.get(2)));
        }
        return positions;
    }

    public List<BlockPos> getBlockPositionsRotated(BlockPos centralPos, Block block, Direction direction)
    {
        List<BlockPos> positions = new LinkedList<>();
        for (List<Integer> loc : this.getBlockLocsRotated(block, direction))
        {
            positions.add(new BlockPos(centralPos.getX() + loc.get(0), centralPos.getY() + loc.get(1), centralPos.getZ() + loc.get(2)));
        }
        return positions;
    }

    public List<BlockPos> getBlockPositionsRotated(BlockPos centralPos, Character charIn, Direction direction)
    {
        List<BlockPos> positions = new LinkedList<>();
        for (List<Integer> loc : this.getCharLocsRotated(charIn, direction))
        {
            positions.add(new BlockPos(centralPos.getX() + loc.get(0), centralPos.getY() + loc.get(1), centralPos.getZ() + loc.get(2)));
        }
        return positions;
    }

    public boolean isPatternValid(World world, BlockPos centralPos)
    {
        boolean allValid = true;
        for(Map.Entry<Block, Character> entry: this.blockMap.entrySet())
        {
            boolean locationsValid = true;
            for (List<Integer> loc : this.getBlockLocs(entry.getKey()))
            {
                BlockPos checkPos = centralPos.add(loc.get(0), loc.get(1), loc.get(2));
                if (world.getBlockState(checkPos).getBlock() != entry.getKey())
                {
                    locationsValid = false;
                }
            }
            if (!locationsValid)
            {
                return false;
            }
        }
        return true;
    }

    public Map<Block, Character> getBlockMap()
    {
        return blockMap;
    }

    public static class PatternBuilder
    {
        private final List<List<String>> pattern3d = new LinkedList<>();
        private Character centralCharacter = 'I';
        private final Map<Character, Integer> intMap = new HashMap<>();
        private final Map<Integer, Character> revIntMap = new HashMap<>();
        private final HashMap<Block, Character> blockMap = new HashMap<>();
        private int lineIndex = 0;

        public PatternBuilder()
        {
            this.pattern3d.add(new LinkedList<>());
            this.addIntCharInternal('N', -1);
            this.addIntCharInternal('P', 1);
            this.addIntCharInternal('-', 0);

        }

        private void addIntCharInternal(Character charIn, Integer intIn)
        {
            this.intMap.put(charIn, intIn);
            this.revIntMap.put(intIn, charIn);
        }

        public PatternBuilder addIntChar(Character charIn, Integer intIn)
        {
           this.addIntCharInternal(charIn, intIn);
           return this;
        }

        public PatternBuilder addLevel()
        {
            this.pattern3d.add(new LinkedList<>());
            this.lineIndex += 1;
            return this;
        }

        public PatternBuilder addLine(String line)
        {
            this.pattern3d.get(this.lineIndex).add(line);
            return this;
        }

        public PatternBuilder setCentral(Character charIn)
        {
            this.centralCharacter = charIn;
            return this;
        }

        public PatternBuilder addBlockMapping(Block block, Character charIn)
        {
            this.blockMap.put(block, charIn);
            return this;
        }

       public BlockPattern build()
       {
           return new BlockPattern(pattern3d, centralCharacter, intMap, revIntMap, blockMap);
       }
    }



}
