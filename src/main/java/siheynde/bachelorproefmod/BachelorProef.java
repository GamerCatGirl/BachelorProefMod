package siheynde.bachelorproefmod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import siheynde.bachelorproefmod.block.ModBlocks;
import siheynde.bachelorproefmod.block.entity.ModBlockEntities;
import siheynde.bachelorproefmod.entity.ModEntities;
import siheynde.bachelorproefmod.entity.robot.RobotEntity;
import siheynde.bachelorproefmod.item.ModItemGroups;
import siheynde.bachelorproefmod.item.ModItems;
import siheynde.bachelorproefmod.networking.ModPackets;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;

public class BachelorProef implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "bachelorproef";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModItemGroups.registerItemGroups();
		ModItems.registerItems();
		ModBlocks.registerModBlocks();
		//ModStructureType.registerStructures();
		//ModPackets.registerS2CPackets();
		ModPackets.registerC2SPackets();
		ModBlockEntities.registerBlockEntities();

		LOGGER.info("Hello Fabric world!");

		FabricDefaultAttributeRegistry.register(ModEntities.ROBOT, RobotEntity.createRobotAttributes());

		CustomPortalBuilder.beginPortal()
				.frameBlock(Blocks.IRON_BLOCK)
				.lightWithItem(Items.IRON_INGOT)
				.destDimID(new Identifier(BachelorProef.MOD_ID, "bachelor_dimension"))
				.tintColor(0xc76efa)
				.registerPortal();

	}
}