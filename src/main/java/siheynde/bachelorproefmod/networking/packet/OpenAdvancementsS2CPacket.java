package siheynde.bachelorproefmod.networking.packet;

import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementManager;
import net.minecraft.advancement.PlacedAdvancement;
import net.minecraft.client.MinecraftClient;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

import java.util.Collection;

public class OpenAdvancementsS2CPacket {

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        // Every
        client.execute(() -> {
            // Everything in this lambda is run on the render thread
            assert client.player != null;
            AdvancementsScreen screen = new AdvancementsScreen(handler.getAdvancementHandler());
            client.setScreen(screen);

            //select robot tab
            Collection<PlacedAdvancement> advancements = handler.getAdvancementHandler().getManager().getAdvancements();
            advancements.forEach(advancement -> {
                AdvancementEntry entry = advancement.getAdvancementEntry();
                String id = entry.toString();
                if (id.equals("bachelorproef:course1/root")) {
                    screen.selectTab(entry);
                }
            });

        });
    }
}
