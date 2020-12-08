package com.sorcery.utils;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;

public class BlockPattern
{
    private List<List<String>> pattern3d;
    private Character centralCharacter;
    private List<Integer> centralCharacterLocation;
    private Map<Character, Integer> intMap;
    private Map<Integer, Character> revIntMap;
    private Map<Block, Character> blockMap;

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
        if (getInterference(x, y, z) < 0)
        {
            return true;
        }
        return false;
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
        private List<List<String>> pattern3d = new LinkedList<>();
        private Character centralCharacter = 'I';
        private Map<Character, Integer> intMap = new HashMap<>();
        private Map<Integer, Character> revIntMap = new HashMap<>();
        private HashMap<Block, Character> blockMap = new HashMap<>();
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
