package net.viktorp04.pet_armor.client;

import net.minecraft.client.model.animal.parrot.ParrotModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

/** A light shell that follows the vanilla parrot's body, wings, and tail animations. */
public final class ParrotArmorModel extends ParrotModel {
	private static final CubeDeformation DEFORMATION = new CubeDeformation(0.15F);

	public ParrotArmorModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();
		root.addOrReplaceChild(
			"body",
			CubeListBuilder.create().texOffs(2, 8).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, DEFORMATION),
			PartPose.offsetAndRotation(0.0F, 16.5F, -3.0F, 0.4937F, 0.0F, 0.0F)
		);
		// ParrotModel requires these animated parts even though the torso-only armor leaves them uncovered.
		root.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 21.07F, 1.16F));
		root.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(1.5F, 16.94F, -2.76F));
		root.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(-1.5F, 16.94F, -2.76F));
		root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 15.69F, -2.76F));
		root.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.0F, 22.0F, -1.05F));
		root.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.0F, 22.0F, -1.05F));
		return LayerDefinition.create(mesh, 32, 32);
	}
}
