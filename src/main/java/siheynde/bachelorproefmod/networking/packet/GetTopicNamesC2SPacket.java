package siheynde.bachelorproefmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.Block;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.networking.ModPackets;
import siheynde.bachelorproefmod.screen.FunctionScreen;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

import java.util.Hashtable;

public class GetTopicNamesC2SPacket {

    //TODO: Implement this method
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {

        BachelorProef.LOGGER.info("Getting topic names packet C -> S");

        PlayerMixinInterface playerInterface = (PlayerMixinInterface) player;
        Hashtable<String, Hashtable<String, Hashtable<BlockPos, Block>>> topics = playerInterface.getShrine().topic.blocks;
        int size = topics.size();

        PacketByteBuf packetByteBuf = PacketByteBufs.create();
        packetByteBuf.writeVarInt(size);

        topics.forEach((key, value) -> {
            packetByteBuf.writeString(key);
        });

        responseSender.sendPacket(ModPackets.GET_TOPIC_NAMES_RESPONSE, packetByteBuf);

    }
}
