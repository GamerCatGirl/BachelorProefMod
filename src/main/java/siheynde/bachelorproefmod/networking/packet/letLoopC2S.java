package siheynde.bachelorproefmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.structure.shrine.Shrine;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

import java.util.List;

public class letLoopC2S {

        public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                                   PacketByteBuf buf, PacketSender responseSender) {

            PlayerMixinInterface playerInterface = (PlayerMixinInterface) player;
            String functionName = buf.readString();
            Shrine shrine = playerInterface.getShrine();
            String lookFor = "(let-loop '" + functionName;
            List<String> activatedLoops = shrine.activatedLoops;
            if (activatedLoops.contains(functionName)) {
                shrine.LookInCompleteFunction = true;
                shrine.indexInFunction = 0;
                shrine.findOccurrence(lookFor, "'", player);
            } else {
                shrine.findOccurrence(lookFor, "'", player);
                shrine.activatedLoops.add(functionName);
            }
        }
}
