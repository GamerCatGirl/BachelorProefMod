package siheynde.bachelorproefmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.profiling.jfr.sample.NetworkIoStatistics;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.networking.ModPackets;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

public class runIDC2SPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {

        String idRun = buf.readString();
        BachelorProef.LOGGER.info("Received runID packet with id: " + idRun);

        PlayerMixinInterface playerInterface = (PlayerMixinInterface) player;
        playerInterface.setRunID(idRun);

        PacketByteBuf bufResponse = PacketByteBufs.create().writeString(idRun);

        responseSender.sendPacket(ModPackets.GET_RUN_ID_RESPONSE, bufResponse);
    }

}
