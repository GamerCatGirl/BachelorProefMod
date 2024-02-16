package siheynde.bachelorproefmod.structure.shrine;

import java.util.ArrayList;

public class Levels {
    public static ArrayList<Level> levels = new ArrayList<>();

    public static final Level _0 = new Level("Introduction");

    public static class Level {
        public String name;

        public Level(String name) {
            this.name = name;
        }

    }

}
