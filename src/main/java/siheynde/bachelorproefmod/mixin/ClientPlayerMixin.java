package siheynde.bachelorproefmod.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.structure.shrine.Levels;
import siheynde.bachelorproefmod.util.ClientPlayerMixinInterface;
import siheynde.bachelorproefmod.structure.shrine.Shrine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerMixin implements ClientPlayerMixinInterface {
    public ArrayList<Shrine> visitedShrines = new ArrayList<>();

    public Levels.Topic topic;

    private HashMap<PacketByteBuf,String> actions = new HashMap<>();

    private List<PacketByteBuf> actionsNew = new ArrayList<>();

    private int actions_count = 0;

    public Shrine getShrine(int level) {
        return visitedShrines.get(level);
    }

    @Override
    public void addAction(String action, PacketByteBuf buf) {
        actions.put(buf, action);
        actionsNew.add(buf);
        actions_count += 1;
    }

    @Override
    public List<PacketByteBuf> getActions() {
        BachelorProef.LOGGER.info("should have " + actions_count + " actions, has " + actions.size() + " actions, new edition has " + actionsNew.size() + " actions.");

        return actionsNew;
    }

    public void setTopic(Levels.Topic topic) {
        BachelorProef.LOGGER.info("setting topic... for player " + this);

        this.topic = topic;
    }

    public Levels.Topic getTopic() {
        BachelorProef.LOGGER.info("getting topic ... for player " + this);

        return this.topic;
    }

}
