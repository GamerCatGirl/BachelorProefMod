package siheynde.bachelorproefmod.block.entity;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import siheynde.bachelorproefmod.screen.FunctionScreenHandler;

public class FunctionBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    private static final Text TITLE = Text.translatable("container.crafting");

    public FunctionBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FUNCTION_BLOCK_ENTITY, pos, state);
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new FunctionScreenHandler(syncId, playerInventory, this);
    }


    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Function Block");
    }

    public void tick(World world, BlockPos pos, BlockState state1) {
        if(world.isClient()) {
            return;
        }
    }
}
