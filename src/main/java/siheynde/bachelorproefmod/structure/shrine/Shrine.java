package siheynde.bachelorproefmod.structure.shrine;

import jscheme.JS;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.w3c.dom.events.EventException;
import siheynde.bachelorproefmod.BachelorProef;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;

public class Shrine {
    public double x;
    public double y;
    public double z;
    public Levels.Level level;

    int maxRange = 20;

    public Shrine(double x, double y, double z, int level) {
        this.x = x;
        this.y = y;
        this.z = z;

        Levels levels = new Levels();

        switch (level){
            case 0: this.level = levels._0;
            default: this.level = levels._0;
        }
    }

    public Object Modify(){
        return JS.call("modify");
    }

    public ArrayList<Hashtable<BlockPos, Block>> getBlockSetups(){
        return this.level.blocks;
    }

    public String predictAnswer(){
        try {
            String path = level.path_rkt;
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
        return this.level.name;
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


    public void setupUtilTestWorld(World world, BlockPos pos, int rangeArea, int idRun) {
        BachelorProef.LOGGER.info("Setting up world with id " + idRun);
        level.blocks.get(idRun).forEach((blockPos, block) -> {;
            BachelorProef.LOGGER.info("Block: " + block);
            BachelorProef.LOGGER.info("World: " + world);

            int x = blockPos.getX();
            int y = blockPos.getY();
            int z = blockPos.getZ();

            BlockPos blockPos1 = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getY() + z);

            if (x <= rangeArea && y <= rangeArea && z <= rangeArea) {
                world.setBlockState(blockPos1, block.getDefaultState());
            }
        });

    }
}
