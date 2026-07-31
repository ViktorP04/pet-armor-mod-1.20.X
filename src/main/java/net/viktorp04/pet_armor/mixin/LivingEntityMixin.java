package net.viktorp04.pet_armor.mixin;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Crackiness;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.animal.parrot.Parrot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.viktorp04.pet_armor.PetArmorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Inject(method = "getBlockSpeedFactor", at = @At("HEAD"), cancellable = true)
	private void petArmor$applySoulSpeed(CallbackInfoReturnable<Float> cir) {
		LivingEntity livingEntity = (LivingEntity) (Object) this;
		if (PetArmorHelper.isSupportedPet(livingEntity)
			&& PetArmorHelper.hasEnchantment(livingEntity, Enchantments.SOUL_SPEED)
			&& livingEntity.level().getBlockState(livingEntity.getBlockPosBelowThatAffectsMyMovement()).is(BlockTags.SOUL_SPEED_BLOCKS)) {
			cir.setReturnValue(1.0F);
		}
	}

	@Inject(method = "actuallyHurt", at = @At("HEAD"), cancellable = true)
	private void petArmor$absorbPetDamage(ServerLevel level, DamageSource source, float damage, CallbackInfo ci) {
		LivingEntity livingEntity = (LivingEntity) (Object) this;
		if (!(livingEntity instanceof Cat || livingEntity instanceof Parrot)
			|| !PetArmorHelper.canAbsorb(livingEntity, source)) {
			return;
		}

		ItemStack armor = livingEntity.getItemBySlot(EquipmentSlot.BODY);
		int damageBefore = armor.getDamageValue();
		int maxDamage = armor.getMaxDamage();
		armor.hurtAndBreak(
			PetArmorHelper.getDurabilityDamage(level, livingEntity, source, damage),
			livingEntity,
			EquipmentSlot.BODY
		);
		if (Crackiness.WOLF_ARMOR.byDamage(damageBefore, maxDamage)
			!= Crackiness.WOLF_ARMOR.byDamage(livingEntity.getItemBySlot(EquipmentSlot.BODY))) {
			livingEntity.playSound(SoundEvents.WOLF_ARMOR_CRACK);
			level.sendParticles(
				new ItemParticleOption(ParticleTypes.ITEM, Items.ARMADILLO_SCUTE),
				livingEntity.getX(), livingEntity.getY() + livingEntity.getBbHeight() * 0.5, livingEntity.getZ(),
				12, 0.15, 0.1, 0.15, 0.08
			);
		}
		ci.cancel();
	}
}
