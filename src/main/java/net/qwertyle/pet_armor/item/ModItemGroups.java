package net.qwertyle.pet_armor.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.qwertyle.pet_armor.PetArmor;

public class ModItemGroups {
    public  static final ItemGroup NEXTSTEP_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(PetArmor.MOD_ID, "pet-armor"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.pet-armor"))
                    .icon(() -> new ItemStack(ModItems.LEATHER_WOLF_ARMOR)).entries((displayContext, entries) -> {

                        entries.add(ModItems.LEATHER_WOLF_ARMOR);
                        entries.add(ModItems.IRON_WOLF_ARMOR);
                        entries.add(ModItems.CHAINMAIL_WOLF_ARMOR);
                        entries.add(ModItems.GOLD_WOLF_ARMOR);
                        entries.add(ModItems.DIAMOND_WOLF_ARMOR);
                        entries.add(ModItems.NETHERITE_WOLF_ARMOR);

                    }).build());


    public  static void registerItemGroups() {
        PetArmor.LOGGER.info("Registering Item Groups for " + PetArmor.MOD_ID);
    }
}
