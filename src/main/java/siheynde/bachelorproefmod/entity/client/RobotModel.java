package siheynde.bachelorproefmod.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import siheynde.bachelorproefmod.entity.robot.RobotEntity;

// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class RobotModel<T extends RobotEntity> extends SinglePartEntityModel<T> {
	private final ModelPart Robot;
	//private final ModelPart head;
	private final ModelPart rightArm;
	private final ModelPart leftArm;
	public RobotModel(ModelPart root) {
		this.Robot = root.getChild("Robot");
		//this.head = root.getChild(EntityModelPartNames.HEAD);
		this.rightArm = root.getChild(EntityModelPartNames.RIGHT_ARM);
		this.leftArm = root.getChild(EntityModelPartNames.LEFT_ARM);
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData Robot = modelPartData.addChild(
				"Robot",
				ModelPartBuilder.create(),
				ModelTransform.of(1.0F, 24.0F, -1.0F, 0.0F, -1.5272F, 0.0F));

		ModelPartData Legs = modelPartData.addChild("Legs", ModelPartBuilder.create().uv(2, 4)
				.cuboid(-1.0F, 4.0F, -4.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 2).cuboid(-1.0F, 4.0F, 1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, 1.0F));

		ModelPartData Body = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 0)
				.cuboid(-4.0F, -5.0F, -5.0F, 9.0F, 9.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, 1.0F));

		ModelPartData ArmsRigth = modelPartData
				.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(0, 2)
				.cuboid(-1.0F, -0.5F, -6.5F, 2.0F, 9.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 2)
						.cuboid(-1.0F, -0.5F, 4.5F, 2.0F, 9.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -11.5F, 0.5F));
                                                                                                                                                           //duwt de arm naar achter (pivot x)
		ModelPartData ArmsLeft = modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(0, 2)
				.cuboid(-1.0F, -0.5F, -6.5F, 9.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 2)
				.cuboid(-1.0F, -0.5F, 4.5F, 9.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -11.5F, 0.5F));


		ModelPartData Antene = modelPartData.addChild("Antene", ModelPartBuilder.create().uv(0, 1).cuboid(0.0F, -7.0F, -1.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 3).cuboid(0.0F, -9.0F, -2.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, 1.0F));

		ModelPartData Energy = modelPartData.addChild("Energy", ModelPartBuilder.create().uv(0, 2).cuboid(0.0F, -11.0F, 2.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 2).cuboid(0.0F, -12.0F, 3.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 2).cuboid(0.0F, -13.0F, 4.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 2).cuboid(0.0F, -14.0F, 5.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, 1.0F));
		Robot.addChild("Legs", ModelPartBuilder.create().uv(2, 4)
				.cuboid(-1.0F, 4.0F, -4.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 2).cuboid(-1.0F, 4.0F, 1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, 1.0F));

		Robot.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 0)
				.cuboid(-4.0F, -5.0F, -5.0F, 9.0F, 9.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, 1.0F));

		Robot
				.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(0, 2)
						.cuboid(-1.0F, -0.5F, -6.5F, 2.0F, 9.0F, 2.0F, new Dilation(0.0F))
						.uv(0, 2)
						.cuboid(-1.0F, -0.5F, 4.5F, 2.0F, 9.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -11.5F, 0.5F));
		//duwt de arm naar achter (pivot x)
		Robot.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(0, 2)
				.cuboid(-1.0F, -0.5F, -6.5F, 2.0F, 9.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 2)
				.cuboid(-1.0F, -0.5F, 4.5F, 2.0F, 9.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -11.5F, 0.5F));


		Robot.addChild("Antene", ModelPartBuilder.create().uv(0, 1).cuboid(0.0F, -7.0F, -1.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 3).cuboid(0.0F, -9.0F, -2.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, 1.0F));

		Robot.addChild("Energy", ModelPartBuilder.create().uv(0, 2).cuboid(0.0F, -11.0F, 2.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
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

	public ModelPart getRightArm() {
		return this.rightArm;
	}
	@Override
	public ModelPart getPart() {
		return this.Robot;
	}
}