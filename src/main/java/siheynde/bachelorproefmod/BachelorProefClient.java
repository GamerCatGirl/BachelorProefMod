package siheynde.bachelorproefmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import siheynde.bachelorproefmod.entity.ModEntities;
import siheynde.bachelorproefmod.entity.client.ModModelLayers;
import siheynde.bachelorproefmod.entity.client.RobotModel;
import siheynde.bachelorproefmod.entity.client.RobotRenderer;
import siheynde.bachelorproefmod.networking.ModPackets;
import siheynde.bachelorproefmod.screen.FunctionScreen;
import siheynde.bachelorproefmod.screen.ModScreenHandlers;
import siheynde.bachelorproefmod.screen.TestScreen;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
//import io.wispforest.lavender

public class BachelorProefClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.register(ModEntities.ROBOT, RobotRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.ROBOT, RobotModel::getTexturedModelData);
        ModPackets.registerS2CPackets();


        HandledScreens.register(ModScreenHandlers.FUNCTION_SCREEN, FunctionScreen::new);
        HandledScreens.register(ModScreenHandlers.TEST_SCREEN, TestScreen::new);

    }
}
