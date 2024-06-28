package siheynde.bachelorproefmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.option.GameOptions;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import siheynde.bachelorproefmod.BachelorProef;

import java.nio.file.Paths;

import static oshi.util.Util.sleep;

public class SetLineS2C {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        try {
            Integer line = buf.readInt();
            BachelorProef.LOGGER.info("Received packet to set line");
            //open -a "QuickTime Player" ~/Desktop/filename.mp4 -> to open a certain file in a certain app
            String currentPath = Paths.get("").toAbsolutePath().getParent().toString(); ///.toAbsolutePath().toString();
            String pathToAppleScriptFolder =  "/src/main/resources/assets/bachelorproef/AppleScript";

            String pathToScriptFile = "/setLine.scpt";
            String pathScript = currentPath + pathToAppleScriptFolder + pathToScriptFile;

            Process terminal = Runtime.getRuntime().exec("osascript " + pathScript + " " + line);
            if (MinecraftClient.getInstance().isPaused()) { //closes pause screen
                MinecraftClient.getInstance().setScreen(null);
                MinecraftClient.getInstance().mouse.lockCursor();
            }
            Text text = Text.of("Set line to: " + line);
            client.player.sendMessage(text);
            terminal.waitFor();
            client.player.sendMessage(Text.of("Set line done!"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
