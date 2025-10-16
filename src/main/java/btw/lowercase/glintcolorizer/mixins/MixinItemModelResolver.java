package btw.lowercase.glintcolorizer.mixins;

import org.spongepowered.asm.mixin.Mixin;

//? if >=1.21.4 {
import btw.lowercase.glintcolorizer.GlintMetadata;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
//? <=1.21.8
import net.minecraft.world.entity.LivingEntity;
//? >=1.21.9
/*import net.minecraft.world.entity.ItemOwner;*/
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemModelResolver.class)
public abstract class MixinItemModelResolver {
    @Inject(method = "appendItemLayers", at = @At("HEAD"))
    private void glintcolorizer$storeItemStack(
            ItemStackRenderState renderState,
            ItemStack stack,
            ItemDisplayContext displayContext,
            Level level,
            //? >=1.21.9 {
            /*ItemOwner itemOwner,
            *///?} else {
            LivingEntity entity,
            //?}
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
