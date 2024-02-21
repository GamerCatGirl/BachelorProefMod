package siheynde.bachelorproefmod.util;

import siheynde.bachelorproefmod.entity.robot.RobotEntity;

public interface PlayerMixinInterface {
    void setRobot(RobotEntity robot);
    RobotEntity getRobot();

}
