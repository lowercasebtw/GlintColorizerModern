package btw.lowercase.glintcolorizer.mixins;

//? if >=1.21.5 {
import btw.lowercase.glintcolorizer.GlintLayer;
import btw.lowercase.glintcolorizer.GlintMetadata;
import btw.lowercase.glintcolorizer.GlintPipeline;
import btw.lowercase.glintcolorizer.config.GlintColorizerConfig;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.MeshData;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.ARGB;
import org.lwjgl.system.MemoryStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.ByteBuffer;

@Mixin(RenderType.CompositeRenderType.class)
public abstract class MixinCompositeRenderType {
    @Shadow
    @Final
    private RenderPipeline renderPipeline;

    //? if <1.21.6 {
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
    *///?} else {
    @Unique
    private static GpuBuffer glintcolorizer$colorGpuBuffer = null;

    @Unique
    private static final int UBO_SIZE = new com.mojang.blaze3d.buffers.Std140SizeCalculator().putInt().get();

    @Inject(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType$CompositeRenderType;setupRenderState()V", shift = At.Shift.AFTER))
    private void glintcolorizer$updateGlintColor(MeshData meshData, CallbackInfo ci) {
        if (GlintColorizerConfig.instance().useCustomRenderer) {
            ByteBuffer byteBuffer;
            final boolean isFirstLayer = this.renderPipeline == GlintPipeline.ITEM_GLINT_1ST_LAYER_PIPELINE || this.renderPipeline == GlintPipeline.SHINY_ITEM_GLINT_1ST_LAYER_PIPELINE || this.renderPipeline == GlintPipeline.ARMOR_GLINT_1ST_LAYER_PIPELINE;
            final boolean isSecondLayer = this.renderPipeline == GlintPipeline.ITEM_GLINT_2ND_LAYER_PIPELINE || this.renderPipeline == GlintPipeline.SHINY_ITEM_GLINT_2ND_LAYER_PIPELINE || this.renderPipeline == GlintPipeline.ARMOR_GLINT_2ND_LAYER_PIPELINE;
            if (isFirstLayer || isSecondLayer) {
                final boolean isArmor = this.renderPipeline == GlintPipeline.ARMOR_GLINT_1ST_LAYER_PIPELINE || this.renderPipeline == GlintPipeline.ARMOR_GLINT_2ND_LAYER_PIPELINE;
                byteBuffer = glintcolorizer$getBuffer(GlintMetadata.getGlintColor(isFirstLayer ? GlintLayer.FIRST : GlintLayer.SECOND, isArmor));
            } else {
                byteBuffer = glintcolorizer$getBuffer(ARGB.white(0.0F));
            }

            if (glintcolorizer$colorGpuBuffer == null) {
                glintcolorizer$colorGpuBuffer = RenderSystem.getDevice().createBuffer(() -> "Glint Color UBO", GpuBuffer.USAGE_UNIFORM | GpuBuffer.USAGE_COPY_DST, UBO_SIZE);
            } else {
                RenderSystem.getDevice().createCommandEncoder().writeToBuffer(glintcolorizer$colorGpuBuffer.slice(), byteBuffer);
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

    @Unique
    private static ByteBuffer glintcolorizer$getBuffer(int value) {
        try (final MemoryStack memoryStack = MemoryStack.stackPush()) {
            return com.mojang.blaze3d.buffers.Std140Builder.onStack(memoryStack, UBO_SIZE).putInt(value).get();
        }
    }
    //?}

}
//?} else {
/*import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Minecraft.class)
public interface MixinCompositeRenderType {

}
*///?}
