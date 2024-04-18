package siheynde.bachelorproefmod.structure.functions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTypes;
import net.minecraft.util.math.BlockPos;

public interface SubTopic {

    public void runPredict();
    public void runRun();
    public void runInvestigate();
    public void runModify();
    public void runMake();

    public void addBlock(String whereToAdd, Block block, BlockPos pos);

}
