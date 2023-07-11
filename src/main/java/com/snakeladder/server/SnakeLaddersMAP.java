package com.snakeladder.server;

import java.util.HashMap;
import java.util.Map;

public class SnakeLaddersMAP {
    private static final Map<Integer, Integer> positionsMAP = new HashMap<>();

    static {
        //ladders
        positionsMAP.put(1, 38);
        positionsMAP.put(4, 14);
        positionsMAP.put(8, 30);
        positionsMAP.put(21, 42);
        positionsMAP.put(28, 76);
        positionsMAP.put(50, 67);
        positionsMAP.put(71, 92);
        positionsMAP.put(80, 99);

        //snakes
        positionsMAP.put(32, 10);
        positionsMAP.put(36, 6);
        positionsMAP.put(48, 26);
        positionsMAP.put(62, 18);
        positionsMAP.put(88, 24);
        positionsMAP.put(95, 56);
        positionsMAP.put(97, 78);
    }

    public static int getPosition(int position){
        return positionsMAP.getOrDefault(position, position);
    }
}
