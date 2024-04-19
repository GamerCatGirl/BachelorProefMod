package siheynde.bachelorproefmod.structure.shrine;

import io.wispforest.lavender.book.Book;
import io.wispforest.lavender.book.BookLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.MinecartItem;
import net.minecraft.util.math.BlockPos;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.structure.functions.StrictComparisonBubbleSort;
import siheynde.bachelorproefmod.structure.functions.SubTopic;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedHashMap;

public class Levels {
    private ArrayList<Hashtable<BlockPos, Block>> blockSetupsLevel1 = new ArrayList<>();

    //private ArrayList<MinecartItem> itemsSimpleSort = {};
    private Hashtable<String, Hashtable<String, LinkedHashMap<BlockPos, Block>>> blockSetupsSimpleSort = new Hashtable<>();

    private ArrayList<Topic> topics = new ArrayList<>();

    public Levels() {
        //run 1
        Hashtable<BlockPos, Block> blocksSort3blocks = new Hashtable<>();
        //blocksSort3blocks.put(new BlockPos(0, 0, 10), Blocks.TINTED_GLASS);
        //blocksSort3blocks.put(new BlockPos(-5, 0, 10), Blocks.GRAY_STAINED_GLASS);
        //blocksSort3blocks.put(new BlockPos(5, 0, 10), Blocks.WHITE_STAINED_GLASS);

        //run 2
        Hashtable<BlockPos, Block> blocksSort4blocksNonStrict = new Hashtable<>();
        blocksSort4blocksNonStrict.put(new BlockPos(5, 0, 10), Blocks.TINTED_GLASS);
        blocksSort4blocksNonStrict.put(new BlockPos(0, 0, 10), Blocks.TINTED_GLASS);
        blocksSort4blocksNonStrict.put(new BlockPos(10, 0, 10), Blocks.GRAY_STAINED_GLASS);
        blocksSort4blocksNonStrict.put(new BlockPos(-5, 0, 10), Blocks.WHITE_STAINED_GLASS);

        //blockSetupsLevel1.add(blocksSort3blocks);
        blockSetupsLevel1.add(blocksSort4blocksNonStrict);

        //test
        Hashtable<String, LinkedHashMap<BlockPos, Block>> blockSetupsStrictComparison = new Hashtable<>();


        LinkedHashMap<BlockPos, Block> blocksPredict = new LinkedHashMap<>();
        //Hashtable<BlockPos, Block> blocksPredict = new Hashtable<>(); //TODO: work maybe with input -> output
        blocksPredict.put(new BlockPos(-10, -1, -5), Blocks.OBSIDIAN);
        blocksPredict.put(new BlockPos(-10, -1,  0), Blocks.OBSIDIAN);
        blocksPredict.put(new BlockPos(-10, -1,  5), Blocks.OBSIDIAN);
        blocksPredict.put(new BlockPos(-10, -1,  10), Blocks.OBSIDIAN);
        BachelorProef.LOGGER.info("blocksPredict: " + blocksPredict);

        LinkedHashMap<BlockPos, Block> blocksRun = new LinkedHashMap<>();
        //[black, gray, black, white]
        blocksRun.put(new BlockPos(-5, 0, 10), Blocks.BLACK_STAINED_GLASS);
        blocksRun.put(new BlockPos(0, 0, 10), Blocks.GRAY_STAINED_GLASS);
        blocksRun.put(new BlockPos(5, 0, 10), Blocks.BLACK_STAINED_GLASS);
        blocksRun.put(new BlockPos(10, 0, 10), Blocks.WHITE_STAINED_GLASS);
        blockSetupsStrictComparison.put("Predict", blocksPredict);
        blockSetupsStrictComparison.put("Run", blocksRun);

        blockSetupsSimpleSort.put("Strict Comparison Bubble Sort", blockSetupsStrictComparison);
        blockSetupsSimpleSort.put("Strict Comparison Insertion Sort", blockSetupsStrictComparison);

        Hashtable<String, SubTopic> functionsStrictComparison = new Hashtable<>();
        SubTopic strictComparisonBubbleSort = new StrictComparisonBubbleSort();


        functionsStrictComparison.put("Strict Comparison Bubble Sort", strictComparisonBubbleSort);

        BookLoader.loadedBooks();

        Topic simple_sort = new Topic (
                "Simple Sort",
                "assets/bachelorproef/racket/simple_sort_2/lesson.rkt",
                blockSetupsSimpleSort,
                new ArrayList<>(),
                "bachelorproef:simple_sorting",
                functionsStrictComparison
        );

        topics.add(simple_sort);


    }

    public ArrayList<Topic> getTopics() {
        return topics;
    }

    public static class Topic {
        public String name;
        public String path_rkt;
        public String bookID;
        public Book book;
        public Hashtable<String, SubTopic> functions;


        public Hashtable<String, Hashtable<String, LinkedHashMap<BlockPos, Block>>> blocks;
        public ArrayList<Topic> requirements;
        public ArrayList<MinecartItem> items;


        public void assignBook(Book book){
            this.book = book;
        }

        public SubTopic getFunctions(String functionname){
            BachelorProef.LOGGER.info("functions: " + functions);
            BachelorProef.LOGGER.info("functionname: " + functionname);
            return functions.get(functionname);
        }

        public Topic(String name,
                     String path_rkt,
                     Hashtable<String, Hashtable<String, LinkedHashMap<BlockPos, Block>>> blocks,
                     ArrayList<Topic> requirements,
                     String bookID,
                     Hashtable<String, SubTopic> functions){
            this.name = name;
            this.path_rkt = path_rkt;
            this.blocks = blocks;
            this.requirements = requirements;
            this.bookID = bookID;
            this.functions = functions;
        }
    }

}
