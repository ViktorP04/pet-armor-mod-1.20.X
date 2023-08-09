package net.qwertyle.pet_armor.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.item.ItemStack;
import net.qwertyle.pet_armor.item.PetArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

    @Inject(
            method = "isAcceptableItem",
            at = @At("HEAD"),
            cancellable = true
    )
    private void isAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if((Object) this instanceof ProtectionEnchantment && stack.getItem() instanceof PetArmorItem) {
            cir.setReturnValue(true);
        }
    }
}
