package siheynde.bachelorproefmod.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.networking.ModPackets;


@Environment(value= EnvType.CLIENT)
public class FunctionCalledByScheme {

    public void test() {
        //ClientPlayNetworking.send(ModPackets.MOVE_ROBOT, PacketByteBufs.empty());

        BachelorProef.LOGGER.info("Function called by scheme");
    }


}
