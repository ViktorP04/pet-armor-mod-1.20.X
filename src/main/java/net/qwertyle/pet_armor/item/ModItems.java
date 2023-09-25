package net.qwertyle.pet_armor.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.qwertyle.pet_armor.PetArmor;

public class ModItems {
    public static final Item LEATHER_WOLF_ARMOR =  registerItem("leather_wolf_armor", new DyablePetArmorItem(new FabricItemSettings().maxDamage(80), 28, "leather", 15, "leather_wolf_armor"));
    public static final Item IRON_WOLF_ARMOR =  registerItem("iron_wolf_armor", new PetArmorItem(new FabricItemSettings().maxDamage(240), 44,"iron_ingot", 9, "iron_wolf_armor"));
    public static final Item CHAINMAIL_WOLF_ARMOR =  registerItem("chainmail_wolf_armor", new PetArmorItem(new FabricItemSettings().maxDamage(240), 48,"", 12, "chainmail_wolf_armor"));
    public static final Item GOLDEN_WOLF_ARMOR =  registerItem("golden_wolf_armor", new PetArmorItem(new FabricItemSettings().maxDamage(112), 60,"gold_ingot",  25, "golden_wolf_armor"));
    public static final Item DIAMOND_WOLF_ARMOR =  registerItem("diamond_wolf_armor", new PetArmorItem(new FabricItemSettings().maxDamage(528), 80,"diamond", 10, "diamond_wolf_armor"));
    public static final Item NETHERITE_WOLF_ARMOR =  registerItem("netherite_wolf_armor", new PetArmorItem(new FabricItemSettings().maxDamage(592).fireproof(), 80, "netherite", 15, "netherite_wolf_armor"));


    private  static void addItemsToIngredientTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(LEATHER_WOLF_ARMOR);
        entries.add(IRON_WOLF_ARMOR);
        entries.add(CHAINMAIL_WOLF_ARMOR);
        entries.add(GOLDEN_WOLF_ARMOR);
        entries.add(DIAMOND_WOLF_ARMOR);
        entries.add(NETHERITE_WOLF_ARMOR);
    }


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(PetArmor.MOD_ID, name), item);
    }
    public static void RegisterModItems() {
        PetArmor.LOGGER.info("Registering Mod Items for " + PetArmor.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ModItems::addItemsToIngredientTabItemGroup);
    }
}
