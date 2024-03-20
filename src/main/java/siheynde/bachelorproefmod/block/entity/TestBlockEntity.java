package siheynde.bachelorproefmod.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import siheynde.bachelorproefmod.screen.TestScreenHandler;

public class TestBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    public TestBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TEST_BLOCK_ENTITY, pos, state);
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new TestScreenHandler(syncId, playerInventory, this);
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
