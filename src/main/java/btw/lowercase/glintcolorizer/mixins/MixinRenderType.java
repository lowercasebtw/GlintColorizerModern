package btw.lowercase.glintcolorizer.mixins;

import btw.lowercase.glintcolorizer.GlintLayer;
import btw.lowercase.glintcolorizer.GlintMetadata;
import btw.lowercase.glintcolorizer.GlintPipeline;
import btw.lowercase.glintcolorizer.config.GlintColorizerConfig;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.MeshData;
import net.minecraft.client.renderer.CompiledShaderProgram;
import net.minecraft.client.renderer.RenderType;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(RenderType.class)
public abstract class MixinRenderType {
    @Inject(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;setupRenderState()V", shift = At.Shift.AFTER))
    private void glintcolorizer$applyGlintColor(MeshData meshData, CallbackInfo ci) {
        final RenderType renderType = (RenderType) (Object) this;
        final boolean isFirstLayer = renderType == GlintPipeline.ITEM_GLINT_1ST_LAYER_RENDERTYPE || renderType == GlintPipeline.SHINY_ITEM_GLINT_1ST_LAYER_RENDERTYPE || renderType == GlintPipeline.ARMOR_GLINT_1ST_LAYER_RENDERTYPE;
        final boolean isSecondLayer = renderType == GlintPipeline.ITEM_GLINT_2ND_LAYER_RENDERTYPE || renderType == GlintPipeline.SHINY_ITEM_GLINT_2ND_LAYER_RENDERTYPE || renderType == GlintPipeline.ARMOR_GLINT_2ND_LAYER_RENDERTYPE;
        if (GlintColorizerConfig.instance().useCustomRenderer && (isFirstLayer || isSecondLayer)) {
            final boolean isArmor = renderType == GlintPipeline.ARMOR_GLINT_1ST_LAYER_RENDERTYPE || renderType == GlintPipeline.ARMOR_GLINT_2ND_LAYER_RENDERTYPE;
            final CompiledShaderProgram shaderProgram = Objects.requireNonNull(RenderSystem.getShader());
            final Vector3f color = GlintMetadata.getGlintColor(isFirstLayer ? GlintLayer.FIRST : GlintLayer.SECOND, isArmor);
            Objects.requireNonNull(shaderProgram.getUniform("GlintColor")).set(color);
        }
    }
}
