package siheynde.bachelorproefmod.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.entity.robot.RobotEntity;
import siheynde.bachelorproefmod.structure.shrine.Levels;
import siheynde.bachelorproefmod.structure.shrine.Shrine;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

import java.util.ArrayList;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin implements PlayerMixinInterface {
    @Shadow protected abstract void takeShieldHit(LivingEntity attacker);

    @Unique
    RobotEntity robot = null;
    RobotEntity robotTestWorld = null;
    Levels levels = new Levels();
    private ArrayList<Levels.Topic> topics = levels.getTopics();
    public ArrayList<Shrine> visitedShrines = new ArrayList<>();
    int amountShrinesUnlocked = -1;
    String runID;
    String nameShrine;
    ArrayList<String> topicNames;
    String bookID;

    @Override
    public Shrine getShrine() {
        int x = (int) ((PlayerEntity) (Object) this).getX();
        int y = (int) ((PlayerEntity) (Object) this).getY();
        int z = (int) ((PlayerEntity) (Object) this).getZ();
        BlockPos pos = new BlockPos(x, y, z);
        return  getShrine(pos);
    }

    @Override
    public void setNameShrine(String name) {
        nameShrine = name;
    }

    @Override
    public String getNameShrine() {
        return nameShrine;
    }

    @Override
    public void setRunID(String runID) {
        this.runID = runID;
    }

    @Override
    public String getRunID() {
        return runID;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void injected(CallbackInfo info) {
        //BachelorProef.LOGGER.info("added in PlayerEntity constructor");
        BlockPos pos = ((PlayerEntity) (Object) this).getBlockPos();
        //Biome world = ((PlayerEntity) (Object) this).getWorld();//.get
        //BachelorProef.LOGGER.info("World: " + world);


    }

    @Override
    public Levels.Topic getTopic() {
        return topics.get(amountShrinesUnlocked);
    }

    @Override
    public void setRobot(RobotEntity robot) {
        BachelorProef.LOGGER.info("Setting robot");
        this.robot = robot;
    }

    @Override
    public void setRobotTestWorld(RobotEntity robot) {
        this.robotTestWorld = robot;
    }

    @Override
    public RobotEntity getRobot() {
        return robot;
    }

    @Override
    public RobotEntity getRobotTestWorld() {
        return this.robotTestWorld;
    }

    //TODO: add shrine completed function to increase amountShrinesUnlocked

    public Shrine getShrine(int level) {
        return visitedShrines.get(level);
    }

    public ArrayList<Shrine> getVisitedShrines() {
        return visitedShrines;
    }

    public void setTopicNames(ArrayList<String> topicNames){this.topicNames = topicNames;};
    public ArrayList<String> getTopicNames(){return this.topicNames;};

    public void setBookID(String bookID){this.bookID = bookID;};
    public String getBookID(){return this.bookID;};

    @Override
    public Shrine getShrine(BlockPos pos) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();


        for (Shrine shrine : this.visitedShrines) {
            if (shrine.isInRange(x, y, z)) {
                return shrine;
            }
        }

        amountShrinesUnlocked = amountShrinesUnlocked + 1;
        Levels.Topic topic = topics.get(amountShrinesUnlocked);


        Shrine newShrine = new Shrine(x, y, z, topic);
        visitedShrines.add(newShrine);

        return newShrine;
    }
}
