package siheynde.bachelorproefmod.structure.shrine;

import jscheme.JS;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.w3c.dom.events.EventException;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.structure.functions.SubTopic;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;

public class Shrine {
    public double x;
    public double y;
    public double z;
    //public Levels.Level level;
    public Levels.Topic topic;

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

    public String runCode(){
        return "Answer";
    }


    public void setupUtilTestWorld(World world, BlockPos pos, int rangeArea, String PRIMMfase ,String idRun) {
        BachelorProef.LOGGER.info("Setting up world with id " + idRun);

        Hashtable<BlockPos, Block> blocks = topic.blocks.get(idRun).get(PRIMMfase);

        System.out.println("Blocks: " + blocks);

        SubTopic subTopic = topic.getFunctions(idRun);

        BachelorProef.LOGGER.info("Shrine: " + this);

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
