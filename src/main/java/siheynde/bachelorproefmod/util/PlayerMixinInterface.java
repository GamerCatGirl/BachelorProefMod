package siheynde.bachelorproefmod.util;

import net.minecraft.util.math.BlockPos;
import siheynde.bachelorproefmod.entity.robot.RobotEntity;
import siheynde.bachelorproefmod.structure.shrine.Levels;
import siheynde.bachelorproefmod.structure.shrine.Shrine;

import java.util.ArrayList;

public interface PlayerMixinInterface {
    Levels.Topic getTopic();
    void setRobot(RobotEntity robot);
    void setRunID(String runID);
    String getRunID();
    RobotEntity getRobot();
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
