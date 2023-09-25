package net.qwertyle.pet_armor.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.ThornsEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
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
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.qwertyle.pet_armor.PetArmor;
import net.qwertyle.pet_armor.item.ModItems;
import net.qwertyle.pet_armor.item.PetArmorItem;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(value = WolfEntity.class, priority = 1001)
public abstract class WolfEntityMixin extends TameableEntity implements Angerable {

	@Shadow private @Nullable UUID angryAt;

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

	//Could be an override but is an inject to make it compatible with Debugify and other mods that override tihs function
	@Inject(
			method = "damage",
			at = @At("HEAD"),
			cancellable = true
	)
	public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {

		float originalDamageAmount = amount;


		if (((WolfEntity)(Object)this).isInvulnerableTo(source)) {
			cir.setReturnValue(false);
		}


		Entity entity = source.getAttacker();
		if (!((WolfEntity)(Object)this).getWorld().isClient) {
			((WolfEntity)(Object)this).setSitting(false);
		}
		if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof PersistentProjectileEntity)) {
			amount = (amount + 1.0f) / 2.0f;
		}

		if (!((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST).isEmpty() && ((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof PetArmorItem)
		{
			ItemStack armorStack = ((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST);

			int defense = 0;
			if (!armorStack.isEmpty())
			{
				defense = ((PetArmorItem)armorStack.getItem()).getDefense();
			}



			//Protection
			//int protectionLevel = EnchantmentHelper.getLevel(Enchantments.PROTECTION, ((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST));
			//amount -= amount * (0.04F * protectionLevel);

			//Thorns
			int thornsLevel = EnchantmentHelper.getLevel(Enchantments.THORNS, armorStack);
			if (thornsLevel > 0)
			{
				Random random = getRandom();

				if (ThornsEnchantment.shouldDamageAttacker(thornsLevel, random)) {
					if (source.getAttacker() != null) {
						//MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("Thorns!"));

						//source.getAttacker().damage(this.getDamageSources().mobAttack(this), 5);
						//source.getAttacker().damage(this.getDamageSources().thorns(this), 5); //ThornsEnchantment.getDamageAmount(thornsLevel, random)

						source.getAttacker().damage(this.getDamageSources().generic(), ThornsEnchantment.getDamageAmount(thornsLevel, random));
						//source.getAttacker().setOnFireFor(50);
					}
				}

			}

			amount = amount * (1F-(defense/100F));

			//Durability damage
			if (armorStack.getItem() instanceof PetArmorItem)
			{
				armorStack.damage(Math.round(amount/4), this, p -> p.sendEquipmentBreakStatus(EquipmentSlot.CHEST));

			}
		}



		cir.setReturnValue(super.damage(source, amount));
	}


	@Override
	protected float getVelocityMultiplier() {
		if (this.isOnSoulSpeedBlock() && EnchantmentHelper.getLevel(Enchantments.SOUL_SPEED, ((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST)) > 0) {
			return 1.0f;
		}
		return super.getVelocityMultiplier();
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
		ItemStack armor = ((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST).copyAndEmpty();
		//MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("C: " + armor.getCount() + ", D: " + armor.getDamage()));
		//System.out.println("Error, duplicate PetArmorItem death drop!");

		((WolfEntity)(Object)this).dropStack(armor);
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

	@Override
	public boolean tryAttack(Entity target) {

		ItemStack armor = ((WolfEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST);
		boolean hasMending = EnchantmentHelper.getLevel(Enchantments.MENDING, armor) > 0;

		boolean bl = target.damage(this.getDamageSources().mobAttack(this), (int)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));
		if (bl) {
			this.applyDamageEffects(this, target);
			if (hasMending)
				armor.setDamage(armor.getDamage() - 3);



		}
		return bl;
	}




/*
	@Inject(at = @At("HEAD"), method = "loadWorld")
	private void init(CallbackInfo info) {
		// This code is injected into the start of MinecraftServer.loadWorld()V
	}
	*/
}