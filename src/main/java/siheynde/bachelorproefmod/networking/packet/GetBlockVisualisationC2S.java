package siheynde.bachelorproefmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.entity.robot.RobotEntity;
import siheynde.bachelorproefmod.networking.ModPackets;
import siheynde.bachelorproefmod.structure.functions.SubTopic;
import siheynde.bachelorproefmod.structure.shrine.Levels;
import siheynde.bachelorproefmod.structure.shrine.Shrine;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

import javax.xml.crypto.Data;
import java.util.Date;

import static java.lang.Thread.sleep;

public class GetBlockVisualisationC2S {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {

        PlayerMixinInterface playerInterface = (PlayerMixinInterface) player;
        Integer toPosition = buf.readInt();
        Shrine shrine = playerInterface.getShrine();
        Levels.Topic topic = shrine.topic;

        String nameSub = playerInterface.getRunID();
        SubTopic subTopic = topic.getSubTopic(nameSub);

        BlockPos blockPos = subTopic.getPosition(toPosition);
        BachelorProef.LOGGER.info("POS vect->real: " + toPosition + " -> " + blockPos);

        String lookFor = "(get-block ";
        PacketByteBuf newbuf =  shrine.findOccurrence(lookFor, "g", player);
        ServerPlayNetworking.send(player, ModPackets.SET_LINE_TERMINAL, newbuf);

        RobotEntity robot = playerInterface.getRobotTestWorld();
        robot.moveTo = blockPos;
        //
        Text text = Text.of("Robot took block at position: " + blockPos);
        player.sendMessage(text);
        robot.holdBlock(blockPos);
        //playerInterface.getRobot().moveBlock(oldPosition, toPosition, blockName);

    }
}
