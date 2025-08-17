package btw.lowercase.glintcolorizer.mixins;

import btw.lowercase.glintcolorizer.GlintMetadata;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemModelResolver.class)
public abstract class MixinItemModelResolver {
    @Inject(method = "updateForTopItem", at = @At("HEAD"))
    private void glintcolorizer$storeItemStack(ItemStackRenderState itemStackRenderState, ItemStack itemStack, ItemDisplayContext itemDisplayContext, boolean bl, Level level, LivingEntity livingEntity, int i, CallbackInfo ci) {
        GlintMetadata.setItemStack(itemStack);
    }
}
