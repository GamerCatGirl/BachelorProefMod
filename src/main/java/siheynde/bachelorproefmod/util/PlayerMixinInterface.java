package siheynde.bachelorproefmod.util;

import net.minecraft.util.math.BlockPos;
import siheynde.bachelorproefmod.entity.robot.RobotEntity;
import siheynde.bachelorproefmod.structure.shrine.Levels;
import siheynde.bachelorproefmod.structure.shrine.Shrine;

import java.util.ArrayList;

public interface PlayerMixinInterface {
    Levels.Topic getTopic();
    void setRobotMoveTo(BlockPos pos);
    void setRobotArrtived(boolean arrived);

    BlockPos getRobotMoveTo();
    boolean getRobotArrived();

    void setRobotHoldBlock(BlockPos pos);
    void setRobotDropBlock();

    void setRobot(RobotEntity robot);
    void setRobotTestWorld(RobotEntity robot);
    void setRunID(String runID);
    String getRunID();
    RobotEntity getRobot();
    RobotEntity getRobotTestWorld();
    void setNameShrine(String name);
    String getNameShrine();
    void setTopicNames(ArrayList<String> topicNames);
    ArrayList<String> getTopicNames();
    void setBookID(String bookID);
    String getBookID();
    Shrine getShrine(BlockPos pos);
    Shrine getShrine();
    ArrayList<Shrine> getVisitedShrines();

}
