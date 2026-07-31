package net.viktorp04.pet_armor.client;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.animal.feline.AbstractFelineModel;
import net.minecraft.client.model.animal.feline.AdultCatModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.CatRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Crackiness;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.viktorp04.pet_armor.PetArmor;
import net.viktorp04.pet_armor.item.ModItemTags;
import net.viktorp04.pet_armor.item.ModItems;

public final class CatArmorLayer extends RenderLayer<CatRenderState, AbstractFelineModel<CatRenderState>> {
	private static final Map<Crackiness.Level, Identifier> ARMOR_CRACK_LOCATIONS = Map.of(
		Crackiness.Level.LOW, PetArmor.id("textures/entity/equipment/cat_body/cat_armor_crackiness_low.png"),
		Crackiness.Level.MEDIUM, PetArmor.id("textures/entity/equipment/cat_body/cat_armor_crackiness_medium.png"),
		Crackiness.Level.HIGH, PetArmor.id("textures/entity/equipment/cat_body/cat_armor_crackiness_high.png")
	);
	private static final Map<Item, Identifier> ARMOR_TEXTURES = Map.of(
		ModItems.LEATHER_WOLF_ARMOR, texture("leather_pet_armor"),
		ModItems.COPPER_WOLF_ARMOR, texture("copper_pet_armor"),
		ModItems.IRON_WOLF_ARMOR, texture("iron_pet_armor"),
		ModItems.CHAINMAIL_WOLF_ARMOR, texture("chainmail_pet_armor"),
		ModItems.GOLDEN_WOLF_ARMOR, texture("golden_pet_armor"),
		ModItems.DIAMOND_WOLF_ARMOR, texture("diamond_pet_armor"),
		ModItems.NETHERITE_WOLF_ARMOR, texture("netherite_pet_armor")
	);

	private final AdultCatModel armorModel;

	public CatArmorLayer(
		RenderLayerParent<CatRenderState, AbstractFelineModel<CatRenderState>> renderer,
		EntityModelSet modelSet
	) {
		super(renderer);
		this.armorModel = new AdultCatModel(modelSet.bakeLayer(PetArmorClient.CAT_ARMOR_MODEL_LAYER));
	}

	@Override
	public void submit(
		PoseStack poseStack,
		SubmitNodeCollector submitNodeCollector,
		int lightCoords,
		CatRenderState state,
		float yRot,
		float xRot
	) {
		ItemStack armor = ((FabricRenderState) state).getDataOrDefault(PetArmorRenderData.CAT_ARMOR, ItemStack.EMPTY);
		Identifier texture = ARMOR_TEXTURES.get(armor.getItem());
		if (!armor.is(ModItemTags.PET_ARMOR) || texture == null || state.isBaby) {
			return;
		}

		int color = -1;
		if (armor.is(ModItems.LEATHER_WOLF_ARMOR)) {
			int dyedColor = DyedItemColor.getOrDefault(armor, 0);
			color = dyedColor == 0 ? -1 : dyedColor;
		}
		submitNodeCollector.order(1).submitModel(
			this.armorModel,
			state,
			poseStack,
			RenderTypes.armorCutoutNoCull(texture),
			lightCoords,
			OverlayTexture.NO_OVERLAY,
			color,
			null,
			state.outlineColor,
			null
		);
		if (armor.hasFoil()) {
			submitNodeCollector.order(2).submitModel(
				this.armorModel,
				state,
				poseStack,
				RenderTypes.armorEntityGlint(),
				lightCoords,
				OverlayTexture.NO_OVERLAY,
				color,
				null,
				state.outlineColor,
				null
			);
		}
		this.renderCracks(poseStack, submitNodeCollector, lightCoords, armor, this.armorModel, state);
	}

	private static Identifier texture(String name) {
		return PetArmor.id("textures/entity/equipment/cat_body/" + name + ".png");
	}

	private void renderCracks(
		PoseStack poseStack,
		SubmitNodeCollector submitNodeCollector,
		int lightCoords,
		ItemStack armor,
		Model<CatRenderState> model,
		CatRenderState state
	) {
		Crackiness.Level crackiness = Crackiness.WOLF_ARMOR.byDamage(armor);
		if (crackiness != Crackiness.Level.NONE) {
			submitNodeCollector.submitModel(
				model,
				state,
				poseStack,
				RenderTypes.armorTranslucent(ARMOR_CRACK_LOCATIONS.get(crackiness)),
				lightCoords,
				OverlayTexture.NO_OVERLAY,
				state.outlineColor,
				null
			);
		}
	}
}
