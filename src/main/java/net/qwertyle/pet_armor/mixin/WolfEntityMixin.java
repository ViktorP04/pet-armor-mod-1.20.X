package net.qwertyle.pet_armor.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import net.qwertyle.pet_armor.item.ModItems;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(WolfEntity.class)
public abstract class WolfEntityMixin extends TameableEntity implements Angerable {


	//@Unique
	//private ItemStack WOLF_ARMOR = ItemStack.EMPTY;

	protected WolfEntityMixin(EntityType<? extends TameableEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(
			method = "interactMob",
			at = @At("HEAD")
	)
	private void init(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {

		ItemStack itemStack2 = player.getStackInHand(hand);
		Item item2 = itemStack2.getItem();
		if (((WolfEntity)(Object)this).isOwner(player)) {
			if (itemStack2.isOf(ModItems.IRON_WOLF_ARMOR) && ((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST).isEmpty())
			{
				player.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
				((WolfEntity)(Object)this).equipStack(EquipmentSlot.CHEST,itemStack2);
				//player.sendMessage(Text.of("Attached " + ((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST)));
			} else if (itemStack2.isOf(Items.AIR)  && !((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST).isEmpty())
			{
				player.giveItemStack(((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST));
				((WolfEntity)(Object)this).equipStack(EquipmentSlot.CHEST,ItemStack.EMPTY);
				//player.sendMessage(Text.of("Removed " + ((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST)));

			}


		}


		// This code is injected into the start of MinecraftServer.loadWorld()V
	}




	/*
	@Inject(at = @At("HEAD"), method = "loadWorld")
	private void init(CallbackInfo info) {
		// This code is injected into the start of MinecraftServer.loadWorld()V
	}
	*/
}