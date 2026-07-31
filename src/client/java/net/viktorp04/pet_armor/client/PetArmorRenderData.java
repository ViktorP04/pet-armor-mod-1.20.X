package net.viktorp04.pet_armor.client;

import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.world.item.ItemStack;

public final class PetArmorRenderData {
	public static final RenderStateDataKey<ItemStack> CAT_ARMOR = RenderStateDataKey.create(() -> "pet-armor:cat_armor");
	public static final RenderStateDataKey<ItemStack> PARROT_ARMOR = RenderStateDataKey.create(() -> "pet-armor:parrot_armor");

	private PetArmorRenderData() {
	}
}
