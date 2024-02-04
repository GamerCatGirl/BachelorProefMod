package siheynde.bachelorproefmod.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.entity.robot.RobotEntity;

public class ModEntities {
    public static final EntityType<RobotEntity> ROBOT = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(BachelorProef.MOD_ID, "robot"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RobotEntity::new).dimensions(EntityDimensions.fixed(1f, 1f)).build()
    );
}
