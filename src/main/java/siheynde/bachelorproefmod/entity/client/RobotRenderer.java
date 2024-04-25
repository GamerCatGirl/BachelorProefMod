package siheynde.bachelorproefmod.entity.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.entity.robot.RobotEntity;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.util.Hand;

public class RobotRenderer extends MobEntityRenderer<RobotEntity, RobotModel<RobotEntity>> {
    private static final Identifier TEXTURE = new Identifier(BachelorProef.MOD_ID, "textures/entity/robot.png");
    public RobotRenderer(EntityRendererFactory.Context context) {
        super(context, new RobotModel<>(context.getPart(ModModelLayers.ROBOT)), 0.6f);
    }

    @Override
    public Identifier getTexture(RobotEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(RobotEntity entity, float f, float g, MatrixStack matrixStack,
        VertexConsumerProvider vertexConsumerProvider, int i) {

        if(entity.isBaby()){
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(2f, 2f, 2f);
        }

        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);

        if (entity.getHeldItem() != null) {
            renderItemInHand(matrixStack, vertexConsumerProvider, i, entity);
        }
    }

    private void renderItemInHand(MatrixStack matrixStack, VertexConsumerProvider buffer, int packedLight, RobotEntity entity) {
        // Determine the position and rotation of the hand
        // For example, you might use a combination of entity's position, rotation, and hand position

        // Use Minecraft's rendering utilities to render the item
        // For example:
        MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getHeldItem(), ModelTransformationMode.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.DEFAULT_UV, matrixStack, buffer, null, 0);
    }
}
