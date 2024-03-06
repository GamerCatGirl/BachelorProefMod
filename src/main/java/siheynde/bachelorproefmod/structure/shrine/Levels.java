package siheynde.bachelorproefmod.structure.shrine;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Levels {
    private ArrayList<Hashtable<BlockPos, Block>> blockSetupsLevel1 = new ArrayList<>();

    Levels() {
        //run 1
        Hashtable<BlockPos, Block> blocksSort3blocks = new Hashtable<>();
        blocksSort3blocks.put(new BlockPos(0, 0, 10), Blocks.TINTED_GLASS);
        blocksSort3blocks.put(new BlockPos(-5, 0, 10), Blocks.GRAY_STAINED_GLASS);
        blocksSort3blocks.put(new BlockPos(5, 0, 10), Blocks.WHITE_STAINED_GLASS);

        //run 2
        Hashtable<BlockPos, Block> blocksSort4blocksNonStrict = new Hashtable<>();
        blocksSort4blocksNonStrict.put(new BlockPos(5, 0, 10), Blocks.TINTED_GLASS);
        blocksSort4blocksNonStrict.put(new BlockPos(0, 0, 10), Blocks.TINTED_GLASS);
        blocksSort4blocksNonStrict.put(new BlockPos(10, 0, 10), Blocks.GRAY_STAINED_GLASS);
        blocksSort4blocksNonStrict.put(new BlockPos(-5, 0, 10), Blocks.WHITE_STAINED_GLASS);

        blockSetupsLevel1.add(blocksSort3blocks);
        blockSetupsLevel1.add(blocksSort4blocksNonStrict);
    }

    public final Level _0 = new Level(
            "Introduction",
            "assets/bachelorproef/racket/introduction/lesson.rkt",
            blockSetupsLevel1);



    public static class Level {
        public String name;
        public String path_rkt;

        public ArrayList<Hashtable<BlockPos, Block>> blocks;

        public Level(String name, String path_rkt, ArrayList<Hashtable<BlockPos, Block>> blocks) {
            this.name = name;
            this.path_rkt = path_rkt;
            this.blocks = blocks;
        }

    }

}
