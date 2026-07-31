package net.viktorp04.pet_armor.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.animal.parrot.Parrot;
import net.minecraft.world.entity.player.Player;
import net.viktorp04.pet_armor.PetArmorHelper;
import net.viktorp04.pet_armor.item.ModItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobMixin {
	@Inject(method = "doHurtTarget", at = @At("RETURN"))
	private void petArmor$repairMendingArmorAfterAttack(
		ServerLevel level,
		Entity target,
		CallbackInfoReturnable<Boolean> cir
	) {
		if (cir.getReturnValue() && (Object) this instanceof Mob mob && PetArmorHelper.isSupportedPet(mob)) {
			PetArmorHelper.repairAfterSuccessfulAttack(mob);
		}
	}

	@Inject(method = "canShearEquipment", at = @At("HEAD"), cancellable = true)
	private void petArmor$restrictPetArmorRemovalToOwner(Player player, CallbackInfoReturnable<Boolean> cir) {
		if ((Object) this instanceof Cat cat && cat.getBodyArmorItem().is(ModItemTags.PET_ARMOR)) {
			cir.setReturnValue(cat.isOwnedBy(player));
		} else if ((Object) this instanceof Parrot parrot && parrot.getBodyArmorItem().is(ModItemTags.PET_ARMOR)) {
			cir.setReturnValue(parrot.isOwnedBy(player));
		}
	}
}
