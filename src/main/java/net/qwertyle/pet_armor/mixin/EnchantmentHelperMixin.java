package net.qwertyle.pet_armor.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.qwertyle.pet_armor.item.ModItems;
import net.qwertyle.pet_armor.item.PetArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;


//Thanks to this guy: https://discord.com/channels/507304429255393322/507982478276034570/1097769918990131231
@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin
{
    // MixinExtras injector for wrapping method calls so you can tweak the returned value
    @ModifyExpressionValue(
            // Inject into getPossibleEntries
            method = "getPossibleEntries",
            // Wrap the call to isAcceptableItem. The punctuation soup is called a JVM method descriptor
            at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentTarget;isAcceptableItem(Lnet/minecraft/item/Item;)Z"))
    // Needs to be static because getPossibleEntries is
    private static boolean scratch_changeAcceptability(
            boolean acceptable, // Wrapped call return value
            // getPossibleEntries parameters
            int power,
            ItemStack stack, // This is the only parameter of getPossibleEntries this example uses, but including the others is necessary
            boolean treasureAllowed,
            // Locals captured via https://github.com/LlamaLad7/MixinExtras/wiki/Sugar
            @Local Enchantment enchantment // The enchantment
    )
    {
        /* Check for the specific item and enchantment we want to make compatible
         * to avoid changing vanilla behaviour. */
        if (stack.getItem() instanceof PetArmorItem &&( enchantment == Enchantments.PROTECTION || enchantment == Enchantments.THORNS || enchantment == Enchantments.MENDING || enchantment == Enchantments.SOUL_SPEED))
            return true;
        return acceptable;
    }
}
