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
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.qwertyle.pet_armor.PetArmor;
import net.qwertyle.pet_armor.item.ModItems;
import net.qwertyle.pet_armor.item.PetArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = WolfEntity.class, priority = 1001)
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
			if (item2.getName().toString().contains("wolf_armor") && ((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST).isEmpty())
			{

				if (item2 instanceof PetArmorItem) {
					PetArmorItem customItem = (PetArmorItem) item2;
					int variableValue = customItem.getDefense();
				}


				player.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
				((WolfEntity)(Object)this).equipStack(EquipmentSlot.CHEST,itemStack2);
				//player.sendMessage(Text.of("Attached " + ((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST)));
				cir.setReturnValue(ActionResult.CONSUME);


			} else if (itemStack2.isOf(Items.AIR) && !((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST).isEmpty() && player.isSneaking())
			{

				player.giveItemStack(((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST));
				((WolfEntity)(Object)this).equipStack(EquipmentSlot.CHEST,ItemStack.EMPTY);
				//player.sendMessage(Text.of("Removed " + ((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST)));
				cir.setReturnValue(ActionResult.CONSUME);

			}


		}


	}

	//Could be an override but is an inject to make it compatible with Debugify
	@Inject(
			method = "damage",
			at = @At("HEAD"),
			cancellable = true
	)
	public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if (((WolfEntity)(Object)this).isInvulnerableTo(source)) {
			cir.setReturnValue(false);
		}


		int defense = 0;
		if (!((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST).isEmpty())
			defense = ((PetArmorItem)((((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST).getItem()))).getDefense();



		Entity entity = source.getAttacker();
		if (!((WolfEntity)(Object)this).getWorld().isClient) {
			((WolfEntity)(Object)this).setSitting(false);
		}
		if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof PersistentProjectileEntity)) {
			amount = (amount + 1.0f) / 2.0f;
		}


		amount = amount * (1F-(defense/100F));

		cir.setReturnValue(super.damage(source, amount));
	}



/*
	@Override
	public boolean damage(DamageSource source, float amount) {
		if (((WolfEntity)(Object)this).isInvulnerableTo(source)) {
			return false;
		}


		int defense = 0;
		if (!((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST).isEmpty())
			defense = ((PetArmorItem)((((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST).getItem()))).getDefense();



		Entity entity = source.getAttacker();
		if (!((WolfEntity)(Object)this).getWorld().isClient) {
			((WolfEntity)(Object)this).setSitting(false);
		}
		if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof PersistentProjectileEntity)) {
			amount = (amount + 1.0f) / 2.0f;
		}


		amount = amount * (1F-(defense/100F));

		return super.damage(source, amount);
	}*/

	@Inject(
			method = "onDeath",
			at = @At("HEAD")
	)
	public void onDeath(DamageSource damageSource, CallbackInfo ci) {
		((WolfEntity)(Object)this).dropStack(((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST));
	}

	@Override
	public boolean isFireImmune() {

		if(((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST).isOf(ModItems.NETHERITE_WOLF_ARMOR)) {
			return true;
		}

		return super.isFireImmune();
	}

	@Override
	public void setOnFireFor(int seconds) {
		if(((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST).isOf(ModItems.NETHERITE_WOLF_ARMOR)) {
			return;
		}

		super.setOnFireFor(seconds);
	}




/*
	@Inject(at = @At("HEAD"), method = "loadWorld")
	private void init(CallbackInfo info) {
		// This code is injected into the start of MinecraftServer.loadWorld()V
	}
	*/
}