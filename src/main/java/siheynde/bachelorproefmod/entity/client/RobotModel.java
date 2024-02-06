package siheynde.bachelorproefmod.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import siheynde.bachelorproefmod.entity.robot.RobotEntity;

// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class RobotModel<T extends RobotEntity> extends SinglePartEntityModel<T> {
	private final ModelPart Robot;
	public RobotModel(ModelPart root) {
		this.Robot = root.getChild("Robot");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData Robot = modelPartData.addChild("Robot", ModelPartBuilder.create(), ModelTransform.of(1.0F, 24.0F, -1.0F, 0.0F, -1.5272F, 0.0F));

		ModelPartData Legs = Robot.addChild("Legs", ModelPartBuilder.create().uv(2, 4).cuboid(-1.0F, 4.0F, -4.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 2).cuboid(-1.0F, 4.0F, 1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, 1.0F));

		ModelPartData Body = Robot.addChild("Body", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -5.0F, -5.0F, 9.0F, 9.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, 1.0F));

		ModelPartData Arms = Robot.addChild("Arms", ModelPartBuilder.create().uv(0, 2).cuboid(-1.0F, -0.5F, -6.5F, 2.0F, 9.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 2).cuboid(-1.0F, -0.5F, 4.5F, 2.0F, 9.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -11.5F, 0.5F));

		ModelPartData Antene = Robot.addChild("Antene", ModelPartBuilder.create().uv(0, 1).cuboid(0.0F, -7.0F, -1.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 3).cuboid(0.0F, -9.0F, -2.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, 1.0F));

		ModelPartData Energy = Robot.addChild("Energy", ModelPartBuilder.create().uv(0, 2).cuboid(0.0F, -11.0F, 2.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 2).cuboid(0.0F, -12.0F, 3.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 2).cuboid(0.0F, -13.0F, 4.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 2).cuboid(0.0F, -14.0F, 5.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, 1.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(RobotEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		Robot.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return this.Robot;
	}
}