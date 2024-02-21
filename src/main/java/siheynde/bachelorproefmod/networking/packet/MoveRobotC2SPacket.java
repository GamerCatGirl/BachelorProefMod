package siheynde.bachelorproefmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlacedAdvancement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
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
        BachelorProef.LOGGER.info(robot.toString());
        //TEST IF ROBOT IS NULL!!!!!

        robot.move(1, 1, 1);

    }
}
