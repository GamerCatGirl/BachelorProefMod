package siheynde.bachelorproefmod.entity.robot;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.util.math.BlockPos;
import siheynde.bachelorproefmod.BachelorProef;

public class ExecuteMove extends Goal {
    RobotEntity robot;
    BlockPos goalPos;
    float range = (float) 0.6;

    public ExecuteMove(RobotEntity robot) {
        this.robot = robot;
    }

    @Override
    public boolean canStart() {
        goalPos = robot.moveTo;
        return goalPos != null;
    }

    @Override
    public boolean shouldContinue() {
        goalPos = robot.moveTo;
        return goalPos != null;
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    private boolean inRange(float v1, float v2) {
        float diff = Math.abs(v1 - v2);
        return diff <= range;
    }

    @Override
    public void tick() {
        // TODO: Execute move
        float moveX = (float) (goalPos.getX() > robot.getBlockPos().getX() ? 0.5 : -0.5);
        float moveY = (float) (goalPos.getY() > robot.getBlockPos().getY() ? 0.5 : -0.5);
        float moveZ = (float) (goalPos.getZ() > robot.getBlockPos().getZ() ? 0.5 : -0.5);

        moveX = inRange(goalPos.getX(), robot.getBlockPos().getX())  ? 0 : moveX;
        moveY = inRange(goalPos.getY(), robot.getBlockPos().getY()) ? 0 : moveY;
        moveZ = inRange(goalPos.getZ(), robot.getBlockPos().getZ()) ? 0 : moveZ;

        //BachelorProef.LOGGER.info("Moving to: " + goalPos + " with: " + moveX + " " + moveY + " " + moveZ);
        robot.move(moveX, moveY, moveZ);

        if (moveX == 0 && moveY == 0 && moveZ == 0) {
            robot.moveTo = null;
        }
    }
}
