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
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

@Mixin(PlayerEntity.class)
public class PlayerMixin implements PlayerMixinInterface {
    @Unique
    RobotEntity robot = null;

    @Inject(method = "tick", at = @At("TAIL"))
    private void injected(CallbackInfo info) {
        //BachelorProef.LOGGER.info("added in PlayerEntity constructor");
        BlockPos pos = ((PlayerEntity) (Object) this).getBlockPos();
        //Biome world = ((PlayerEntity) (Object) this).getWorld();//.get
        //BachelorProef.LOGGER.info("World: " + world);


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
}
