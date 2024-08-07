package siheynde.bachelorproefmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

public class GetNameShrineS2CPacket {

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {

        String name = buf.readString();

        PlayerMixinInterface playerInterface = (PlayerMixinInterface) client.player;
        playerInterface.setNameShrine(name);

        client.player.sendMessage(Text.of(name + " shrine unlocked!"));

    }

}
