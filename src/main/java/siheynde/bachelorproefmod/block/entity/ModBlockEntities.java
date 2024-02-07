package siheynde.bachelorproefmod.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.block.ModBlocks;

public class ModBlockEntities {
    /*public static final BlockEntityType<FunctionBlockEntity> FUNCTION_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(BachelorProef.MOD_ID, "function_block"),
                    FabricBlockEntityTypeBuilder.create(FunctionBlockEntity::new,
                            ModBlocks.FUNCTION_BLOCK).build());
*/
    public static void registerBlockEntities() {
        BachelorProef.LOGGER.info("Registering Mod Block Entities for " + BachelorProef.MOD_ID);
    }
}
