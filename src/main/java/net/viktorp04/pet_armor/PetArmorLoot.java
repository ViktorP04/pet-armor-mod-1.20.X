package net.viktorp04.pet_armor;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.viktorp04.pet_armor.item.ModItems;

public final class PetArmorLoot {
	private static final float END_CITY_CHANCE = 0.10F;
	private static final float BASTION_TREASURE_CHANCE = 0.15F;

	private PetArmorLoot() {
	}

	public static void initialize() {
		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
			if (!source.isBuiltin()) {
				return;
			}

			if (BuiltInLootTables.END_CITY_TREASURE.equals(key)) {
				tableBuilder.withPool(armorPool(ModItems.DIAMOND_WOLF_ARMOR, END_CITY_CHANCE, registries));
			} else if (BuiltInLootTables.BASTION_TREASURE.equals(key)) {
				tableBuilder.withPool(armorPool(ModItems.NETHERITE_WOLF_ARMOR, BASTION_TREASURE_CHANCE, registries));
			}
		});
	}

	private static LootPool.Builder armorPool(
		net.minecraft.world.item.Item armor,
		float chance,
		net.minecraft.core.HolderLookup.Provider registries
	) {
		return LootPool.lootPool()
			.setRolls(ConstantValue.exactly(1.0F))
			.when(LootItemRandomChanceCondition.randomChance(chance))
			.add(
				LootItem.lootTableItem(armor)
					.apply(
						EnchantRandomlyFunction.randomApplicableEnchantment(registries)
							.when(LootItemRandomChanceCondition.randomChance(0.5F))
					)
			);
	}
}
