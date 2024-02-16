package siheynde.bachelorproefmod.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;
import siheynde.bachelorproefmod.structure.shrine.Shrine;

import java.util.ArrayList;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerMixin implements PlayerMixinInterface {
    @Shadow private double lastX;
    @Shadow private double lastZ;
    @Shadow private double lastBaseY;
    public ArrayList<Shrine> visitedShrines = new ArrayList<>();

    public void addVisitedShrine(Shrine shrine) {
        this.visitedShrines.add(shrine);
    }

    public Shrine getShrine(int level) {
        return visitedShrines.get(level);
    }

    public ArrayList<Shrine> getVisitedShrines() {
        return visitedShrines;
    }

    public Shrine getShrine() {
        double x = this.lastX;
        double y = this.lastBaseY;
        double z = this.lastZ;

        for (Shrine shrine : visitedShrines) {
            if (shrine.isInRange(x, y, z)) {
                return shrine;
            }
        }

        int level = visitedShrines.size();
        Shrine newShrine = new Shrine(x, y, z, level);
        addVisitedShrine(newShrine);
        return newShrine;
    }

    //TODO: Save the player data --- example: how health is saved
    //this.dataTracker.set(HEALTH, Float.valueOf(MathHelper.clamp(health, 0.0f, this.getMaxHealth())));


}
