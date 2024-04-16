package siheynde.bachelorproefmod.util;

import siheynde.bachelorproefmod.entity.robot.RobotEntity;
import siheynde.bachelorproefmod.structure.shrine.Levels;
import siheynde.bachelorproefmod.structure.shrine.Shrine;

import java.util.ArrayList;

public interface ClientPlayerMixinInterface {
    ArrayList<Shrine> getVisitedShrines();
    void addVisitedShrine(Shrine shrine);
    Shrine getShrine(int level);
    //Shrine getShrine();
    void setSelectedSubTopic(String subTopic);
    String getSelectedSubTopic();

    void setTopic(Levels.Topic topic);
    Levels.Topic getTopic();
}
