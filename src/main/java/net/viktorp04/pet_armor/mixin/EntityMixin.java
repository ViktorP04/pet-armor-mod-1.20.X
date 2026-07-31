package net.viktorp04.pet_armor.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.viktorp04.pet_armor.PetArmorHelper;
import net.viktorp04.pet_armor.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@Inject(method = "fireImmune", at = @At("HEAD"), cancellable = true)
	private void petArmor$netheritePetArmorFireImmunity(CallbackInfoReturnable<Boolean> cir) {
		if ((Object) this instanceof LivingEntity livingEntity
			&& PetArmorHelper.isSupportedPet(livingEntity)
			&& livingEntity.getItemBySlot(EquipmentSlot.BODY).is(ModItems.NETHERITE_WOLF_ARMOR)) {
			cir.setReturnValue(true);
		}
	}
}
