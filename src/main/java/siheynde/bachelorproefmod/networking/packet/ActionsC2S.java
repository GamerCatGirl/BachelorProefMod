package siheynde.bachelorproefmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
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
import siheynde.bachelorproefmod.util.Action;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

import java.util.List;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class ActionsC2S {
        private static void setBlockVisualisation(PlayerMixinInterface playerInterface, String blockName, Integer toPosition, ServerPlayerEntity player) {
            BachelorProef.LOGGER.info("Set block visualisation");
            Shrine shrine = playerInterface.getShrine();
            String lookFor = "(set-block!";
            shrine.findOccurrence(lookFor, "!", player);
            //TODO: wait untill action is done
        }

        private static String getBlockVisualisation(PlayerMixinInterface playerInterface, Integer toPosition, ServerPlayerEntity player) {
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

            //TODO: let robot sit
            playerInterface.makeRobotSit();

            //wait a second for the line to be set in terminal
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //TODO: let robot stand up --- so there is no difference between client and server position of robot after waiting
            playerInterface.makeRobotStand();

            //RobotEntity robot = playerInterface.getRobotTestWorld();
            BachelorProef.LOGGER.info("Setting move to of Robot!: " + blockPos);
            //RobotEntity robot2 = playerInterface.getRobot();
            //BachelorProef.LOGGER.info("Robot: " + robot);
            //BachelorProef.LOGGER.info("Robot2: " + robot2);
            BlockPos robotPos = blockPos.add(0, 1, 0);
            playerInterface.setRobotMoveTo(robotPos); //zet y + 1 dat hij op de blok staat ipv in de blok crashed
            playerInterface.setRobotArrtived(false);

            BachelorProef.LOGGER.info("Robot start moving to block");

            while(playerInterface.getRobotArrived() == false || playerInterface.getRobotMoveTo() != null){
                BachelorProef.LOGGER.info("Waiting...");
            }

            playerInterface.setRobotArrtived(false); //reset arrived
            BachelorProef.LOGGER.info("ACTIONS: Robot arrived at block! :)");

            playerInterface.setRobotHoldBlock(blockPos); //TODO: implement this in robot itself too in tick function
            Text text = Text.of("Robot took block at position: " + blockPos);
            player.sendMessage(text);
            //playerInterface.setRobotHoldBlock(blockPos);
            BachelorProef.LOGGER.info("Get Block Visualisation done");
            playerInterface.setPreviousActionDone(true);
            return "DONE";
        }

        public static void letLoopVisualisation(PlayerMixinInterface playerInterface, String functionName, ServerPlayerEntity player) {
            BachelorProef.LOGGER.info("Let loop");
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

    private static void startRobotMovementThread(ServerPlayerEntity player, List<Action> actions) {
            PlayerMixinInterface playerInterface = (PlayerMixinInterface) player;

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(() -> {
            BachelorProef.LOGGER.info("Waiting for previous action to be done...");
            // This is the task that will run in a separate thread
            while (!playerInterface.getPreviousActionDone()) {
                try {
                    Thread.sleep(100); // Sleep to prevent busy-waiting
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return "interrupted";
                }
            }

            playerInterface.setPreviousActionDone(false);

            return "done";
        });

        // Create a scheduled executor to check the future periodically
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            if (future.isDone()) {
                try {
                    BachelorProef.LOGGER.info("Previous action is done!");
                    Action action = actions.get(0);
                    actions.remove(0);
                    BachelorProef.LOGGER.info("Starting new action " + action.toString());

                    action.execute(player);
                    // Execute any additional logic needed after the robot arrives
                    /*
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
                        default:
                            BachelorProef.LOGGER.info("Action not found: " + action);
                            throw new IllegalStateException("Unexpected value: " + action);
                    }

                     */

                    // Shutdown the executors
                    executor.shutdown();
                    scheduler.shutdown();

                    //Start new action
                    BachelorProef.LOGGER.info("Trying to start new action!");
                    if (actions.size() > 0) {startRobotMovementThread(player, actions);}


                } catch (Exception e) {
                    BachelorProef.LOGGER.error("Error occurred while waiting for the robot to arrive", e);
                    // Handle the error appropriately
                }
            }
        }, 0, 100, TimeUnit.MILLISECONDS); // Check every 100 milliseconds
    }



    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                        PacketByteBuf buf, PacketSender responseSender) {

            BachelorProef.LOGGER.info("Received packet to execute Actions");
            PlayerMixinInterface playerInterface = (PlayerMixinInterface) player;
            List<Action> actions = playerInterface.getActions();
            BachelorProef.LOGGER.info("actions on server side: " + actions.toString());


            playerInterface.setRobotArrtived(true);

            //TODO: let robot come to your position



            BachelorProef.LOGGER.info("BUF?: " + buf.toString());

            playerInterface.setPreviousActionDone(true);
            //Action action = actions.get(0);
            //actions.remove(0);

            //for (Action action : actions) {
            startRobotMovementThread(player, actions);
            //}

            //
            //while(buf.isReadable()) {
            //    String action = buf.readString();
            //    BachelorProef.LOGGER.info("Action: " + action);
                //startRobotMovementThread(playerInterface, buf, player, action);
                //if (action.equals("getBlock")) {
                //    currentBuf = PacketByteBufs.create();
                //}
                //BachelorProef.LOGGER.info("New action?: " + buf.isReadable());
            //}

    }
}
