package siheynde.bachelorproefmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
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
import java.util.concurrent.*;

import static java.lang.Thread.sleep;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class GetBlockVisualisationC2S { //TODO: delete this file

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

        //wait a second for the line to be set in terminal
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //RobotEntity robot = playerInterface.getRobotTestWorld();
        //robot.arrived = false;
        //robot.moveTo = blockPos;

        //
        //TODO: wait untill robot has arrived
        ExecutorService executor = newSingleThreadExecutor();
        Future<String> future =  executor.submit(() -> {
                //TODO: check in player if the robot is arrived
                //while(robot.arrived == false){}
                return "done";
        });
        try {
            future.get(50, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //while(robot.arrived == false){}
        //TODO: Create CompletableFuture to wait for robot.arrived to be true


        //

        Text text = Text.of("Robot took block at position: " + blockPos);
        player.sendMessage(text);
        //robot.holdBlock(blockPos);

        //TODO: Ask if server play networking is done

        //playerInterface.getRobot().moveBlock(oldPosition, toPosition, blockName);

    }
}
