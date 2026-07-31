package net.viktorp04.pet_armor.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.item.ItemStack;
import net.viktorp04.pet_armor.PetArmorHelper;
import net.viktorp04.pet_armor.item.ModItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Wolf.class)
public abstract class WolfMixin {
	@Unique
	private int petArmor$durabilityDamage;

	@Inject(method = "canArmorAbsorb", at = @At("HEAD"), cancellable = true)
	private void petArmor$allowCustomWolfArmor(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
		Wolf wolf = (Wolf) (Object) this;
		if (wolf.getBodyArmorItem().is(ModItemTags.PET_ARMOR)) {
			cir.setReturnValue(PetArmorHelper.canAbsorb(wolf, source));
		}
	}

	@Inject(method = "actuallyHurt", at = @At("HEAD"))
	private void petArmor$calculateDurabilityDamage(ServerLevel level, DamageSource source, float damage, CallbackInfo ci) {
		Wolf wolf = (Wolf) (Object) this;
		ItemStack armor = wolf.getBodyArmorItem();
		this.petArmor$durabilityDamage = Mth.ceil(damage);
		if (!armor.is(ModItemTags.PET_ARMOR)) {
			return;
		}
		this.petArmor$durabilityDamage = PetArmorHelper.getDurabilityDamage(level, wolf, source, damage);
	}

	@ModifyArg(
		method = "actuallyHurt",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/item/ItemStack;hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;)V"
		),
		index = 0
	)
	private int petArmor$applyDurabilityDamageReduction(int originalDamage) {
		Wolf wolf = (Wolf) (Object) this;
		return wolf.getBodyArmorItem().is(ModItemTags.PET_ARMOR) ? this.petArmor$durabilityDamage : originalDamage;
	}
}
