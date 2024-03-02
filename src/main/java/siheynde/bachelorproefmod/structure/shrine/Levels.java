package siheynde.bachelorproefmod.structure.shrine;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Levels {

    private Hashtable<BlockPos, Block> blocksLevel1 = new Hashtable<>();

    Levels() {
        blocksLevel1.put(new BlockPos(10, 0, 0), Blocks.DIAMOND_BLOCK);
    }

    public final Level _0 = new Level(
            "Introduction",
            "assets/bachelorproef/racket/introduction/lesson.rkt",
            blocksLevel1);



    public static class Level {
        public String name;
        public String path_rkt;

        public Hashtable<BlockPos, Block> blocks;

        public Level(String name, String path_rkt, Hashtable<BlockPos, Block> blocks) {
            this.name = name;
            this.path_rkt = path_rkt;
            this.blocks = blocks;
        }

    }

}
