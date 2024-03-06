package siheynde.bachelorproefmod.util;

import net.minecraft.util.math.BlockPos;
import siheynde.bachelorproefmod.entity.robot.RobotEntity;
import siheynde.bachelorproefmod.structure.shrine.Shrine;

import java.util.ArrayList;

public interface PlayerMixinInterface {
    void setRobot(RobotEntity robot);

    void setRunID(int runID);
    int getRunID();
    RobotEntity getRobot();
    Shrine getShrine(BlockPos pos);
    ArrayList<Shrine> getVisitedShrines();
    void addVisitedShrine(Shrine shrine);

}
