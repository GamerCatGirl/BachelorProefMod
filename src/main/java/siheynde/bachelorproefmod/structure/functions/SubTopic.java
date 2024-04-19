package siheynde.bachelorproefmod.structure.functions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface SubTopic {

    public String runPredict(PlayerEntity player);
    public void runRun();
    public void runInvestigate();
    public void runModify();
    public void runMake();

    public void addBlock(String whereToAdd, Block block, BlockPos pos);

}
