package siheynde.bachelorproefmod.util;

import ca.weblite.objc.Client;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.networking.ModPackets;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;
import static java.util.concurrent.Executors.newSingleThreadExecutor;


@Environment(value= EnvType.CLIENT)
public class FunctionCalledByScheme {
    //TODO:  send op het einde naar server in lijst, dan geen sync nodig tussen client en server
    MinecraftClient client = MinecraftClient.getInstance();
    ClientPlayerMixinInterface playerInterface = (ClientPlayerMixinInterface) client.player;

    public void setBlock(String blockName, Integer toPosition) {
        BachelorProef.LOGGER.info("Function setBlock " + blockName + " on " + toPosition + " place");

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(blockName);
        buf.writeInt(toPosition);
        playerInterface.addAction("setBlock", buf);
        //actions.put("setBlock", buf);
        //ClientPlayNetworking.send(ModPackets.SET_BLOCK_VISUALISATION, buf);
    }

    public void getBlock(Integer toPosition) {
        BachelorProef.LOGGER.info("Function getBlock " + toPosition + " place");

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(toPosition);
        playerInterface.addAction("getBlock", buf);
        //actions.put("getBlock", buf);
        //ClientPlayNetworking.send(ModPackets.GET_BLOCK_VISUALISATION, buf); //TODO: do this on server

    }

    public void letLoop(String loopname) {
        BachelorProef.LOGGER.info("Function letLoop " + loopname);
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(loopname);
        playerInterface.addAction("letLoop", buf);
    }

    public void done() {
        PacketByteBuf buf = PacketByteBufs.create();
        HashMap<PacketByteBuf, String> actions = playerInterface.getActions();
        BachelorProef.LOGGER.info(actions.toString());
        for (Map.Entry<PacketByteBuf, String> entry : actions.entrySet()) {
            switch (entry.getValue()) {
                case "setBlock" -> {
                    String blockName = entry.getKey().readString();
                    Integer toPosition = entry.getKey().readInt();
                    buf.writeString(entry.getValue());
                    buf.writeString(blockName);
                    buf.writeInt(toPosition);
                }
                case "getBlock" -> {
                    Integer toPosition = entry.getKey().readInt();
                    buf.writeString(entry.getValue());
                    buf.writeInt(toPosition);
                }
                case "letLoop" -> {
                    String loopName = entry.getKey().readString();
                    buf.writeString(entry.getValue());
                    buf.writeString(loopName);
                }
            }
        }
        //TODO: send each action to server
        ClientPlayNetworking.send(ModPackets.ACTIONS, buf);

    }

}
