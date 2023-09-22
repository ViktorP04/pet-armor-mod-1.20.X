package net.qwertyle.pet_armor.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.qwertyle.pet_armor.item.ModItems;

public class ModModelProvider  extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.LEATHER_WOLF_ARMOR, Models.GENERATED);
        itemModelGenerator.register(ModItems.IRON_WOLF_ARMOR, Models.GENERATED);
        itemModelGenerator.register(ModItems.CHAINMAIL_WOLF_ARMOR, Models.GENERATED);
        itemModelGenerator.register(ModItems.GOLD_WOLF_ARMOR, Models.GENERATED);
        itemModelGenerator.register(ModItems.DIAMOND_WOLF_ARMOR, Models.GENERATED);
        itemModelGenerator.register(ModItems.NETHERITE_WOLF_ARMOR, Models.GENERATED);
    }
}
