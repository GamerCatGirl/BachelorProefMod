package siheynde.bachelorproefmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

public class ExecuteRacketS2C {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {

        String What = buf.readString();

        PlayerMixinInterface playerInterface = (PlayerMixinInterface) client.player;
        String path = playerInterface.getTopic().path_rkt;
        //playerInterface.setBookID(bookID);


    }
}
