package net.viktorp04.pet_armor.mixin;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.parrot.Parrot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.viktorp04.pet_armor.PetArmorHelper;
import net.viktorp04.pet_armor.item.ModItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Parrot.class)
public abstract class ParrotMixin {
	@Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
	private void petArmor$handleArmorInteraction(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
		Parrot parrot = (Parrot) (Object) this;
		if (!parrot.isTame() || !parrot.isOwnedBy(player)) {
			return;
		}

		ItemStack heldItem = player.getItemInHand(hand);
		ItemStack armor = parrot.getBodyArmorItem();
		if (heldItem.is(ModItemTags.PET_ARMOR) && armor.isEmpty()) {
			parrot.setItemSlot(EquipmentSlot.BODY, heldItem.copyWithCount(1));
			parrot.setGuaranteedDrop(EquipmentSlot.BODY);
			heldItem.consume(1, player);
			cir.setReturnValue(InteractionResult.SUCCESS);
			return;
		}

		if (parrot.isInSittingPose()
			&& armor.is(ModItemTags.PET_ARMOR)
			&& armor.isDamaged()
			&& armor.isValidRepairItem(heldItem)) {
			heldItem.shrink(1);
			parrot.playSound(SoundEvents.WOLF_ARMOR_REPAIR);
			int repairUnit = (int) (armor.getMaxDamage() * 0.125F);
			armor.setDamageValue(Math.max(0, armor.getDamageValue() - repairUnit));
			cir.setReturnValue(InteractionResult.SUCCESS);
		}
	}

	@Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true)
	private void petArmor$useArmorDamageSound(DamageSource source, CallbackInfoReturnable<SoundEvent> cir) {
		Parrot parrot = (Parrot) (Object) this;
		if (PetArmorHelper.canAbsorb(parrot, source)) {
			cir.setReturnValue(SoundEvents.WOLF_ARMOR_DAMAGE);
		}
	}
}
