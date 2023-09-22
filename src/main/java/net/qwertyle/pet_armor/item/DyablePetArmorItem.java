package net.qwertyle.pet_armor.item;

import net.minecraft.item.DyeableItem;


public class DyablePetArmorItem extends PetArmorItem implements DyeableItem {


    public DyablePetArmorItem(Settings settings, int defense, String material, String type) {
        super(settings, defense, material, type);
    }
}
