package net.viktorp04.pet_armor.item;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.Equippable;
import net.viktorp04.pet_armor.PetArmor;

public final class ModItems {
	public static final Item LEATHER_WOLF_ARMOR = register("leather_wolf_armor", ModArmorMaterials.LEATHER, false);
	public static final Item COPPER_WOLF_ARMOR = register("copper_wolf_armor", ModArmorMaterials.COPPER, false);
	public static final Item IRON_WOLF_ARMOR = register("iron_wolf_armor", ModArmorMaterials.IRON, false);
	public static final Item CHAINMAIL_WOLF_ARMOR = register("chainmail_wolf_armor", ModArmorMaterials.CHAINMAIL, false);
	public static final Item GOLDEN_WOLF_ARMOR = register("golden_wolf_armor", ModArmorMaterials.GOLD, false);
	public static final Item DIAMOND_WOLF_ARMOR = register("diamond_wolf_armor", ModArmorMaterials.DIAMOND, false);
	public static final Item NETHERITE_WOLF_ARMOR = register("netherite_wolf_armor", ModArmorMaterials.NETHERITE, true);

	private ModItems() {
	}

	public static void initialize() {
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(entries -> {
			entries.accept(LEATHER_WOLF_ARMOR);
			entries.accept(COPPER_WOLF_ARMOR);
			entries.accept(IRON_WOLF_ARMOR);
			entries.accept(CHAINMAIL_WOLF_ARMOR);
			entries.accept(GOLDEN_WOLF_ARMOR);
			entries.accept(DIAMOND_WOLF_ARMOR);
			entries.accept(NETHERITE_WOLF_ARMOR);
		});
	}

	private static Item register(String name, net.minecraft.world.item.equipment.ArmorMaterial material, boolean fireResistant) {
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, PetArmor.id(name));
		Item.Properties properties = new Item.Properties()
			.wolfArmor(material)
			.enchantable(material.enchantmentValue())
			.component(
				DataComponents.EQUIPPABLE,
				Equippable.builder(EquipmentSlot.BODY)
					.setEquipSound(material.equipSound())
					.setAsset(material.assetId())
					.setAllowedEntities(HolderSet.direct(
						EntityTypes.WOLF.builtInRegistryHolder(),
						EntityTypes.CAT.builtInRegistryHolder(),
						EntityTypes.PARROT.builtInRegistryHolder()
					))
					.setCanBeSheared(true)
					.setShearingSound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.ARMOR_UNEQUIP_WOLF))
					.build()
			);
		if (fireResistant) {
			properties.fireResistant();
		}

		return Registry.register(BuiltInRegistries.ITEM, key, new Item(properties.setId(key)));
	}
}
