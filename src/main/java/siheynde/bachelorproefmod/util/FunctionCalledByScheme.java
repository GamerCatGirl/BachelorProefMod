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
import java.util.List;
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
        buf.writeString("setBlock");
        buf.writeString(blockName);
        buf.writeInt(toPosition);

        ClientPlayNetworking.send(ModPackets.SAVE_ACTION, buf);

        playerInterface.addAction("setBlock", buf); //TODO: send to server!
    }

    public void getBlock(Integer toPosition) {
        BachelorProef.LOGGER.info("Function getBlock " + toPosition + " place");

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString("getBlock");
        buf.writeInt(toPosition);

        ClientPlayNetworking.send(ModPackets.SAVE_ACTION, buf);

        playerInterface.addAction("getBlock", buf); //TODO: send to server!
    }

    public void letLoop(String loopname) {
        BachelorProef.LOGGER.info("Function letLoop " + loopname);
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString("letLoop");
        buf.writeString(loopname);

        ClientPlayNetworking.send(ModPackets.SAVE_ACTION, buf);

        playerInterface.addAction("letLoop", buf); // TODO: send to server!
    }

    public void done() {
        PacketByteBuf buf = PacketByteBufs.create();
        ClientPlayNetworking.send(ModPackets.ACTIONS, buf);

        //TODO: delete under this line
        /*List<PacketByteBuf> actions = playerInterface.getActions();
        //BachelorProef.LOGGER.info("actions: " + actions.toString());

        for (PacketByteBuf entry : actions) {
            String action = entry.readString();
            BachelorProef.LOGGER.info(action);
            switch (entry.readString()) {
                case "setBlock" -> {
                    String blockName = entry.readString();
                    int toPosition = entry.readInt();
                    //String blockName = entry.getKey().readString();
                    //Integer toPosition = entry.getKey().readInt();
                    buf.writeString("setBlock");
                    buf.writeString(blockName);
                    buf.writeInt(toPosition);
                }
                case "getBlock" -> {
                    int toPosition = entry.readInt();
                    buf.writeString("getBlock");
                    buf.writeInt(toPosition);
                }
                case "letLoop" -> {
                    String loopName = buf.readString();
                    buf.writeString("letLoop");
                    buf.writeString(loopName);
                }
            }
        };

        ClientPlayNetworking.send(ModPackets.ACTIONS, buf);

         */

    }

}
