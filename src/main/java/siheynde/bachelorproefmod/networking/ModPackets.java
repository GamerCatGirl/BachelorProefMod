package siheynde.bachelorproefmod.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.networking.packet.*;

public class ModPackets {

    public static final Identifier OPEN_ADVANCEMENTS_ID = new Identifier(BachelorProef.MOD_ID, "open_advancements");
    public static final Identifier MOVE_ROBOT = new Identifier(BachelorProef.MOD_ID, "move_robot");
    public static final Identifier SET_RUN_ID = new Identifier(BachelorProef.MOD_ID, "run_id");

    public static final Identifier GET_RUN_ID = new Identifier(BachelorProef.MOD_ID, "get_run_id");
    public static final Identifier GET_RUN_ID_RESPONSE = new Identifier(BachelorProef.MOD_ID, "get_run_id_response");
    public static final Identifier GET_NAME_SHRINE = new Identifier(BachelorProef.MOD_ID, "get_name_shrine");
    public static final Identifier GET_NAME_SHRINE_RESPONSE = new Identifier(BachelorProef.MOD_ID, "get_name_shrine_response");
    public static final Identifier GET_TOPIC_NAMES = new Identifier(BachelorProef.MOD_ID, "get_topic_names");
    public static final Identifier GET_TOPIC_NAMES_RESPONSE = new Identifier(BachelorProef.MOD_ID, "get_topic_names_response");
    public static final Identifier GET_BOOK_ID = new Identifier(BachelorProef.MOD_ID, "get_book_id");
    public static final Identifier GET_BOOK_ID_RESPONSE = new Identifier(BachelorProef.MOD_ID, "get_book_id_response");
    public static final Identifier EXECUTE_FUNCTION = new Identifier(BachelorProef.MOD_ID, "execute_function");
    public static final Identifier SET_BLOCK_VISUALISATION = new Identifier(BachelorProef.MOD_ID, "set_block_visualisation");
    public static final Identifier LET_LOOP = new Identifier(BachelorProef.MOD_ID, "let_loop");
    public static final Identifier OPEN_TERMINAL = new Identifier(BachelorProef.MOD_ID, "open_terminal");
    public static final Identifier SET_LINE_TERMINAL = new Identifier(BachelorProef.MOD_ID, "set_line_terminal");

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(OPEN_ADVANCEMENTS_ID, OpenAdvancementsS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(GET_RUN_ID_RESPONSE, GetRunIDS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(GET_NAME_SHRINE_RESPONSE, GetNameShrineS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(GET_TOPIC_NAMES_RESPONSE, GetTopicNamesS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(GET_BOOK_ID_RESPONSE, GetBookIDS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(OPEN_TERMINAL, OpenTerminalS2C::receive);
        ClientPlayNetworking.registerGlobalReceiver(SET_LINE_TERMINAL, SetLineS2C::receive);
    }

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(MOVE_ROBOT, MoveRobotC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(SET_RUN_ID, runIDC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(GET_RUN_ID, GetRunIDC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(GET_NAME_SHRINE, GetNameShrineC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(GET_TOPIC_NAMES, GetTopicNamesC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(GET_BOOK_ID, GetBookIDC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(EXECUTE_FUNCTION, ExecuteFunctionC2S::receive);
        ServerPlayNetworking.registerGlobalReceiver(SET_BLOCK_VISUALISATION, SetBlockVisualisationC2S::receive);
        ServerPlayNetworking.registerGlobalReceiver(LET_LOOP, letLoopC2S::receive);
    }
}
