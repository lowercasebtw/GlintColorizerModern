package btw.lowercase.glintcolorizer.mixins;

//? if <1.21.5 {
import btw.lowercase.glintcolorizer.GlintLayer;
import btw.lowercase.glintcolorizer.GlintMetadata;
import btw.lowercase.glintcolorizer.GlintPipeline;
import btw.lowercase.glintcolorizer.config.GlintColorizerConfig;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.MeshData;
//? if >=1.21.2
import net.minecraft.client.renderer.CompiledShaderProgram;
import net.minecraft.client.renderer.RenderType;
//? if <1.21.2
/*import net.minecraft.client.renderer.ShaderInstance;*/
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
        if (GlintColorizerConfig.instance().useCustomRenderer) {
            final RenderType renderType = (RenderType) (Object) this;
            final boolean isFirstLayer = renderType == GlintPipeline.ITEM_GLINT_1ST_LAYER_RENDERTYPE || renderType == GlintPipeline.SHINY_ITEM_GLINT_1ST_LAYER_RENDERTYPE || renderType == GlintPipeline.ARMOR_GLINT_1ST_LAYER_RENDERTYPE;
            final boolean isSecondLayer = renderType == GlintPipeline.ITEM_GLINT_2ND_LAYER_RENDERTYPE || renderType == GlintPipeline.SHINY_ITEM_GLINT_2ND_LAYER_RENDERTYPE || renderType == GlintPipeline.ARMOR_GLINT_2ND_LAYER_RENDERTYPE;
            if (isFirstLayer || isSecondLayer) {
                final boolean isArmor = renderType == GlintPipeline.ARMOR_GLINT_1ST_LAYER_RENDERTYPE || renderType == GlintPipeline.ARMOR_GLINT_2ND_LAYER_RENDERTYPE;
                final
                //? if <1.21.2
                /*ShaderInstance*/
                //? if >=1.21.2
                CompiledShaderProgram
                shaderProgram = Objects.requireNonNull(RenderSystem.getShader());
                Objects.requireNonNull(shaderProgram.getUniform("GlintColor")).set(GlintMetadata.getGlintColor(isFirstLayer ? GlintLayer.FIRST : GlintLayer.SECOND, isArmor));
            }
        }
    }
}
//?} else {
/*import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Minecraft.class)
public interface MixinRenderType {

}*/
//?}


