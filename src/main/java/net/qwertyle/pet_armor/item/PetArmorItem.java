package net.qwertyle.pet_armor.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class PetArmorItem extends Item {

    private final int defense;
    private final String material;
    private final Identifier texture;
    public PetArmorItem (Settings settings, int defense, String material) {
        super(settings);
        this.defense = defense;
        this.material = material;
        this.texture = new Identifier("pet-armor","textures/entity/wolf/wolf_armor.png");
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        user.sendMessage(Text.of("yes"));

        return ActionResult.SUCCESS;
    }

    @Override
    public int getEnchantability() {
        return 10;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        if (material == ingredient.getItem().getName().toString())
            return true;
        return false;
    }



    public int getDefense ()
    {
        return defense;
    }
}
