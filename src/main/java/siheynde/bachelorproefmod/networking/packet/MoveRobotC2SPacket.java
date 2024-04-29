package siheynde.bachelorproefmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlacedAdvancement;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.entity.robot.RobotEntity;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

import java.util.Collection;
import java.util.logging.Logger;

public class MoveRobotC2SPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {

        PlayerMixinInterface playerInterface = (PlayerMixinInterface) player;
        RobotEntity robot = playerInterface.getRobot();

        BachelorProef.LOGGER.info("Received move robot packet");

        int x = buf.readVarInt();
        int y = buf.readVarInt();
        int z = buf.readVarInt();
        if (robot != null) {
            robot.move(x, y, z);
        }

        //test the place block
        Block block = Blocks.DIAMOND_BLOCK;
        BlockPos pos = new BlockPos(-129, 73, 81);

        if (robot != null) {
            robot.placeBlock(pos, block);
            BachelorProef.LOGGER.info("Place block " + block + " at " + pos);
        }

        //TODO: popup no assigned robot
    }
}
