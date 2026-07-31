package net.viktorp04.pet_armor.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityRenderLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.animal.feline.AdultCatModel;
import net.minecraft.client.model.animal.feline.AdultFelineModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.world.entity.EntityTypes;
import net.viktorp04.pet_armor.PetArmor;

public class PetArmorClient implements ClientModInitializer {
	public static final ModelLayerLocation CAT_ARMOR_MODEL_LAYER = new ModelLayerLocation(PetArmor.id("cat_armor"), "main");
	public static final ModelLayerLocation PARROT_ARMOR_MODEL_LAYER = new ModelLayerLocation(PetArmor.id("parrot_armor"), "main");

	@Override
	public void onInitializeClient() {
		ModelLayerRegistry.registerModelLayer(
			CAT_ARMOR_MODEL_LAYER,
			() -> LayerDefinition.create(AdultFelineModel.createBodyMesh(new CubeDeformation(0.2F)), 64, 32)
				.apply(AdultCatModel.CAT_TRANSFORMER)
		);
		ModelLayerRegistry.registerModelLayer(PARROT_ARMOR_MODEL_LAYER, ParrotArmorModel::createBodyLayer);
		LivingEntityRenderLayerRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
			if (entityType == EntityTypes.CAT && entityRenderer instanceof CatRenderer catRenderer) {
				registrationHelper.register(new CatArmorLayer(catRenderer, context.getModelSet()));
			}
			if (entityType == EntityTypes.PARROT && entityRenderer instanceof ParrotRenderer parrotRenderer) {
				registrationHelper.register(new ParrotArmorLayer(parrotRenderer, context.getModelSet()));
			}
		});
	}
}
