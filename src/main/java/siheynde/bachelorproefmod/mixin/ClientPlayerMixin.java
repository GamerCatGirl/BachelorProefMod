package siheynde.bachelorproefmod.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.structure.shrine.Levels;
import siheynde.bachelorproefmod.util.ClientPlayerMixinInterface;
import siheynde.bachelorproefmod.structure.shrine.Shrine;

import java.util.ArrayList;
import java.util.HashMap;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerMixin implements ClientPlayerMixinInterface {
    @Shadow private double lastX;
    @Shadow private double lastZ;
    @Shadow private double lastBaseY;
    public ArrayList<Shrine> visitedShrines = new ArrayList<>();
    private boolean locked = false;
    public String selectedSubTopic;
    public Levels.Topic topic;

    public void addVisitedShrine(Shrine shrine) {
        this.visitedShrines.add(shrine);
    }

    public Shrine getShrine(int level) {
        return visitedShrines.get(level);
    }

    public ArrayList<Shrine> getVisitedShrines() {
        return visitedShrines;
    }

    @Override
    public void addAction(String action, PacketByteBuf buf) {
        actions.put(buf, action);
    }

    @Override
    public HashMap<PacketByteBuf, String> getActions() {
        return actions;
    }

    @Override
    public Boolean isBlocked() {
        return locked;
    }

    @Override
    public void block() {
        locked = true;
    }

    @Override
    public void unblock() {
        locked = false;
    }

    public void setRunID(String subTopic) {
        this.selectedSubTopic = subTopic;
    }

    public String getRunID() {
        return this.selectedSubTopic;
    }

    public void setTopic(Levels.Topic topic) {
        BachelorProef.LOGGER.info("setting topic... for player " + this);

        this.topic = topic;
    }

    public Levels.Topic getTopic() {
        BachelorProef.LOGGER.info("getting topic ... for player " + this);

        return this.topic;
    }

    /*
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
     */


    //TODO: Save the player data --- example: how health is saved
    //this.dataTracker.set(HEALTH, Float.valueOf(MathHelper.clamp(health, 0.0f, this.getMaxHealth())));


}
