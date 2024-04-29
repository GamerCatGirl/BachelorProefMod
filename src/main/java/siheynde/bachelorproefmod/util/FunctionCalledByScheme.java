package siheynde.bachelorproefmod.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.networking.ModPackets;

import static java.lang.Thread.sleep;


@Environment(value= EnvType.CLIENT)
public class FunctionCalledByScheme {

    public void setBlock(String blockName, Integer toPosition) {
        BachelorProef.LOGGER.info("Function setBlock " + blockName + " on " + toPosition + " place");

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(blockName);
        buf.writeInt(toPosition);
        ClientPlayNetworking.send(ModPackets.SET_BLOCK_VISUALISATION, buf);
    }

    public void getBlock(Integer toPosition) {
        BachelorProef.LOGGER.info("Function getBlock " + toPosition + " place");

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(toPosition);
        ClientPlayNetworking.send(ModPackets.GET_BLOCK_VISUALISATION, buf);

    }

    public void letLoop(String loopname) {
        BachelorProef.LOGGER.info("Function letLoop " + loopname);

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(loopname);
        ClientPlayNetworking.send(ModPackets.LET_LOOP, buf);
    }

}
