package siheynde.bachelorproefmod.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
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
public class PlayerMixin implements PlayerMixinInterface {
    @Unique
    RobotEntity robot = null;
    //Levels levels = new Levels();
    private ArrayList<Levels.Topic> topics = Levels.topics;
    int amountShrinesUnlocked = 0;
    int runID;

    @Override
    public void setRunID(int runID) {
        this.runID = runID;
    }

    @Override
    public int getRunID() {
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
    public RobotEntity getRobot() {
        return robot;
    }

    public ArrayList<Shrine> visitedShrines = new ArrayList<>();

    public void addVisitedShrine(Shrine shrine) {
        this.visitedShrines.add(shrine);
    }

    //TODO: add shrine completed function to increase amountShrinesUnlocked

    public Shrine getShrine(int level) {
        return visitedShrines.get(level);
    }

    public ArrayList<Shrine> getVisitedShrines() {
        return visitedShrines;
    }

    @Override
    public Shrine getShrine(BlockPos pos) {

        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();

        for (Shrine shrine : visitedShrines) {
            if (shrine.isInRange(x, y, z)) {
                return shrine;
            }
        }

        int level = visitedShrines.size();
        Shrine newShrine = new Shrine(x, y, z, level);
        addVisitedShrine(newShrine);
        return newShrine;
    }
}
