package siheynde.bachelorproefmod.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

import java.util.ArrayList;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerMixin implements PlayerMixinInterface {
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

    public Shrine getShrine(int x, int y) {
        for (Shrine shrine : visitedShrines) {
            if (shrine.x == x && shrine.y == y) {
                return shrine;
            }
        }
        Shrine newShrine = new Shrine(x, y, 0);
        this.
        addVisitedShrine(newShrine);
        return newShrine;
    }

    //TODO: Save the player data --- example: how health is saved
    //this.dataTracker.set(HEALTH, Float.valueOf(MathHelper.clamp(health, 0.0f, this.getMaxHealth())));


}
