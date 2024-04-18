package siheynde.bachelorproefmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.networking.ModPackets;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

public class GetNameShrineC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {


        PlayerMixinInterface playerInterface = (PlayerMixinInterface) player;
        String name = playerInterface.getShrine().getName();

        BachelorProef.LOGGER.info("Getting name shrine packet: " + name);

        PacketByteBuf packetByteBuf = PacketByteBufs.create();
        packetByteBuf.writeString(name);

        responseSender.sendPacket(ModPackets.GET_NAME_SHRINE_RESPONSE, packetByteBuf);

    }
}
