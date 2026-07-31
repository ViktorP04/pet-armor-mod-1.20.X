package net.viktorp04.pet_armor.mixin;

import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.viktorp04.pet_armor.item.ModItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SmithingTransformRecipe.class)
public abstract class SmithingTransformRecipeMixin {
	@Inject(
		method = "assemble(Lnet/minecraft/world/item/crafting/SmithingRecipeInput;)Lnet/minecraft/world/item/ItemStack;",
		at = @At("RETURN")
	)
	private void petArmor$preserveWearPercentage(SmithingRecipeInput input, CallbackInfoReturnable<ItemStack> cir) {
		ItemStack inputArmor = input.base();
		ItemStack outputArmor = cir.getReturnValue();
		boolean validInput = inputArmor.is(Items.WOLF_ARMOR) || inputArmor.is(ModItemTags.PET_ARMOR);
		if (!validInput || !outputArmor.is(ModItemTags.PET_ARMOR) || !inputArmor.isDamageableItem() || !outputArmor.isDamageableItem()) {
			return;
		}

		float wear = (float) inputArmor.getDamageValue() / inputArmor.getMaxDamage();
		int outputDamage = Mth.clamp(Math.round(wear * outputArmor.getMaxDamage()), 0, outputArmor.getMaxDamage() - 1);
		outputArmor.setDamageValue(outputDamage);
	}
}
