package siheynde.bachelorproefmod.entity.robot;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import siheynde.bachelorproefmod.entity.ModEntities;

public class RobotEntity extends TameableEntity {

    public RobotEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createRobotAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, Integer.MAX_VALUE)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this)); //lower the number to make it a higher priority
        this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 4f));
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.ROBOT.create(world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }


    @Override
    public EntityView method_48926() {
        return null;
    }
}
