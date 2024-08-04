package siheynde.bachelorproefmod.util;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import siheynde.bachelorproefmod.BachelorProef;

import java.nio.file.Paths;

public class TerminalActions {
    public static void openCode() {
        try {
            BachelorProef.LOGGER.info("Received packet to open terminal");
            //open -a "QuickTime Player" ~/Desktop/filename.mp4 -> to open a certain file in a certain app
            String currentPath = Paths.get("").toAbsolutePath().getParent().toString(); ///.toAbsolutePath().toString();
            String pathToAppleScriptFolder =  "/src/main/resources/assets/bachelorproef/AppleScript";

            String pathToRacketFolder = "/src/main/resources/assets/bachelorproef/racket";
            String pathToRacketFile = "/simple_sort/lesson.rkt";
            String pathRacket = currentPath + pathToRacketFolder + pathToRacketFile;

            ///Users/silkenheynderickx/Documents/GitHub/bachelorproefMod-1.20.4/src/main/resources/assets/bachelorproef/AppleScript/openFileNvim.scpt
            String pathToScriptFile = "/openFileNvim.scpt";
            String pathScript = currentPath + pathToAppleScriptFolder + pathToScriptFile;

            Process terminal = Runtime.getRuntime().exec("osascript " + pathScript + " " + pathRacket);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
