package siheynde.bachelorproefmod.util;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.networking.ModPackets;
import siheynde.bachelorproefmod.structure.functions.SubTopic;
import siheynde.bachelorproefmod.structure.shrine.Levels;
import siheynde.bachelorproefmod.structure.shrine.Shrine;

import static java.lang.Thread.sleep;

public class Action {
    public String type;
    public String blockName;
    public int toPosition;
    public String loopName;

    public Action(String blockName, int toPosition) {
        this.type = "setBlock";
        this.blockName = blockName;
        this.toPosition = toPosition;
        BachelorProef.LOGGER.info("New action made of type " + this.type);
    }

    public Action(int toPosition) {
        this.type = "getBlock";
        this.toPosition = toPosition;
        BachelorProef.LOGGER.info("New action made of type " + this.type);
    }

    public Action(String loopName) {
        // TODO: Implement this method
        this.type = "letLoop";
        this.loopName = loopName;
        BachelorProef.LOGGER.info("New action made of type " + this.type);
    }

    public String toString() {
        return this.type;
    }

    public String execute(ServerPlayerEntity player) {
        // TODO: Implement this method
        BachelorProef.LOGGER.info("Executing action of type " + this.type + " ...");
        if (this.type.equals("setBlock")) {
            throw new RuntimeException("Set Block Function not yet implemented!"); //setBlockVisualisation(player);
        } else if (this.type.equals("getBlock")) {
            return getBlockVisualisation(player);
        } else if (this.type.equals("letLoop")) {
            return letLoop(player);//letLoop(player);//letLoop(player);
        } else {
            return "Unknown action type";
        }
    }

    private String letLoop(ServerPlayerEntity player){
        //TODO: implement
        PlayerMixinInterface playerInterface = (PlayerMixinInterface) player;
        playerInterface.setPreviousActionDone(true);
        return "DONE";
    }

    private String getBlockVisualisation(ServerPlayerEntity player) {
        PlayerMixinInterface playerInterface = (PlayerMixinInterface) player;

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


}
