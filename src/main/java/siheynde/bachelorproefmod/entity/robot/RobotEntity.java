package siheynde.bachelorproefmod.entity.robot;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.entity.ModEntities;
import siheynde.bachelorproefmod.networking.ModPackets;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

import java.util.Optional;

//TODO: motivation factor -> need to unlock first other shrine
//TODO: robot need to activate the portal before can go through
//TODO: get help from the robot when stuck
//TODO: storyboard what the game will look like in global
//TODO: exercises linked to storyline

public class RobotEntity extends TameableEntity implements InventoryOwner {
    private static final TrackedData<Optional<BlockState>> CARRIED_BLOCK = DataTracker.registerData(RobotEntity.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_STATE);
    public BlockPos moveTo = null;


    public RobotEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        BachelorProef.LOGGER.info(entityType.toString());
    }

    public void setWorld(World world) {
        super.setWorld(world);
    }
    private final SimpleInventory inventory = new SimpleInventory(8);

    public static DefaultAttributeContainer.Builder createRobotAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, Integer.MAX_VALUE)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f);
    }

    public void holdBlock(BlockPos pos) {
        BlockState state = this.getWorld().getBlockState(pos);
        Block block = state.getBlock();
        ItemStack stack = new ItemStack(block);
        this.equipStack(EquipmentSlot.MAINHAND, stack);
        //this.hand
        this.dataTracker.set(CARRIED_BLOCK, Optional.ofNullable(state));
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this)); //lower the number to make it a higher priority
        this.goalSelector.add(1, new ExecuteMove(this));
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

    private boolean teleportTo(double x, double y, double z) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);
        while (mutable.getY() > this.getWorld().getBottomY() && !this.getWorld().getBlockState(mutable).blocksMovement()) {
            mutable.move(Direction.DOWN);
        }
        BlockState blockState = this.getWorld().getBlockState(mutable);
        boolean bl = blockState.blocksMovement();
        boolean bl2 = blockState.getFluidState().isIn(FluidTags.WATER);
        if (!bl || bl2) {
            return false;
        }
        Vec3d vec3d = this.getPos();
        boolean bl3 = this.teleport(x, y, z, true);
        if (bl3) {
            this.getWorld().emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(this));
        }
        return bl3;
    }




    public void moveBlock(BlockPos newPos, Block block) {
        //block.getDefaultState();
        //BlockState OldState = this.getWorld().getBlockState(oldPos);
        //setCarriedBlock(OldState);
            //this.getWorld().setBlockState(oldPos, this.getWorld().getBlockState(newPos));
            //this.getWorld().setBlockState(newPos, block.getDefaultState());
            //this.getOwner().sendMessage(Text.of("Your robot moved block from " + oldPos + " to " + newPos));

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

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(CARRIED_BLOCK, Optional.empty());
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
    public EntityView method_48926() {
        return this.getWorld();
    }

    @Nullable
    @Override
    public LivingEntity getOwner() {
        return super.getOwner();
    }


    @Override
    public SimpleInventory getInventory() {
        return this.inventory;
    }

    public ItemStack getHeldItem() {
        return this.getStackInHand(Hand.MAIN_HAND);
    }
}
