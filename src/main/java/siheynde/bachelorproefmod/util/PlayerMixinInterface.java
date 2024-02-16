package siheynde.bachelorproefmod.util;

import siheynde.bachelorproefmod.structure.shrine.Shrine;

import java.util.ArrayList;

public interface PlayerMixinInterface {
    ArrayList<Shrine> getVisitedShrines();
    void addVisitedShrine(Shrine shrine);
    Shrine getShrine(int level);
    Shrine getShrine();


}
