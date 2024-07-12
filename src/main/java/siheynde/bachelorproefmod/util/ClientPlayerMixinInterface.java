package siheynde.bachelorproefmod.util;

import net.minecraft.network.PacketByteBuf;
import siheynde.bachelorproefmod.entity.robot.RobotEntity;
import siheynde.bachelorproefmod.structure.shrine.Levels;
import siheynde.bachelorproefmod.structure.shrine.Shrine;

import java.util.ArrayList;
import java.util.HashMap;

public interface ClientPlayerMixinInterface {
    HashMap<PacketByteBuf,String> actions = new HashMap<>();
    void addAction(String action, PacketByteBuf buf);
    HashMap<PacketByteBuf, String> getActions();
    Shrine getShrine(int level);
    void setTopic(Levels.Topic topic);
    Levels.Topic getTopic();
}
