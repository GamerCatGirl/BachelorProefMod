package siheynde.bachelorproefmod.entity.robot;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

import java.util.EnumSet;

public class ExecuteMove extends Goal {
    RobotEntity robot;
    BlockPos goalPos;
    private final TameableEntity tameable;
    private int updateCountdownTicks = 0;
    private final double speed = 1;
    private final WorldView world;
    private final EntityNavigation navigation;
    private LivingEntity owner;
    private final float maxDistance = 10.0f; //based on follow owner goal
    private final float minDistance = 1.5f; //robot moet minstens 2 blokken van de goalpositie
    float range = (float) 0.6;

    public ExecuteMove(RobotEntity robot) {
        this.tameable = (TameableEntity) robot;
        this.navigation = tameable.getNavigation();
        this.robot = robot;
        this.world = tameable.getWorld();
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        if (!(tameable.getNavigation() instanceof MobNavigation) && !(tameable.getNavigation() instanceof BirdNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }

    private boolean cannotFollow() {
        return this.tameable.hasVehicle() || this.tameable.isLeashed();
    }

    @Override
    public boolean shouldContinue() {
        if (this.navigation.isIdle()) {
            BachelorProef.LOGGER.info("Stopping: Navigation is idle");
            return false;
        }
        if (this.cannotFollow()) {
            BachelorProef.LOGGER.info("Stopping: Cannot follow");
            return false;
        }

        double distance = this.tameable.squaredDistanceTo(this.goalPos.getX(), goalPos.getY(), goalPos.getZ());
        Boolean wrongDistance = (distance <= (double)(this.maxDistance * this.maxDistance));

        if(!wrongDistance){
            BachelorProef.LOGGER.info("Stopping: Wrong distance");
            BachelorProef.LOGGER.info("Squared Distance: " + distance);
            BachelorProef.LOGGER.info("Squared Max distance: " + (double)(this.maxDistance * this.maxDistance));
            return false;
        }

        return wrongDistance;
    }

    @Override
    public boolean canStart() {;
        LivingEntity livingEntity = this.tameable.getOwner();

        if (livingEntity == null) {
            BachelorProef.LOGGER.info("FAULT: No owner found");
            return false;
        }

        PlayerMixinInterface ownerMixin = (PlayerMixinInterface) livingEntity;
        BlockPos pos = ownerMixin.getRobotMoveTo();
        BachelorProef.LOGGER.info("Robot " + robot + " can start moving to?: " + ownerMixin.getRobotMoveTo());

        if (pos == null) {
            BachelorProef.LOGGER.info("FAULT: No position found");
            return false;
        }

        if (livingEntity.isSpectator()) {
            BachelorProef.LOGGER.info("FAULT: No spectator found");
            return false;
        }
        if (this.cannotFollow()) {
            BachelorProef.LOGGER.info("FAULT: Cannot Follow");
            return false;
        }
        if (this.tameable.squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ()) < (double)(this.minDistance * this.minDistance)) {
            BachelorProef.LOGGER.info("FAULT: Distance " + this.tameable.squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ()) + " too small");
            return false;
        }
        this.owner = livingEntity;
        goalPos = pos;
        return goalPos != null;
    }

    public void start() {
        BachelorProef.LOGGER.info("Start moving to: " + goalPos);
        this.updateCountdownTicks = 0;
        this.tameable.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
    }

    @Override
    public void stop() {
        BachelorProef.LOGGER.info("Stopping navigation robot");
        this.navigation.stop();
    }

    private void tryTeleport() {
        BlockPos blockPos = goalPos;
        for (int i = 0; i < 10; ++i) {
        int j = this.getRandomInt(-3, 3);
        int k = this.getRandomInt(-1, 1);
        int l = this.getRandomInt(-3, 3);
        boolean bl = this.tryTeleportTo(blockPos.getX() + j, blockPos.getY() + k, blockPos.getZ() + l);
        if (!bl) continue;
        return;
        }
    }

    private boolean tryTeleportTo(int x, int y, int z) {
        if (Math.abs((double)x - this.goalPos.getX()) < 2.0 && Math.abs((double)z - this.goalPos.getZ()) < 2.0) {
            return false;
        }
        if (!this.canTeleportTo(new BlockPos(x, y, z))) {
            return false;
        }
        this.tameable.refreshPositionAndAngles((double)x + 0.5, y, (double)z + 0.5, this.tameable.getYaw(), this.tameable.getPitch());
        this.navigation.stop();
        return true;
    }

    private boolean canTeleportTo(BlockPos pos) {
        PathNodeType pathNodeType = LandPathNodeMaker.getLandNodeType(this.world, pos.mutableCopy());
        if (pathNodeType != PathNodeType.WALKABLE) {
            return false;
        }
        BlockState blockState = this.world.getBlockState(pos.down());
        if (blockState.getBlock() instanceof LeavesBlock) {
            return false;
        }
        BlockPos blockPos = pos.subtract(this.tameable.getBlockPos());
        return this.world.isSpaceEmpty(this.tameable, this.tameable.getBoundingBox().offset(blockPos));
    }

    private int getRandomInt(int min, int max) {
        return this.tameable.getRandom().nextInt(max - min + 1) + min;
    }

    @Override
    public void tick() {
        if (this.goalPos != null){
            this.tameable.getLookControl().lookAt(goalPos.getX(), goalPos.getY(), goalPos.getZ(), 10.0f, this.tameable.getMaxLookPitchChange());

            if (this.tameable.squaredDistanceTo(goalPos.getX(), goalPos.getY(), goalPos.getZ()) < 3) {
                PlayerMixinInterface ownerMixin = (PlayerMixinInterface) this.owner;
                BachelorProef.LOGGER.info("Robot arrived at block: " + goalPos + ", its owner is: " + this.owner);
                ownerMixin.setRobotArrtived(true);
                ownerMixin.setRobotMoveTo(null);
                //TODO: check of robot needs to take the block
            }
            if (--this.updateCountdownTicks > 0) {
                return; //next test doesn't need to be checked if robot is moving
            }
            this.updateCountdownTicks = this.getTickCount(10);

            if (this.tameable.squaredDistanceTo(goalPos.getX(), goalPos.getY(), goalPos.getZ()) >= 144.0) {
                this.tryTeleport();
            } else {
                if ( !this.navigation.startMovingTo(goalPos.getX(), goalPos.getY(), goalPos.getZ(), this.speed)){
                    throw new Error("Navigation starting failed");
                }
                if (navigation.isIdle()) {throw new Error("Navigation is idle");}
                if (navigation.getCurrentPath() == null) {return;}

                BachelorProef.LOGGER.info("path: " + navigation.getCurrentPath());
            }
        }
    }
}
