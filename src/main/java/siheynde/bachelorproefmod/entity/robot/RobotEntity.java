package siheynde.bachelorproefmod.entity.robot;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
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
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.entity.ModEntities;
import siheynde.bachelorproefmod.networking.ModPackets;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

//TODO: motivition factor -> need to unlock first other shrine
//TODO: robot need to activate the portal before can go through
//TODO: get help from the robot when stuck
//TODO: storyboard what the game will look like in global
//TODO: exercises linked to storyline

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
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 4f));
        this.goalSelector.add(3, new FollowOwnerGoal(this, 1.0, 10.0f, 2.0f, false));
    }

    public void move(int x, int y, int z) { //TODO: doens't work
        //if (this.canTeleportTo(new BlockPos(x, y, z))) --- TODO: function from FollowOwnerGoal (make something similar)
        this.getOwner().sendMessage(Text.of("Your robot moved with x: " + + x + "y: " + y + "& z: " + z));
        this.refreshPositionAndAngles(this.getX() + x, this.getY() + y, this.getZ() + z, this.getYaw(), this.getPitch());
    }

    public void replace(BlockPos pos) { //TODO: doesn't work completely
        this.refreshPositionAndAngles(pos, this.getYaw(), this.getPitch()); //TODO: doesn't work
        //this.setPosition();
        //this.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), this.getYaw(), this.getPitch());
    }

    public void placeBlock(BlockPos pos, Block block) {
        if (this.getWorld().canSetBlock(pos)) {
            //TODO: place robot close to the block
            this.getWorld().setBlockState(pos, block.getDefaultState());
            this.getOwner().sendMessage(Text.of("Your robot placed ... block on pos ...."));

        }
        //if (this.canPlaceBlock(pos)) {
        //    this.world.setBlockState(pos, block.getDefaultState());
        //}
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

            if (!this.isSitting()) {
                Text text = Text.of("Robot set on sitting!");
                player.sendMessage(text);
            }
            else {
                Text text = Text.of("Robot set on standing!");
                player.sendMessage(text);
            }

            this.setSitting(!this.isSitting());
            this.navigation.stop();
            this.setTarget(null);
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
