package btw.lowercase.glintcolorizer.mixins;

import net.minecraft.client.gui.render.GuiRenderer;
import org.spongepowered.asm.mixin.Mixin;

//? >=1.21.6 {
import btw.lowercase.glintcolorizer.CustomItemStackRenderState;
import btw.lowercase.glintcolorizer.GlintMetadata;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.render.state.GuiItemRenderState;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiRenderer.class)
public abstract class MixinGuiRenderer {
    @Inject(method = "method_71055", at = @At("HEAD"))
    private void glintcolorizer$setGuiItemStack(MutableBoolean mutableBoolean, int i, int j, MutableBoolean mutableBoolean2, PoseStack poseStack, GuiItemRenderState guiItemRenderState, CallbackInfo ci) {
        if (guiItemRenderState.itemStackRenderState() instanceof CustomItemStackRenderState customItemStackRenderState) {
            GlintMetadata.setItemStack(customItemStackRenderState.getItemStack());
            GlintMetadata.setRenderMode(GlintMetadata.RenderMode.GUI);
        }
    }
}
//? } else {
/*@Mixin(GuiRenderer.class)
public abstract class MixinGuiRenderer {}
*///? }
