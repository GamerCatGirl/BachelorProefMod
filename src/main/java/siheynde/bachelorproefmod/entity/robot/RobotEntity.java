package siheynde.bachelorproefmod.entity.robot;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.entity.ModEntities;
import siheynde.bachelorproefmod.networking.ModPackets;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

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
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 4f));
        this.goalSelector.add(3, new FollowOwnerGoal(this, 1.0, 10.0f, 2.0f, false));
    }

    public void move(int x, int y, int z) {
        BachelorProef.LOGGER.info("Moving to " + x + " " + y + " " + z);
       // this.updatePosition(x, y, z); ---> check if this is the right function
    }

    //TODO: make happy Goal when new function received

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.ROBOT.create(world);
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();

        PlayerMixinInterface playerInterface = (PlayerMixinInterface) player;

        //ClientPlayerEntity serverPlayer = (ClientPlayerEntity) player;
        //ClientPlayerMixinInterface playerInterface = (ClientPlayerMixinInterface) serverPlayer;


        if (this.getWorld().isClient) {
            boolean bl = this.isOwner(player) || this.isTamed() || (itemStack.isOf(Items.IRON_INGOT) && !this.isTamed());
            return bl ? ActionResult.CONSUME : ActionResult.PASS;
        }
        if (this.isOwner(player)) {
            ServerPlayNetworking.send((ServerPlayerEntity) player, ModPackets.OPEN_ADVANCEMENTS_ID, PacketByteBufs.empty());
            //player.openHandledScreen(new AdvancementsScreen(player.getServer().getAdvancementLoader()) );
            //player.op
            //zie video min 22  Block Entity
            System.out.println("Owner");
        }
        else if (this.isTamed()) {
            // TODO: View progress other player
            System.out.println("Tamed");
        }
        else if (item == Items.IRON_INGOT && !this.isTamed() && playerInterface.getRobot() == null) {

            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            this.setOwner(player);
            playerInterface.setRobot(this);
            BachelorProef.LOGGER.info(playerInterface.getRobot().toString());
            BachelorProef.LOGGER.info(playerInterface.toString());

            //TODO: save after game is closed and opened again
            this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
            player.addExperienceLevels(1);
        }

        return ActionResult.SUCCESS;


    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }


    @Override
    public EntityView method_48926() {
        return this.getWorld();
    }

    @Nullable
    @Override
    public LivingEntity getOwner() {
        return super.getOwner();
    }




}
