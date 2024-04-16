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

        try {
            //TODO: works 1 only on mac and renamed the from Visual Studio Code.app to VSCode.app -> did not work
            //open -a "QuickTime Player" ~/Desktop/filename.mp4 -> to open a certain file in a certain app
            String currentPath = Paths.get("").toAbsolutePath().getParent().toString(); ///.toAbsolutePath().toString();
            String pathToRacketFolder = "/src/main/resources/assets/bachelorproef/racket";
            String pathToRacketFile = "/simple_sort/lesson.rkt";
            String path = currentPath + pathToRacketFolder + pathToRacketFile;
            BachelorProef.LOGGER.info(path);


            String pathToAppleScriptFolder =  "/src/main/resources/assets/bachelorproef/AppleScript";
            String pathToScriptFile = "/test.scpt";
            String pathScript = currentPath + pathToAppleScriptFolder + pathToScriptFile;

            Process terminal = Runtime.getRuntime().exec("osascript " + pathScript + " " + path);
            String output = terminal.info().toString();
            BachelorProef.LOGGER.info(output);
            //Window activeWindow = javax.swing.FocusManager.getCurrentManager().getFocusedWindow();
            //BufferedWriter writer = terminal.outputWriter();
            //writer.write("nvim " + path + " \n");

            //BachelorProef.LOGGER.info(activeWindow.getName());
            //Runtime.getRuntime().exec("open ");
            //Process test = Runtime.getRuntime().exec("open /Applications/Visual\\ Studio\\ Code.app");
            //BachelorProef.LOGGER.info(test.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
