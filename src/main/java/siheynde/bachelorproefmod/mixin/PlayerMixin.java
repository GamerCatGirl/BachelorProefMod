package siheynde.bachelorproefmod.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.entity.robot.RobotEntity;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

@Mixin(PlayerEntity.class)
public class PlayerMixin implements PlayerMixinInterface {
    @Unique
    RobotEntity robot = null;
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
