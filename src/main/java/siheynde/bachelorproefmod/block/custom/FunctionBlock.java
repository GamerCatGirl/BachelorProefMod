package siheynde.bachelorproefmod.block.custom;

import com.mojang.serialization.MapCodec;
//import java.nio.
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import siheynde.bachelorproefmod.screen.RobotScreenHandler;

public class FunctionBlock extends Block {
    public FunctionBlock(AbstractBlock.Settings settings) {super(settings);}
    private static final Text TITLE = Text.translatable("container.crafting");
    public static final MapCodec<FunctionBlock> CODEC = FunctionBlock.createCodec(FunctionBlock::new);

    //@Override
    public MapCodec<? extends FunctionBlock> getCodec() {
       return CODEC;
    }


    //@Nullable
    //@Override
    //public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
     //   return new FunctionBlockEntity(pos, state);
    //}

    //@Override
    //public BlockRenderType getRenderType(BlockState state) {
    //    return BlockRenderType.MODEL;
    //}


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        //open file
        //System.out.println("Block clicked");
        player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
        player.incrementStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
        return ActionResult.CONSUME;
        //player.openHandledScreen();
    }

    //@Nullable
    //@Override
    //public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
    //    return validateTicker(type, ModBlockEntities.FUNCTION_BLOCK_ENTITY,
    //            (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    //}

    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new RobotScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos)), TITLE);
    }


}
