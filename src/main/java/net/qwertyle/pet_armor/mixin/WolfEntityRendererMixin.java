package net.qwertyle.pet_armor.mixin;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.WolfEntityRenderer;
import net.minecraft.client.render.entity.feature.WolfCollarFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.Identifier;
import net.qwertyle.pet_armor.PetArmor;
import net.qwertyle.pet_armor.client.feature.WolfArmorFeatureRenderer;
import net.qwertyle.pet_armor.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WolfEntityRenderer.class)
public abstract class WolfEntityRendererMixin extends MobEntityRenderer<WolfEntity, WolfEntityModel<WolfEntity>> {
    private static final Identifier WILD_TEXTURE = new Identifier("textures/entity/wolf/wolf.png");
    private static final Identifier TAMED_TEXTURE = new Identifier("textures/entity/wolf/wolf_tame.png");
    private static final Identifier ANGRY_TEXTURE = new Identifier("textures/entity/wolf/wolf_angry.png");

    public WolfEntityRendererMixin(EntityRendererFactory.Context context, WolfEntityModel<WolfEntity> entityModel, float f) {
        super(context, entityModel, f);
        /*
        super(context, new WolfEntityModel(context.getPart(EntityModelLayers.WOLF)), 0.5f);
        this.addFeature(new WolfCollarFeatureRenderer(this));*/
    }

    /*
    @Inject(method = "getTexture", at = @At("HEAD"), cancellable = true)
    public void getTexture(WolfEntity wolfEntity, CallbackInfoReturnable<Identifier> cir) {
        if (wolfEntity.getEquippedStack(EquipmentSlot.CHEST).getItem() == ModItems.LEATHER_WOLF_ARMOR) {
            cir.setReturnValue(new Identifier(PetArmor.MOD_ID,"textures/entity/wolf/leather_wolf_armor.png"));
        } else if (wolfEntity.getEquippedStack(EquipmentSlot.CHEST).getItem() == ModItems.IRON_WOLF_ARMOR) {
            cir.setReturnValue(new Identifier(PetArmor.MOD_ID,"textures/entity/wolf/wolf_armor_iron.png"));
        } else if (wolfEntity.getEquippedStack(EquipmentSlot.CHEST).getItem() == ModItems.GOLD_WOLF_ARMOR) {
            cir.setReturnValue(new Identifier(PetArmor.MOD_ID,"textures/entity/wolf/wolf_armor_gold.png"));
        } else if (wolfEntity.getEquippedStack(EquipmentSlot.CHEST).getItem() == ModItems.DIAMOND_WOLF_ARMOR) {
            cir.setReturnValue(new Identifier(PetArmor.MOD_ID,"textures/entity/wolf/wolf_armor_diamond.png"));
        } else if (wolfEntity.getEquippedStack(EquipmentSlot.CHEST).getItem() == ModItems.NETHERITE_WOLF_ARMOR) {
            cir.setReturnValue(new Identifier(PetArmor.MOD_ID,"textures/entity/wolf/wolf_armor_netherite.png"));
        }else if (wolfEntity.getEquippedStack(EquipmentSlot.CHEST).getItem() == ModItems.CHAINMAIL_WOLF_ARMOR) {
            cir.setReturnValue(new Identifier(PetArmor.MOD_ID,"textures/entity/wolf/wolf_armor_chainmail.png"));
        }

    }*/

    @Inject(method = "<init>", at = @At("RETURN"))
    private void addWolfArmorFeature(EntityRendererFactory.Context context, CallbackInfo ci) {
        this.addFeature(new WolfArmorFeatureRenderer(this, context.getModelLoader()));
    }

}
