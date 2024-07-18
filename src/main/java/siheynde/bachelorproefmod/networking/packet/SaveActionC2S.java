package siheynde.bachelorproefmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import siheynde.bachelorproefmod.util.Action;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

public class SaveActionC2S {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {

        String actionName = buf.readString();

        Action action;

        switch (actionName) {
            case "setBlock" -> {
                String blockName = buf.readString();
                int toPosition = buf.readInt();
                action = new Action(blockName, toPosition);
            }
            case "getBlock" -> {
                int toPosition = buf.readInt();
                action = new Action(toPosition);
            }
            case "letLoop" -> {
                String loopName = buf.readString();
                action = new Action(loopName);
            }
            default -> {throw new IllegalStateException("Action unknown: " + actionName);}
        }

        PlayerMixinInterface playerInterface = (PlayerMixinInterface) player;
        playerInterface.addAction(action);


    }
}
