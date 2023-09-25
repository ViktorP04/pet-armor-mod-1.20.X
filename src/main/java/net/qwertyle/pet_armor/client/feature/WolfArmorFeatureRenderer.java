package net.qwertyle.pet_armor.client.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.qwertyle.pet_armor.PetArmorClient;
import net.qwertyle.pet_armor.client.WolfArmorModel;
import net.qwertyle.pet_armor.item.DyablePetArmorItem;
import net.qwertyle.pet_armor.item.PetArmorItem;

@Environment(EnvType.CLIENT)
public class WolfArmorFeatureRenderer extends FeatureRenderer<WolfEntity, WolfEntityModel<WolfEntity>> {
    private final WolfArmorModel model;

    public WolfArmorFeatureRenderer(FeatureRendererContext<WolfEntity, WolfEntityModel<WolfEntity>> context, EntityModelLoader loader) {
        super(context);
        model = new WolfArmorModel(loader.getModelPart(PetArmorClient.WOLF_ARMOR));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, WolfEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {

        ItemStack itemStack = entity.getEquippedStack(EquipmentSlot.CHEST);

        if (itemStack.getItem() instanceof PetArmorItem armorItem)
        {
            this.getContextModel().copyStateTo(this.model);
            this.model.animateModel(entity, limbAngle, limbDistance, tickDelta);
            this.model.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);

            float r;
            float g;
            float b;
            if (armorItem instanceof DyablePetArmorItem dyeable) {
                int m = dyeable.getColor(itemStack);
                r = (float)(m >> 16 & 255) / 255F;
                g = (float)(m >> 8 & 255) / 255F;
                b = (float)(m & 255) / 255F;
            } else {
                r = 1F;
                g = 1F;
                b = 1F;
            }
            boolean isEnchanted = itemStack.hasEnchantments();

            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(armorItem.GetEntityTexture()), false, isEnchanted);
            this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, r, g, b, 1F);
        }

    }
}
