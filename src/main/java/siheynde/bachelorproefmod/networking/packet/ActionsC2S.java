package siheynde.bachelorproefmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
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

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class ActionsC2S {

        private static void setBlockVisualisation(PlayerMixinInterface playerInterface, String blockName, Integer toPosition, ServerPlayerEntity player) {
            Shrine shrine = playerInterface.getShrine();
            String lookFor = "(set-block!";
            shrine.findOccurrence(lookFor, "!", player);
            //TODO: wait untill action is done
        }

        private static void getBlockVisualisation(PlayerMixinInterface playerInterface, Integer toPosition, ServerPlayerEntity player) {
            BachelorProef.LOGGER.info("Get block visualisation");
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
            BachelorProef.LOGGER.info("Setting move to of Robot!: " + blockPos);
            //RobotEntity robot2 = playerInterface.getRobot();
            //BachelorProef.LOGGER.info("Robot: " + robot);
            //BachelorProef.LOGGER.info("Robot2: " + robot2);
            playerInterface.setRobotMoveTo(blockPos);
            playerInterface.setRobotArrtived(false);
            //robot.arrived = false;
            //robot.moveTo = blockPos;

            BachelorProef.LOGGER.info("Robot start moving to block");

            //wait untill robot has arrived on different thread
            ExecutorService executor = newSingleThreadExecutor();
           Future<String> future =  executor.submit(() -> {
               BachelorProef.LOGGER.info("Trying to check if robot arrived for user " + player + "!");
                while(playerInterface.getRobotArrived() == false){}
                playerInterface.setRobotArrtived(false); //reset arrived
                BachelorProef.LOGGER.info("ACTIONS: Robot arrived at block! :)"); //TODO: is never completed
                return "done";
            });
            try {
                future.get(5000, TimeUnit.SECONDS); //waiting max 50 seconds per action
                BachelorProef.LOGGER.info("ACTIONS: Robot arrived at block! :)");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            Text text = Text.of("Robot took block at position: " + blockPos);
            player.sendMessage(text);
            playerInterface.setRobotHoldBlock(blockPos);
            //robot.holdBlock(blockPos);
        }

        public static void letLoopVisualisation(PlayerMixinInterface playerInterface, String functionName, ServerPlayerEntity player) {
            Shrine shrine = playerInterface.getShrine();
            String lookFor = "(let-loop '" + functionName;
            List<String> activatedLoops = shrine.activatedLoops;
            if (activatedLoops.contains(functionName)) {
                shrine.LookInCompleteFunction = true;
                shrine.indexInFunction = 0;
                shrine.findOccurrence(lookFor, "'", player);
            } else {
                shrine.findOccurrence(lookFor, "'", player);
                shrine.activatedLoops.add(functionName);
            }
        }


        public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                                   PacketByteBuf buf, PacketSender responseSender) {

            BachelorProef.LOGGER.info("Received packet to execute Actions");
            PlayerMixinInterface playerInterface = (PlayerMixinInterface) player;

            //TODO: let robot come to your position



            BachelorProef.LOGGER.info("BUF?: " + buf.toString());

            while(buf.isReadable()){
                String action = buf.readString();
                BachelorProef.LOGGER.info("Action: " + action);
                switch(action){
                    case "setBlock":
                        String blockName = buf.readString();
                        Integer toPosition = buf.readInt();
                        setBlockVisualisation(playerInterface, blockName, toPosition, player);
                        //TODO: ask here if the action is done
                        break;
                    case "getBlock":
                        Integer toPosition2 = buf.readInt();
                        getBlockVisualisation(playerInterface, toPosition2, player);
                        break;

                    case "letLoop":
                        String loopName = buf.readString();
                        letLoopVisualisation(playerInterface, loopName, player);
                        break;
                }
                BachelorProef.LOGGER.info("New action?: " + buf.isReadable());
            }

            //TODO: make robot stand up again
        }
}
