package siheynde.bachelorproefmod.util;

import net.minecraft.network.PacketByteBuf;
import siheynde.bachelorproefmod.entity.robot.RobotEntity;
import siheynde.bachelorproefmod.structure.shrine.Levels;
import siheynde.bachelorproefmod.structure.shrine.Shrine;

import java.util.ArrayList;
import java.util.HashMap;

public interface ClientPlayerMixinInterface {
    ArrayList<Shrine> getVisitedShrines();

    HashMap<PacketByteBuf,String> actions = new HashMap<>();

    void addAction(String action, PacketByteBuf buf);

    HashMap<PacketByteBuf, String> getActions();

    Boolean isBlocked();
    void block();
    void unblock();

    void addVisitedShrine(Shrine shrine);
    Shrine getShrine(int level);
    //Shrine getShrine();
    void setRunID(String subTopic);
    String getRunID();

    void setTopic(Levels.Topic topic);
    Levels.Topic getTopic();
}
