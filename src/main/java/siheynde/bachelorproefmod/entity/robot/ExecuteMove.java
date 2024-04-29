package siheynde.bachelorproefmod.entity.robot;

import net.minecraft.entity.ai.goal.Goal;
import siheynde.bachelorproefmod.BachelorProef;

public class ExecuteMove extends Goal {
    RobotEntity robot;

    public ExecuteMove(RobotEntity robot) {
        this.robot = robot;
    }

    @Override
    public boolean canStart() {
        return robot.moveTo != null;
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        // TODO: Execute move
        BachelorProef.LOGGER.info("Executing move: " + robot.moveTo);
    }
}
