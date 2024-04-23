package siheynde.bachelorproefmod.structure.shrine;

import jscheme.JS;
import jscheme.SchemePair;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.w3c.dom.events.EventException;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.structure.functions.SubTopic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

public class Shrine {
    public double x;
    public double y;
    public double z;
    //public Levels.Level level;
    public Levels.Topic topic;
    private BufferedReader bufferedReader;
    private String currentLine; //soms worden er 2 functies gecalled op 1 line
    private Integer currentLineIndex;

    int maxRange = 20;

    public Shrine(double x, double y, double z, Levels.Topic topic) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.topic = topic;
    }


    public Object Modify(){
        return JS.call("modify");
    }


    public String predictAnswer(){
        try {
            String path = topic.path_rkt;
            URL resource = getClass().getClassLoader().getResource(path);
            JS.load(new java.io.FileReader(resource.getFile()));

            return JS.call("predict").toString();

            //JS.load(new java.io.FileReader("src/main/resources/assests/bachelorproef/racket/introduction/predict.rkt"));
        } catch (EventException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public String getName() {
        return this.topic.name;
    }

    public boolean isInRange(double x, double y, double z) {
        double deltaX = x - this.x;
        double deltaY = y - this.y;
        double deltaZ = z - this.z;
        double distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2) + Math.pow(deltaZ, 2));
        return distance < this.maxRange;
    }

    private void getStartLine(URL resource, String functionName){
        try {
            FileReader fileReader = new FileReader(resource.getFile());
            this.bufferedReader = new BufferedReader(fileReader);

            //definition filereader
            String startFunction = "(define (" + functionName + " ";

            currentLine = bufferedReader.readLine();
            currentLineIndex = 1;
            while (!(currentLine.contains(startFunction))) {
                BachelorProef.LOGGER.info("Reading: " + currentLine);
                currentLine = bufferedReader.readLine();
                currentLineIndex++;
            }
            BachelorProef.LOGGER.info("Found: " + currentLine + " at line: " + currentLineIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findOccurrence(String sequence, String split) {
        try {
            while (!(currentLine.contains(sequence))) {
                currentLine = bufferedReader.readLine();
                currentLineIndex++;
            }
            BachelorProef.LOGGER.info("Found: " + currentLine + " at line: " + currentLineIndex);
            currentLine = currentLine.split(split)[1];
            BachelorProef.LOGGER.info("new currentline: " + currentLine);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String runCode(List<Block> blocks, String functionName){
        try {
            String path = topic.path_rkt;
            URL resource = getClass().getClassLoader().getResource(path);
            // TODO: get line number where function starts
            getStartLine(resource, functionName);
            // TODO: Highlight line client slide
            //
            JS.load(new java.io.FileReader(resource.getFile()));
            SchemePair pair = JS.list();

            for (Block block : blocks) {
                String block_unedited = block.toString();
                String[] block_split = block_unedited.split(":");
                String block_name = block_split[1].split("}")[0];
                pair = JS.list(block_name, pair);
            }

            return JS.call("run", pair).toString();

            //JS.load(new java.io.FileReader("src/main/resources/assests/bachelorproef/racket/introduction/predict.rkt"));
        } catch (EventException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        // TODO: set the bufferedReader to null
        return null;
    }


    public void setupUtilTestWorld(World world, BlockPos pos, int rangeArea, String PRIMMfase ,String idRun) {
        BachelorProef.LOGGER.info("Setting up world with id " + idRun);

        LinkedHashMap<BlockPos, Block> blocks = topic.blocks.get(idRun).get(PRIMMfase);

        SubTopic subTopic = topic.getFunctions(idRun);

         blocks.forEach((blockPos, block) -> {;
            BachelorProef.LOGGER.info("Block: " + block);
            BachelorProef.LOGGER.info("BlockPos: " + blockPos);

            int x = blockPos.getX();
            int y = blockPos.getY();
            int z = blockPos.getZ();

             BlockPos blockPos1 = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
             BachelorProef.LOGGER.info("New BlockPos: " + blockPos1);

            if (x <= rangeArea && y <= rangeArea && z <= rangeArea) {
                world.setBlockState(blockPos1, block.getDefaultState());
                subTopic.addBlock(PRIMMfase, block, blockPos1);
            }
        });

    }
}
