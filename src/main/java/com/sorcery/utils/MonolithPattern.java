package com.sorcery.utils;

import net.minecraft.block.Block;

import javax.annotation.Nullable;
import java.util.*;

public class MonolithPattern
{
    private List<String> patternLines;
    private Character negativeInterference;
    private Character positiveInterference;
    private Character noInterference;
    private Character centralMonolith;
    private List<Integer> centralMonolithLocation;
    private Map<Block, Character> blockMap;

    public MonolithPattern(List<String> patternLines, Character neg, Character pos, Character noInt, Character mono, Map<Block, Character> blockMap)
    {
        this.patternLines = patternLines;
        this.negativeInterference = neg;
        this.positiveInterference = pos;
        this.noInterference = noInt;
        this.centralMonolith = mono;
        this.centralMonolithLocation = this.getCentralMonolithLocation();
        this.blockMap = blockMap;
    }

    public boolean isNegInterference(int x, int z)
    {
      int relX = x + centralMonolithLocation.get(0);
      int relZ = z + centralMonolithLocation.get(1);
      return patternLines.get(relZ).charAt(relX) == negativeInterference;
    }

    public List<List<Integer>> getBlockLocs(Block block)
    {
        return getOffsetPositions(getRawPositions(this.blockMap.get(block)));
    }

    public List<List<Integer>> getPosInterferenceLocs()
    {
        return getOffsetPositions(getRawPositions(this.positiveInterference));
    }

    public List<List<Integer>> getNegInterferenceLocs()
    {
        return getOffsetPositions(getRawPositions(this.negativeInterference));
    }

    private List<List<Integer>> getOffsetPositions(List<List<Integer>> positionsIn)
    {
        List<List<Integer>> offsetPositions = new LinkedList<>();

        for (List<Integer> location : positionsIn)
        {
            offsetPositions.add(Arrays.asList(location.get(0) - centralMonolithLocation.get(0), location.get(1) - centralMonolithLocation.get(1)));
        }
        return offsetPositions;
    }

    private List<List<Integer>> getRawPositions(Character charIn)
    {
        List<List<Integer>> locations = new LinkedList<>();

        for (int i = 0; i < patternLines.size(); i++)
        {
            String line = patternLines.get(i);
            for( int j = 0; j < line.length(); j++)
            {
                if (line.charAt(j) == charIn)
                {
                    locations.add(Arrays.asList(j, i));
                }
            }
        }
        return locations;
    }

    @Nullable
    private List<Integer> getCentralMonolithLocation()
    {
        List<List<Integer>> monoLocations = this.getRawPositions(this.centralMonolith);
        if (monoLocations.size() == 1)
        {
            return monoLocations.get(0);
        }
        return null;
    }



    public static class PatternBuilder
    {
        private List<String> patternLines = new LinkedList<>();
        private Character negativeInterference = 'N';
        private Character positiveInterference = 'P';
        private Character noInterference = '-';
        private Character monolith = 'I';
        private HashMap<Block, Character> blockMap = new HashMap<>();

        public PatternBuilder(){}

        public PatternBuilder addLine(String line)
        {
            this.patternLines.add(line);
            return this;
        }

        public PatternBuilder setNeg(Character charIn)
        {
            this.negativeInterference = charIn;
            return this;
        }

        public PatternBuilder setPos(Character charIn)
        {
            this.positiveInterference = charIn;
            return this;
        }

        public PatternBuilder setNo(Character charIn)
        {
            this.noInterference = charIn;
            return this;
        }

        public PatternBuilder setMono(Character charIn)
        {
            this.monolith = charIn;
            return this;
        }

        public PatternBuilder addBlockMapping(Block block, Character charIn)
        {
            this.blockMap.put(block, charIn);
            return this;
        }

       public MonolithPattern build()
       {
           return new MonolithPattern(patternLines, negativeInterference, positiveInterference, noInterference, monolith, blockMap);
       }
    }



}
