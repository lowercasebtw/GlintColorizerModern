package btw.lowercase.glintcolorizer.mixins;

import btw.lowercase.glintcolorizer.GlintLayer;
import btw.lowercase.glintcolorizer.GlintMetadata;
import btw.lowercase.glintcolorizer.GlintPipeline;
import btw.lowercase.glintcolorizer.config.GlintColorizerConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.MeshData;
import net.minecraft.client.renderer.CompiledShaderProgram;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Objects;

@Mixin(RenderType.class)
public abstract class MixinRenderType {
    @WrapOperation(method = "draw", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferUploader;drawWithShader(Lcom/mojang/blaze3d/vertex/MeshData;)V"))
    private void glintcolorizer$applyGlintColor(MeshData meshData, Operation<Void> original) {
        final RenderType renderType = (RenderType) (Object) this;
        final boolean isFirstLayer = renderType == GlintPipeline.ITEM_GLINT_1ST_LAYER_RENDERTYPE || renderType == GlintPipeline.SHINY_ITEM_GLINT_1ST_LAYER_RENDERTYPE || renderType == GlintPipeline.ARMOR_GLINT_1ST_LAYER_RENDERTYPE;
        final boolean isSecondLayer = renderType == GlintPipeline.ITEM_GLINT_2ND_LAYER_RENDERTYPE || renderType == GlintPipeline.SHINY_ITEM_GLINT_2ND_LAYER_RENDERTYPE || renderType == GlintPipeline.ARMOR_GLINT_2ND_LAYER_RENDERTYPE;
        if (GlintColorizerConfig.instance().useCustomRenderer && (isFirstLayer || isSecondLayer)) {
            final boolean isArmor = renderType == GlintPipeline.ARMOR_GLINT_1ST_LAYER_RENDERTYPE || renderType == GlintPipeline.ARMOR_GLINT_2ND_LAYER_RENDERTYPE;
            final CompiledShaderProgram shaderProgram = Objects.requireNonNull(RenderSystem.getShader());
            Objects.requireNonNull(shaderProgram.getUniform("GlintColor")).set(GlintMetadata.getGlintColor(isFirstLayer ? GlintLayer.FIRST : GlintLayer.SECOND, isArmor));
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
        }

        original.call(meshData);
        if (GlintColorizerConfig.instance().useCustomRenderer && (isFirstLayer || isSecondLayer)) {
            RenderSystem.defaultBlendFunc();
        }
    }
}
