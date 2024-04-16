package siheynde.bachelorproefmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.networking.ModPackets;
import siheynde.bachelorproefmod.util.ClientPlayerMixinInterface;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

public class GetRunIDC2SPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {


        PlayerMixinInterface playerInterface = (PlayerMixinInterface) player;
        String idRun = playerInterface.getRunID();

        BachelorProef.LOGGER.info("Getting runID packet with id: " + idRun);

        PacketByteBuf packetByteBuf = PacketByteBufs.create();
        packetByteBuf.writeString(idRun);

        responseSender.sendPacket(ModPackets.GET_RUN_ID_RESPONSE, packetByteBuf);

    }

}
