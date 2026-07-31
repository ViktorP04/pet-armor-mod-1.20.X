package net.viktorp04.pet_armor.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.viktorp04.pet_armor.PetArmor;

public final class ModItemTags {
	/**
	 * Kept on the original {@code wolf_armor} tag ID for datapack compatibility.
	 * The items in this tag are now shared by every supported pet.
	 */
	public static final TagKey<Item> PET_ARMOR = TagKey.create(Registries.ITEM, PetArmor.id("wolf_armor"));

	private ModItemTags() {
	}
}
