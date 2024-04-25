package siheynde.bachelorproefmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.Block;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.entity.robot.RobotEntity;
import siheynde.bachelorproefmod.structure.functions.SubTopic;
import siheynde.bachelorproefmod.structure.shrine.Levels;
import siheynde.bachelorproefmod.structure.shrine.Shrine;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

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
        shrine.findOccurrence(lookFor, "g", player);

        //TODO: set the block visualisation
        //TODO: robot takes block in hand
        RobotEntity robot = playerInterface.getRobot();
        robot.holdBlock(blockPos);
        //playerInterface.getRobot().moveBlock(oldPosition, toPosition, blockName);

    }
}
