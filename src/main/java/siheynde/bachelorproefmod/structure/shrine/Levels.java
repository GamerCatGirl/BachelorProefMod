package siheynde.bachelorproefmod.structure.shrine;

import io.wispforest.lavender.book.Book;
import io.wispforest.lavender.book.BookLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.MinecartItem;
import net.minecraft.util.math.BlockPos;
import siheynde.bachelorproefmod.BachelorProef;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Levels {
    private ArrayList<Hashtable<BlockPos, Block>> blockSetupsLevel1 = new ArrayList<>();

    //private ArrayList<MinecartItem> itemsSimpleSort = {};
    private Hashtable<String, Hashtable<String, Hashtable<BlockPos, Block>>> blockSetupsSimpleSort = new Hashtable<>();

    private ArrayList<Topic> topics = new ArrayList<>();

    public Levels() {
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
        Hashtable<String, Hashtable<BlockPos, Block>> blockSetupsStrictComparison = new Hashtable<>();


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

        blockSetupsSimpleSort.put("Strict Comparison Bubble Sort", blockSetupsStrictComparison);
        blockSetupsSimpleSort.put("Strict Comparison Insertion Sort", blockSetupsStrictComparison);


        //TODO: link Lavender Book to the topic
        BachelorProef.LOGGER.info("LoadedBooks books: " + BookLoader.loadedBooks());
        BookLoader.loadedBooks();

        Topic simple_sort = new Topic (
                "Simple Sort",
                "assets/bachelorproef/racket/simple_sort_2/lesson.rkt",
                blockSetupsSimpleSort,
                new ArrayList<>(),
                "bachelorproef:simple_sorting"
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

        public Hashtable<String, Hashtable<String, Hashtable<BlockPos, Block>>> blocks;
        public ArrayList<Topic> requirements;
        public ArrayList<MinecartItem> items;


        //public getSubTopicBook() {

        //}
        public void assignBook(Book book){
            this.book = book;
        }

        public Topic(String name,
                     String path_rkt,
                     Hashtable<String, Hashtable<String, Hashtable<BlockPos, Block>>> blocks,
                     ArrayList<Topic> requirements,
                     String bookID) {
            this.name = name;
            this.path_rkt = path_rkt;
            this.blocks = blocks;
            this.requirements = requirements;
            this.bookID = bookID;


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
