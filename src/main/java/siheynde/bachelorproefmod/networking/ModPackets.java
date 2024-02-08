package siheynde.bachelorproefmod.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.networking.packet.OpenAdvancementsS2CPacket;

public class ModPackets {

    public static final Identifier OPEN_ADVANCEMENTS = new Identifier(BachelorProef.MOD_ID, "open_advancements");

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(OPEN_ADVANCEMENTS, OpenAdvancementsS2CPacket::receive);
    }
}
