package net.viktorp04.pet_armor.mixin.client;

import net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.client.renderer.entity.state.CatRenderState;
import net.minecraft.world.entity.animal.feline.Cat;
import net.viktorp04.pet_armor.client.PetArmorRenderData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CatRenderer.class)
public abstract class CatRendererMixin {
	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/animal/feline/Cat;Lnet/minecraft/client/renderer/entity/state/CatRenderState;F)V", at = @At("TAIL"))
	private void petArmor$copyArmorToRenderState(Cat cat, CatRenderState state, float partialTicks, CallbackInfo ci) {
		((FabricRenderState) state).setData(PetArmorRenderData.CAT_ARMOR, cat.getBodyArmorItem().copy());
	}
}
