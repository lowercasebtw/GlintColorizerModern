package btw.lowercase.glintcolorizer.mixins;

import btw.lowercase.glintcolorizer.util.GlintLayer;
import btw.lowercase.glintcolorizer.util.GlintMetadata;
import btw.lowercase.glintcolorizer.util.GlintPipeline;
import btw.lowercase.glintcolorizer.config.GlintColorizerConfig;
import com.llamalad7.mixinextras.sugar.Local;
//? >=1.21.5
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.vertex.MeshData;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
        RenderType.CompositeRenderType.class
)
public abstract class MixinRenderType_ApplyGlintColor {
    //? >=1.21.5 {
    @org.spongepowered.asm.mixin.Shadow
    @org.spongepowered.asm.mixin.Final
    private com.mojang.blaze3d.pipeline.RenderPipeline renderPipeline;

    //? >=1.21.6 {
    @org.spongepowered.asm.mixin.Unique
    private static com.mojang.blaze3d.buffers.GpuBuffer glintcolorizer$colorGpuBuffer = null;

    @org.spongepowered.asm.mixin.Unique
    private static final int glintcolorizer$UBO_SIZE = new com.mojang.blaze3d.buffers.Std140SizeCalculator().putInt().get();

    @org.spongepowered.asm.mixin.Unique
    private static java.nio.ByteBuffer glintcolorizer$getBuffer(int value) {
        try (final org.lwjgl.system.MemoryStack memoryStack = org.lwjgl.system.MemoryStack.stackPush()) {
            return com.mojang.blaze3d.buffers.Std140Builder.onStack(memoryStack, glintcolorizer$UBO_SIZE).putInt(value).get();
        }
    }

    @Inject(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType$CompositeRenderType;setupRenderState()V", shift = At.Shift.AFTER))
    private void glintcolorizer$updateGlintColor(MeshData meshData, CallbackInfo ci) {
        if (GlintColorizerConfig.instance().useCustomRenderer) {
            java.nio.ByteBuffer byteBuffer;
            final boolean isFirstLayer = this.renderPipeline == GlintPipeline.ITEM_GLINT_1ST_LAYER_PIPELINE || this.renderPipeline == GlintPipeline.SHINY_ITEM_GLINT_1ST_LAYER_PIPELINE || this.renderPipeline == GlintPipeline.ARMOR_GLINT_1ST_LAYER_PIPELINE;
            final boolean isSecondLayer = this.renderPipeline == GlintPipeline.ITEM_GLINT_2ND_LAYER_PIPELINE || this.renderPipeline == GlintPipeline.SHINY_ITEM_GLINT_2ND_LAYER_PIPELINE || this.renderPipeline == GlintPipeline.ARMOR_GLINT_2ND_LAYER_PIPELINE;
            if (isFirstLayer || isSecondLayer) {
                final boolean isArmor = this.renderPipeline == GlintPipeline.ARMOR_GLINT_1ST_LAYER_PIPELINE || this.renderPipeline == GlintPipeline.ARMOR_GLINT_2ND_LAYER_PIPELINE;
                byteBuffer = glintcolorizer$getBuffer(GlintMetadata.getGlintColor(isFirstLayer ? GlintLayer.FIRST : GlintLayer.SECOND, isArmor));
            } else {
                byteBuffer = glintcolorizer$getBuffer(0);
            }

            com.mojang.blaze3d.systems.GpuDevice gpuDevice = com.mojang.blaze3d.systems.RenderSystem.getDevice();
            if (glintcolorizer$colorGpuBuffer == null) {
                glintcolorizer$colorGpuBuffer = gpuDevice.createBuffer(() -> "Glint Color UBO", 136, glintcolorizer$UBO_SIZE);
            } else {
                gpuDevice.createCommandEncoder().writeToBuffer(glintcolorizer$colorGpuBuffer.slice(), byteBuffer);
            }
        }
    }

    @Inject(method = "draw", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderPass;setIndexBuffer(Lcom/mojang/blaze3d/buffers/GpuBuffer;Lcom/mojang/blaze3d/vertex/VertexFormat$IndexType;)V", shift = At.Shift.AFTER))
    private void glintcolorizer$applyGlintColor(MeshData meshData, CallbackInfo ci, @Local RenderPass renderPass) {
        if (GlintColorizerConfig.instance().useCustomRenderer && (this.renderPipeline == GlintPipeline.ITEM_GLINT_1ST_LAYER_PIPELINE ||
                this.renderPipeline == GlintPipeline.SHINY_ITEM_GLINT_1ST_LAYER_PIPELINE ||
                this.renderPipeline == GlintPipeline.ITEM_GLINT_2ND_LAYER_PIPELINE ||
                this.renderPipeline == GlintPipeline.SHINY_ITEM_GLINT_2ND_LAYER_PIPELINE ||
                this.renderPipeline == GlintPipeline.ARMOR_GLINT_1ST_LAYER_PIPELINE ||
                this.renderPipeline == GlintPipeline.ARMOR_GLINT_2ND_LAYER_PIPELINE)) {
            renderPass.setUniform("Glint", glintcolorizer$colorGpuBuffer);
        }
    }
    //?} else {
    /*@Inject(method = "draw", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderPass;setIndexBuffer(Lcom/mojang/blaze3d/buffers/GpuBuffer;Lcom/mojang/blaze3d/vertex/VertexFormat$IndexType;)V", shift = At.Shift.AFTER))
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
    *///?}
}