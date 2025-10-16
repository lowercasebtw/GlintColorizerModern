package btw.lowercase.glintcolorizer.mixins;

import btw.lowercase.glintcolorizer.GlintLayer;
import btw.lowercase.glintcolorizer.GlintMetadata;
import btw.lowercase.glintcolorizer.GlintPipeline;
import btw.lowercase.glintcolorizer.config.GlintColorizerConfig;
import com.mojang.blaze3d.vertex.MeshData;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? <=1.21.4 {
/*import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.CompiledShaderProgram;
import java.util.Objects;
*///?} else {
import com.mojang.blaze3d.systems.RenderPass;
//?}

@Mixin(
        //? <=1.21.4 {
        /*RenderType.class
        *///?} else {
        RenderType.CompositeRenderType.class
        //?}
)
public abstract class MixinRenderType {
//    @Inject(
//            method = "draw",
//            at = @At(
//                    value = "INVOKE",
//                    //? <=1.21.4 {
//                    /*target = "Lnet/minecraft/client/renderer/RenderType;setupRenderState()V",
//                    *///?} else {
//                    target = "Lnet/minecraft/client/renderer/RenderType$CompositeRenderType;setupRenderState()V",
//                    //?}
//                    shift = At.Shift.AFTER
//            )
//    )
    private void glintcolorizer$applyGlintColor(MeshData meshData, CallbackInfo ci) {
        if (GlintColorizerConfig.instance().useCustomRenderer) {
            final RenderType renderType = (RenderType) (Object) this;
            final boolean isFirstLayer = renderType == GlintPipeline.ITEM_GLINT_1ST_LAYER_RENDERTYPE || renderType == GlintPipeline.SHINY_ITEM_GLINT_1ST_LAYER_RENDERTYPE || renderType == GlintPipeline.ARMOR_GLINT_1ST_LAYER_RENDERTYPE;
            final boolean isSecondLayer = renderType == GlintPipeline.ITEM_GLINT_2ND_LAYER_RENDERTYPE || renderType == GlintPipeline.SHINY_ITEM_GLINT_2ND_LAYER_RENDERTYPE || renderType == GlintPipeline.ARMOR_GLINT_2ND_LAYER_RENDERTYPE;
            if (isFirstLayer || isSecondLayer) {
                final boolean isArmor = renderType == GlintPipeline.ARMOR_GLINT_1ST_LAYER_RENDERTYPE || renderType == GlintPipeline.ARMOR_GLINT_2ND_LAYER_RENDERTYPE;

                //? <=1.21.4 {
                /*final CompiledShaderProgram program = Objects.requireNonNull(RenderSystem.getShader());
                *///?}

//                bindUniform(
//                        program,
//                        //? >=1.21.6 {
//                        "Glint",
//                        //?} else {
//                        /*"GlintColor",
//                        *///?}
//                        GlintMetadata.getGlintColor(isFirstLayer ? GlintLayer.FIRST : GlintLayer.SECOND, isArmor)
//                );
            }
        }
    }

    @Unique
    private static void bindUniform(
            //? >=1.21.5 {
            RenderPass program,
            //?} else {
            /*CompiledShaderProgram program,
            *///?}
            String name,
            int value
    ) {
        //? <=1.21.4 {
        /*Objects.requireNonNull(program.getUniform(name)).set(value);
        *///?} else <=1.21.5 {
        /*program.setUniform(name, value);
        *///?} else {
        // TODO
        //?}
    }
}