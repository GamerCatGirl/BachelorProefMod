package siheynde.bachelorproefmod.structure.shrine;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Levels {

    private Hashtable<BlockPos, Block> blocksLevel1 = new Hashtable<>();

    Levels() {
        //run 1
        blocksLevel1.put(new BlockPos(0, 0, 10), Blocks.TINTED_GLASS);
        blocksLevel1.put(new BlockPos(-5, 0, 10), Blocks.GRAY_STAINED_GLASS);
        blocksLevel1.put(new BlockPos(5, 0, 10), Blocks.WHITE_STAINED_GLASS);

        //run 2
        blocksLevel1.put(new BlockPos(5, 0, -12), Blocks.TINTED_GLASS);
        blocksLevel1.put(new BlockPos(0, 0, -12), Blocks.TINTED_GLASS);
        blocksLevel1.put(new BlockPos(10, 0, -12), Blocks.GRAY_STAINED_GLASS);
        blocksLevel1.put(new BlockPos(-5, 0, -12), Blocks.WHITE_STAINED_GLASS);
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
