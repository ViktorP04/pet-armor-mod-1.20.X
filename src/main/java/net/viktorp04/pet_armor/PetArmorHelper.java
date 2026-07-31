package net.viktorp04.pet_armor;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.animal.parrot.Parrot;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.viktorp04.pet_armor.item.ModItemTags;
import org.apache.commons.lang3.mutable.MutableFloat;

public final class PetArmorHelper {
	private PetArmorHelper() {
	}

	public static boolean isSupportedPet(LivingEntity entity) {
		return entity instanceof Wolf || entity instanceof Cat || entity instanceof Parrot;
	}

	public static boolean canAbsorb(LivingEntity pet, DamageSource source) {
		return pet.getItemBySlot(EquipmentSlot.BODY).is(ModItemTags.PET_ARMOR)
			&& !source.is(DamageTypeTags.BYPASSES_WOLF_ARMOR);
	}

	public static boolean hasEnchantment(LivingEntity pet, ResourceKey<Enchantment> enchantmentKey) {
		ItemStack armor = pet.getItemBySlot(EquipmentSlot.BODY);
		if (!armor.is(ModItemTags.PET_ARMOR)) {
			return false;
		}

		Holder<Enchantment> enchantment = pet.registryAccess()
			.lookupOrThrow(Registries.ENCHANTMENT)
			.getOrThrow(enchantmentKey);
		return EnchantmentHelper.getItemEnchantmentLevel(enchantment, armor) > 0;
	}

	public static void repairAfterSuccessfulAttack(LivingEntity pet) {
		ItemStack armor = pet.getItemBySlot(EquipmentSlot.BODY);
		if (armor.isDamaged() && hasEnchantment(pet, net.minecraft.world.item.enchantment.Enchantments.MENDING)) {
			armor.setDamageValue(Math.max(0, armor.getDamageValue() - 3));
		}
	}

	public static int getDurabilityDamage(ServerLevel level, LivingEntity pet, DamageSource source, float damage) {
		if (!PetArmorConfig.armorLosesDurability()) {
			return 0;
		}

		ItemStack armor = pet.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.BODY);
		MutableFloat protection = new MutableFloat(0.0F);
		EnchantmentHelper.runIterationOnItem(
			armor,
			(enchantment, enchantmentLevel) -> enchantment.value()
				.modifyDamageProtection(level, enchantmentLevel, armor, pet, source, protection)
		);
		float reducedDamage = CombatRules.getDamageAfterMagicAbsorb(damage, protection.floatValue());
		return Math.max(1, Mth.ceil(reducedDamage));
	}
}
