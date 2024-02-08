package siheynde.bachelorproefmod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import siheynde.bachelorproefmod.entity.ModEntities;
import siheynde.bachelorproefmod.entity.client.ModModelLayers;
import siheynde.bachelorproefmod.entity.client.RobotModel;
import siheynde.bachelorproefmod.entity.client.RobotRenderer;
import siheynde.bachelorproefmod.networking.ModPackets;

public class BachelorProefClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.register(ModEntities.ROBOT, RobotRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.ROBOT, RobotModel::getTexturedModelData);
        //ModPackets.registerC2SPackets();
    }
}
