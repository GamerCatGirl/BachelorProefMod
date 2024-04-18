package siheynde.bachelorproefmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

import java.util.ArrayList;

public class GetTopicNamesS2CPacket {

    //TODO: Implement this method
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {

        BachelorProef.LOGGER.info("Getting topic names packet S -> C");
        int size = buf.readVarInt();
        ArrayList<String> subTopics = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            String key = buf.readString();
            subTopics.add(key);
        }

        System.out.println("Subtopics: " + subTopics);

        PlayerMixinInterface playerInterface = (PlayerMixinInterface) client.player;
        playerInterface.setTopicNames(subTopics);

    }
}
