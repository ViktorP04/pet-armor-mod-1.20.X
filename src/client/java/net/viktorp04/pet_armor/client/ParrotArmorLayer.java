package net.viktorp04.pet_armor.client;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.animal.parrot.ParrotModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.ParrotRenderState;
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

public final class ParrotArmorLayer extends RenderLayer<ParrotRenderState, ParrotModel> {
	private static final Map<Crackiness.Level, Identifier> ARMOR_CRACK_LOCATIONS = Map.of(
		Crackiness.Level.LOW, texture("parrot_armor_crackiness_low"),
		Crackiness.Level.MEDIUM, texture("parrot_armor_crackiness_medium"),
		Crackiness.Level.HIGH, texture("parrot_armor_crackiness_high")
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

	private final ParrotArmorModel armorModel;

	public ParrotArmorLayer(RenderLayerParent<ParrotRenderState, ParrotModel> renderer, EntityModelSet modelSet) {
		super(renderer);
		this.armorModel = new ParrotArmorModel(modelSet.bakeLayer(PetArmorClient.PARROT_ARMOR_MODEL_LAYER));
	}

	@Override
	public void submit(
		PoseStack poseStack,
		SubmitNodeCollector submitNodeCollector,
		int lightCoords,
		ParrotRenderState state,
		float yRot,
		float xRot
	) {
		ItemStack armor = ((FabricRenderState) state).getDataOrDefault(PetArmorRenderData.PARROT_ARMOR, ItemStack.EMPTY);
		Identifier texture = ARMOR_TEXTURES.get(armor.getItem());
		if (!armor.is(ModItemTags.PET_ARMOR) || texture == null) {
			return;
		}

		int color = -1;
		if (armor.is(ModItems.LEATHER_WOLF_ARMOR)) {
			int dyedColor = DyedItemColor.getOrDefault(armor, 0);
			color = dyedColor == 0 ? -1 : dyedColor;
		}
		submitNodeCollector.order(1).submitModel(
			this.armorModel, state, poseStack, RenderTypes.armorCutoutNoCull(texture), lightCoords,
			OverlayTexture.NO_OVERLAY, color, null, state.outlineColor, null
		);
		if (armor.hasFoil()) {
			submitNodeCollector.order(2).submitModel(
				this.armorModel, state, poseStack, RenderTypes.armorEntityGlint(), lightCoords,
				OverlayTexture.NO_OVERLAY, color, null, state.outlineColor, null
			);
		}
		this.renderCracks(poseStack, submitNodeCollector, lightCoords, armor, state);
	}

	private void renderCracks(
		PoseStack poseStack,
		SubmitNodeCollector submitNodeCollector,
		int lightCoords,
		ItemStack armor,
		ParrotRenderState state
	) {
		Crackiness.Level crackiness = Crackiness.WOLF_ARMOR.byDamage(armor);
		if (crackiness != Crackiness.Level.NONE) {
			submitNodeCollector.submitModel(
				(Model<ParrotRenderState>) this.armorModel,
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

	private static Identifier texture(String name) {
		return PetArmor.id("textures/entity/equipment/parrot_body/" + name + ".png");
	}
}
