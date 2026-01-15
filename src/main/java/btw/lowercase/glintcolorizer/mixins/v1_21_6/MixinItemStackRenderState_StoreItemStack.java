package btw.lowercase.glintcolorizer.mixins.v1_21_6;

import org.spongepowered.asm.mixin.Mixin;

//? >=1.21.6 {
/*import btw.lowercase.glintcolorizer.util.GlintMetadata;
import btw.lowercase.glintcolorizer.util.ItemRenderStateStorage;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.renderer.item.ItemStackRenderState;

@Mixin(ItemStackRenderState.class)
public abstract class MixinItemStackRenderState_StoreItemStack implements ItemRenderStateStorage {
    @Unique
    private ItemStack glintcolorizer$stack = ItemStack.EMPTY;

    @Unique
    private GlintMetadata.RenderMode glintcolorizer$renderMode = null;

    @Override
    public ItemStack glintcolorizer$getItemStack() {
        return glintcolorizer$stack;
    }

    @Override
    public void glintcolorizer$setItemStack(ItemStack itemStack) {
        glintcolorizer$stack = itemStack;
    }

    @Override
    public GlintMetadata.RenderMode glintcolorizer$getRenderMode() {
        return glintcolorizer$renderMode == null ? GlintMetadata.getRenderMode() : glintcolorizer$renderMode;
    }

    @Override
    public void glintcolorizer$setRenderMode(GlintMetadata.RenderMode renderMode) {
        this.glintcolorizer$renderMode = renderMode;
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void glintcolorizer$applyState(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, CallbackInfo ci) {
        GlintMetadata.setRenderMode(this.glintcolorizer$getRenderMode());
        GlintMetadata.setItemStack(this.glintcolorizer$getItemStack());
    }
}
*///?} else {
@Mixin(net.minecraft.client.Minecraft.class)
public abstract class MixinItemStackRenderState_StoreItemStack {}
//?}