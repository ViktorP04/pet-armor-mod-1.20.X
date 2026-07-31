package net.viktorp04.pet_armor.item;

import java.util.Map;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.viktorp04.pet_armor.PetArmor;

public final class ModArmorMaterials {
	public static final ArmorMaterial LEATHER = create(
		"leather_wolf_armor", 5, 7, 15, 0.0F, 0.0F, ItemTags.REPAIRS_LEATHER_ARMOR
	);
	public static final ArmorMaterial COPPER = create(
		"copper_wolf_armor", 11, 9, 8, 0.0F, 0.0F, ItemTags.REPAIRS_COPPER_ARMOR
	);
	public static final ArmorMaterial IRON = create(
		"iron_wolf_armor", 15, 11, 9, 0.0F, 0.0F, ItemTags.REPAIRS_IRON_ARMOR
	);
	public static final ArmorMaterial CHAINMAIL = create(
		"chainmail_wolf_armor", 15, 12, 12, 0.0F, 0.0F, ItemTags.REPAIRS_CHAIN_ARMOR
	);
	public static final ArmorMaterial GOLD = create(
		"golden_wolf_armor", 7, 15, 25, 0.0F, 0.0F, ItemTags.REPAIRS_GOLD_ARMOR
	);
	public static final ArmorMaterial DIAMOND = create(
		"diamond_wolf_armor", 33, 20, 10, 2.0F, 0.0F, ItemTags.REPAIRS_DIAMOND_ARMOR
	);
	public static final ArmorMaterial NETHERITE = create(
		"netherite_wolf_armor", 37, 20, 15, 3.0F, 0.1F, ItemTags.REPAIRS_NETHERITE_ARMOR
	);

	private ModArmorMaterials() {
	}

	private static ArmorMaterial create(
		String assetName,
		int durability,
		int bodyDefense,
		int enchantmentValue,
		float toughness,
		float knockbackResistance,
		net.minecraft.tags.TagKey<net.minecraft.world.item.Item> repairIngredient
	) {
		ResourceKey<EquipmentAsset> asset = ResourceKey.create(EquipmentAssets.ROOT_ID, PetArmor.id(assetName));
		return new ArmorMaterial(
			durability,
			Map.of(ArmorType.BODY, bodyDefense),
			enchantmentValue,
			SoundEvents.ARMOR_EQUIP_WOLF,
			toughness,
			knockbackResistance,
			repairIngredient,
			asset
		);
	}
}
