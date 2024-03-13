package siheynde.bachelorproefmod.util;

import siheynde.bachelorproefmod.entity.robot.RobotEntity;
import siheynde.bachelorproefmod.structure.shrine.Shrine;

import java.util.ArrayList;

public interface ClientPlayerMixinInterface {
    ArrayList<Shrine> getVisitedShrines();
    void addVisitedShrine(Shrine shrine);
    Shrine getShrine(int level);
    //Shrine getShrine();
}
