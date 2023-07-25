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
import net.qwertyle.pet_armor.PetArmor;

public class ModItems {
    public static final Item IRON_WOLF_ARMOR = registerItem("iron_wolf_armor", new Item(new FabricItemSettings()));


    private  static void addItemsToIngredientTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(IRON_WOLF_ARMOR);
    }


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(PetArmor.MOD_ID, name), item);
    }
    public static void RegisterModItems() {
        PetArmor.LOGGER.info("Registering Mod Items for" + PetArmor.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientTabItemGroup);
    }
}
