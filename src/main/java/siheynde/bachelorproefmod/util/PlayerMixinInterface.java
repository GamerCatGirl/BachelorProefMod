package siheynde.bachelorproefmod.util;

import net.minecraft.util.math.BlockPos;
import siheynde.bachelorproefmod.entity.robot.RobotEntity;
import siheynde.bachelorproefmod.structure.shrine.Shrine;

import java.util.ArrayList;

public interface PlayerMixinInterface {
    void setRobot(RobotEntity robot);
    RobotEntity getRobot();
    Shrine getShrine(BlockPos pos);
    ArrayList<Shrine> getVisitedShrines();
    void addVisitedShrine(Shrine shrine);

}
