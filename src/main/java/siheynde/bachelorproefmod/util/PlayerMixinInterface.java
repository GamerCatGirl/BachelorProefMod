package siheynde.bachelorproefmod.util;

import siheynde.bachelorproefmod.mixin.Shrine;

import java.util.ArrayList;

public interface PlayerMixinInterface {
    ArrayList<Shrine> getVisitedShrines();
    void addVisitedShrine(Shrine shrine);
    Shrine getShrine(int level);


}
