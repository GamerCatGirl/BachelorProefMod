package siheynde.bachelorproefmod.structure.shrine;

import java.util.ArrayList;

public class Levels {
    public static ArrayList<Level> levels = new ArrayList<>();

    public static final Level _0 = new Level("Introduction", "assets/bachelorproef/racket/introduction/lesson.rkt");

    public static class Level {
        public String name;
        public String path_rkt;

        public Level(String name, String path_rkt) {

            this.name = name;
            this.path_rkt = path_rkt;
        }

    }

}
