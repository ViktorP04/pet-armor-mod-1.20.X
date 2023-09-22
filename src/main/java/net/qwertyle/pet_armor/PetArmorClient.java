package net.qwertyle.pet_armor;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.qwertyle.pet_armor.client.WolfArmorModel;
import net.qwertyle.pet_armor.impl.client.LeatherHueProvider;
import net.qwertyle.pet_armor.item.ModItems;

public class PetArmorClient implements ClientModInitializer {

    public static final EntityModelLayer WOLF_ARMOR = new EntityModelLayer(new Identifier("pet-armor", "wolf_armor"), "wolf_armor");
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(WOLF_ARMOR, () -> WolfArmorModel.getTexturedModelData(new Dilation(0.35f)));
        ColorProviderRegistry.ITEM.register(new LeatherHueProvider(), ModItems.LEATHER_WOLF_ARMOR);
    }
}
