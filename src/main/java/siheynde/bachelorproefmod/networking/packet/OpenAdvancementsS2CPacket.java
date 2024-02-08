package siheynde.bachelorproefmod.networking.packet;

import net.minecraft.client.MinecraftClient;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class OpenAdvancementsS2CPacket {

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        // Every
        client.execute(() -> {
            // Everything in this lambda is run on the render thread
            assert client.player != null;
            client.setScreen(new AdvancementsScreen(client.player.networkHandler.getAdvancementHandler()));
        });
    }
}
