package siheynde.bachelorproefmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.networking.ModPackets;
import siheynde.bachelorproefmod.structure.functions.SubTopic;
import siheynde.bachelorproefmod.structure.shrine.Levels;
import siheynde.bachelorproefmod.structure.shrine.Shrine;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

public class ExecuteFunctionC2S {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {

        PlayerMixinInterface playerInterface = (PlayerMixinInterface) player;
        String runID = buf.readString();
        Shrine shrine = playerInterface.getShrine();
        Levels.Topic topic = shrine.topic;
        String subTopic = playerInterface.getRunID();
        SubTopic functions = topic.getFunctions(subTopic);

        if (runID.equals("Predict")) {
            functions.runPredict();
        } else if (runID.equals("Run")) {
            functions.runRun();
        } else if (runID.equals("Investigate")) {
            functions.runInvestigate();
        } else if (runID.equals("Modify")) {
            functions.runModify();
        } else if (runID.equals("Make")) {
            functions.runMake();
        }

    }
}
