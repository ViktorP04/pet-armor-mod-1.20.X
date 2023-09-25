package net.qwertyle.pet_armor.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PetArmorItem extends Item {

    private final int defense;
    private final String material;
    private final int enchantability;
    private final Identifier texture;
    public PetArmorItem (Settings settings, int defense, String material, int enchantability, String entityTexturePath) {
        super(settings);
        this.defense = defense;
        this.material = material;
        this.enchantability = enchantability;
        this.texture = new Identifier("pet-armor","textures/entity/wolf/" + entityTexturePath + ".png");
    }


    @Environment(EnvType.CLIENT)
    public Identifier GetEntityTexture() {
        return this.texture;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        /*
        ItemStack itemStack2 = user.getStackInHand(hand);
        Item item2 = itemStack2.getItem();
        if (((WolfEntity)(Object)entity).isOwner(user)) {
            if (item2.getName().toString().contains("wolf_armor") && ((WolfEntity)(Object)entity).getEquippedStack(EquipmentSlot.CHEST).isEmpty())
            {

                if (item2 instanceof PetArmorItem) {
                    PetArmorItem customItem = (PetArmorItem) item2;
                    int variableValue = customItem.getDefense();
                    //
                }


                user.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                ((WolfEntity)(Object)entity).equipStack(EquipmentSlot.CHEST,itemStack2);
                //player.sendMessage(Text.of("Attached " + ((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST)));
                return ActionResult.SUCCESS;


            } else if (itemStack2.isOf(Items.AIR) && !((WolfEntity)(Object)entity).getEquippedStack(EquipmentSlot.CHEST).isEmpty() && user.isSneaking())
            {

                user.giveItemStack(((WolfEntity)(Object)entity).getEquippedStack(EquipmentSlot.CHEST));
                ((WolfEntity)(Object)entity).equipStack(EquipmentSlot.CHEST,ItemStack.EMPTY);
                //player.sendMessage(Text.of("Removed " + ((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST)));
                return ActionResult.SUCCESS;

            }


        }

        return ActionResult.FAIL;*/
        return ActionResult.SUCCESS;
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }


    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        if (material == ingredient.getItem().getName().toString())
            return true;
        return false;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.pet-armor.petarmortooltip").formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, context);
    }

    public int getDefense ()
    {
        return defense;
    }
}
