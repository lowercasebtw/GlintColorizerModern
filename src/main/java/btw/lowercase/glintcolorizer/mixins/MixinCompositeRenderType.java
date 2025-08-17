package btw.lowercase.glintcolorizer.mixins;

import btw.lowercase.glintcolorizer.GlintLayer;
import btw.lowercase.glintcolorizer.GlintMetadata;
import btw.lowercase.glintcolorizer.GlintPipeline;
import btw.lowercase.glintcolorizer.config.GlintColorizerConfig;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.vertex.MeshData;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderType.CompositeRenderType.class)
public abstract class MixinCompositeRenderType {
    @Shadow
    @Final
    private RenderPipeline renderPipeline;

    @Inject(method = "draw", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderPass;setIndexBuffer(Lcom/mojang/blaze3d/buffers/GpuBuffer;Lcom/mojang/blaze3d/vertex/VertexFormat$IndexType;)V", shift = At.Shift.AFTER))
    private void glintcolorizer$applyGlintColor(MeshData meshData, CallbackInfo ci, @Local RenderPass renderPass) {
        if (GlintColorizerConfig.instance().useCustomRenderer) {
            final boolean isFirstLayer = this.renderPipeline == GlintPipeline.ITEM_GLINT_1ST_LAYER_PIPELINE || this.renderPipeline == GlintPipeline.SHINY_ITEM_GLINT_1ST_LAYER_PIPELINE || this.renderPipeline == GlintPipeline.ARMOR_GLINT_1ST_LAYER_PIPELINE;
            final boolean isSecondLayer = this.renderPipeline == GlintPipeline.ITEM_GLINT_2ND_LAYER_PIPELINE || this.renderPipeline == GlintPipeline.SHINY_ITEM_GLINT_2ND_LAYER_PIPELINE || this.renderPipeline == GlintPipeline.ARMOR_GLINT_2ND_LAYER_PIPELINE;
            if (isFirstLayer || isSecondLayer) {
                final boolean isArmor = this.renderPipeline == GlintPipeline.ARMOR_GLINT_1ST_LAYER_PIPELINE || this.renderPipeline == GlintPipeline.ARMOR_GLINT_2ND_LAYER_PIPELINE;
                renderPass.setUniform("GlintColor", GlintMetadata.getGlintColor(isFirstLayer ? GlintLayer.FIRST : GlintLayer.SECOND, isArmor));
            }
        }
    }
}
