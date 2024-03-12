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
    private Dictionary<String, Dictionary<String, Hashtable<BlockPos, Block>>> blockSetupsSimpleSort = new Hashtable<>();

    public static ArrayList<Topic> topics = new ArrayList<>();

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

        //test
        Dictionary<String, Hashtable<BlockPos, Block>> blockSetupsStrictComparison = new Hashtable<>();
        Hashtable<BlockPos, Block> blocksPredict = new Hashtable<>();
        blocksPredict.put(new BlockPos(-5, 0, 10), Blocks.OBSIDIAN);
        blocksPredict.put(new BlockPos(0, 0, 10), Blocks.OBSIDIAN);
        blocksPredict.put(new BlockPos(5, 0, 10), Blocks.OBSIDIAN);
        blocksPredict.put(new BlockPos(10, 0, 10), Blocks.OBSIDIAN);

        Hashtable<BlockPos, Block> blocksRun = new Hashtable<>();
        //[black, gray, black, white]
        blocksRun.put(new BlockPos(-5, 0, 10), Blocks.BLACK_STAINED_GLASS);
        blocksRun.put(new BlockPos(0, 0, 10), Blocks.GRAY_STAINED_GLASS);
        blocksRun.put(new BlockPos(5, 0, 10), Blocks.BLACK_STAINED_GLASS);
        blocksRun.put(new BlockPos(10, 0, 10), Blocks.WHITE_STAINED_GLASS);
        blockSetupsStrictComparison.put("Predict", blocksPredict);
        blockSetupsStrictComparison.put("Run", blocksRun);

        blockSetupsSimpleSort.put("Strict Comparison", blockSetupsStrictComparison);

        Topic simple_sort = new Topic (
                "Simple Sort",
                "assets/bachelorproef/racket/simple_sort_2/lesson.rkt",
                blockSetupsSimpleSort,
                new ArrayList<>()
        );

        topics.add(simple_sort);


    }

    public final Level _0 = new Level(
            "Introduction",
            "assets/bachelorproef/racket/introduction/lesson.rkt",
            blockSetupsLevel1);

    public final Topic simple_sort = new Topic (
            "Simple Sort",
            "assets/bachelorproef/racket/simple_sort_2/lesson.rkt",
            blockSetupsSimpleSort,
            new ArrayList<>()
    );

    public static class Topic {
        public String name;
        public String path_rkt;

        public Dictionary<String, Dictionary<String, Hashtable<BlockPos, Block>>> blocks;
        public ArrayList<Topic> requirements;
        public Topic(String name, String path_rkt, Dictionary<String, Dictionary<String, Hashtable<BlockPos, Block>>> blocks, ArrayList<Topic> requirements) {
            this.name = name;
            this.path_rkt = path_rkt;
            this.blocks = blocks;
            this.requirements = requirements;
        }
    }



    public static class Level {
        public String name;
        public String path_rkt;

        public ArrayList<Hashtable<BlockPos, Block>> blocks;

        public Level(String name, String path_rkt, ArrayList<Hashtable<BlockPos, Block>> blocks) {
            this.name = name;
            this.path_rkt = path_rkt;
            this.blocks = blocks;
        }

        public Level(String name, String path_rkt, Dictionary<String, Dictionary<String, Hashtable<BlockPos, Block>>> blocks) {
            this.name = name;
            this.path_rkt = path_rkt;
            this.blocks = new ArrayList<>();
        }

    }

}
