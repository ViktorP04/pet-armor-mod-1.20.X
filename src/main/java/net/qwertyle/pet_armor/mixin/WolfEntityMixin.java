package net.qwertyle.pet_armor.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.qwertyle.pet_armor.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WolfEntity.class)
public abstract class WolfEntityMixin extends TameableEntity implements Angerable {

	protected WolfEntityMixin(EntityType<? extends TameableEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(
			method = "interactMob",
			at = @At("HEAD"),
			cancellable = true
	)
	private void init(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {

		ItemStack itemStack2 = player.getStackInHand(hand);
		Item item2 = itemStack2.getItem();
		if (((WolfEntity)(Object)this).isOwner(player)) {
			if (itemStack2.isOf(ModItems.LEATHER_WOLF_ARMOR) && ((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST).isEmpty())
			{
				player.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
				((WolfEntity)(Object)this).equipStack(EquipmentSlot.CHEST,itemStack2);
				//player.sendMessage(Text.of("Attached " + ((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST)));
				cir.setReturnValue(ActionResult.CONSUME);
			} else if (itemStack2.isOf(Items.AIR)  && !((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST).isEmpty() && player.isSneaking())
			{
				player.giveItemStack(((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST));
				((WolfEntity)(Object)this).equipStack(EquipmentSlot.CHEST,ItemStack.EMPTY);
				//player.sendMessage(Text.of("Removed " + ((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST)));
				cir.setReturnValue(ActionResult.CONSUME);


			}


		}


		// This code is injected into the start of MinecraftServer.loadWorld()V
	}

	@Override
	public boolean damage(DamageSource source, float amount) {
		if (((WolfEntity)(Object)this).isInvulnerableTo(source)) {
			return false;
		}
		Entity entity = source.getAttacker();
		if (!((WolfEntity)(Object)this).getWorld().isClient) {
			((WolfEntity)(Object)this).setSitting(false);
		}
		if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof PersistentProjectileEntity)) {
			amount = (amount + 1.0f) / 2.0f;
		}
		if (((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST).getItem() == ModItems.LEATHER_WOLF_ARMOR)
		{
			amount = amount / 2.0f;
		}
		return super.damage(source, amount);
	}

	@Inject(
			method = "onDeath",
			at = @At("HEAD")
	)
	public void onDeath(DamageSource damageSource, CallbackInfo ci) {
		((WolfEntity)(Object)this).dropStack(((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST));
	}




	/*
	@Inject(at = @At("HEAD"), method = "loadWorld")
	private void init(CallbackInfo info) {
		// This code is injected into the start of MinecraftServer.loadWorld()V
	}
	*/
}