package net.viktorp04.pet_armor.mixin.client;

import net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.client.renderer.entity.state.ParrotRenderState;
import net.minecraft.world.entity.animal.parrot.Parrot;
import net.viktorp04.pet_armor.client.PetArmorRenderData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParrotRenderer.class)
public abstract class ParrotRendererMixin {
	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/animal/parrot/Parrot;Lnet/minecraft/client/renderer/entity/state/ParrotRenderState;F)V", at = @At("TAIL"))
	private void petArmor$copyArmorToRenderState(Parrot parrot, ParrotRenderState state, float partialTicks, CallbackInfo ci) {
		((FabricRenderState) state).setData(PetArmorRenderData.PARROT_ARMOR, parrot.getBodyArmorItem().copy());
	}
}
