package siheynde.bachelorproefmod.entity.client;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import siheynde.bachelorproefmod.BachelorProef;

public class ModModelLayers {
    public static final EntityModelLayer ROBOT =
            new EntityModelLayer(new Identifier(BachelorProef.MOD_ID, "robot"), "main");
}
