package siheynde.bachelorproefmod.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.networking.packet.MoveRobotC2SPacket;
import siheynde.bachelorproefmod.networking.packet.OpenAdvancementsS2CPacket;

public class ModPackets {

    public static final Identifier OPEN_ADVANCEMENTS_ID = new Identifier(BachelorProef.MOD_ID, "open_advancements");
    public static final Identifier MOVE_ROBOT = new Identifier(BachelorProef.MOD_ID, "move_robot");


    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(OPEN_ADVANCEMENTS_ID, OpenAdvancementsS2CPacket::receive);

    }

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(MOVE_ROBOT, MoveRobotC2SPacket::receive);
    }
}
