package siheynde.bachelorproefmod.structure.functions;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import siheynde.bachelorproefmod.BachelorProef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StrictComparisonBubbleSort implements SubTopic {
    public ArrayList<BlockPos> blocksPredict = new ArrayList<>();
    List<Block> blocksPredictOrder = Arrays.asList(
            Blocks.BLACK_STAINED_GLASS,
            Blocks.GRAY_STAINED_GLASS,
            Blocks.BLACK_STAINED_GLASS,
            Blocks.WHITE_STAINED_GLASS);

    @Override
    public void runPredict() {
        BachelorProef.LOGGER.info("Running predict");
        BachelorProef.LOGGER.info("Blocks: " + blocksPredict);
        BachelorProef.LOGGER.info(this.toString());
        //TODO: check if the blocks are in the right order
    }

    @Override
    public void addBlock(String whereToAdd, Block block, BlockPos pos){
        BachelorProef.LOGGER.info("Adding block to " + whereToAdd);
        BachelorProef.LOGGER.info(this.toString());
        if(whereToAdd.equals("Predict")){
            BlockPos newPos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()); //needs to be above the other block
            blocksPredict.add(newPos);
        }
    }

    @Override
    public void runRun() {
        BachelorProef.LOGGER.info("Running run");
    }

    @Override
    public void runInvestigate() {
        BachelorProef.LOGGER.info("Running investigate");
    }

    @Override
    public void runModify() {
        BachelorProef.LOGGER.info("Running modify");
    }

    @Override
    public void runMake() {
        BachelorProef.LOGGER.info("Running make");
    }
}
