package btw.lowercase.glintcolorizer.mixins.v1_21_4;

import org.spongepowered.asm.mixin.Mixin;

//? if >=1.21.4 {
import btw.lowercase.glintcolorizer.GlintMetadata;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemModelResolver.class)
public abstract class MixinItemModelResolver_StoreItemStack {
    @Inject(method = "appendItemLayers", at = @At("HEAD"))
    private void glintcolorizer$storeItemStack(
            ItemStackRenderState renderState,
            ItemStack stack,
            ItemDisplayContext displayContext,
            Level level,
            LivingEntity entity,
            int seed,
            CallbackInfo ci
    ) {
        boolean shouldApply =
                //? >=1.21.6 {
                displayContext != ItemDisplayContext.GUI;
                //? } else {
                /*true;
                 *///? }
        if (shouldApply) {
            GlintMetadata.setItemStack(stack);
        }
    }
}
//?} else {
/*@Mixin(net.minecraft.client.Minecraft.class)
public abstract class MixinItemModelResolver {}
*///?}
