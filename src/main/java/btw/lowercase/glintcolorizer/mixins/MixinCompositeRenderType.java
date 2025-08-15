package btw.lowercase.glintcolorizer.mixins;

import btw.lowercase.glintcolorizer.GlintLayer;
import btw.lowercase.glintcolorizer.GlintMetadata;
import btw.lowercase.glintcolorizer.GlintPipeline;
import btw.lowercase.glintcolorizer.config.GlintColorizerConfig;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.MeshData;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.renderer.RenderType;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
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

    @Unique
    private static GpuBuffer glintcolorizer$first = null;

    @Unique
    private static GpuBuffer glintcolorizer$second = null;


    @Inject(method = "draw", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderPass;setIndexBuffer(Lcom/mojang/blaze3d/buffers/GpuBuffer;Lcom/mojang/blaze3d/vertex/VertexFormat$IndexType;)V", shift = At.Shift.AFTER))
    private void glintcolorizer$applyGlintColor(MeshData meshData, CallbackInfo ci, @Local RenderPass renderPass) {
        if (GlintColorizerConfig.instance().useCustomRenderer) {
            final boolean isArmor = this.renderPipeline == GlintPipeline.ARMOR_GLINT_1ST_LAYER_PIPELINE || this.renderPipeline == GlintPipeline.ARMOR_GLINT_2ND_LAYER_PIPELINE;
            if (this.renderPipeline == GlintPipeline.ITEM_GLINT_1ST_LAYER_PIPELINE || this.renderPipeline == GlintPipeline.SHINY_ITEM_GLINT_1ST_LAYER_PIPELINE || this.renderPipeline == GlintPipeline.ARMOR_GLINT_1ST_LAYER_PIPELINE) {
                final float[] color = GlintMetadata.getGlintColor(GlintLayer.FIRST, isArmor);
                if (glintcolorizer$first == null) {
                    glintcolorizer$first = RenderSystem.getDevice().createBuffer(() -> "Float Vector #0", GpuBuffer.USAGE_UNIFORM, glintcolorizer$bufferFromFloatArray(color));
                } else {
                    RenderSystem.getDevice().createCommandEncoder().writeToBuffer(glintcolorizer$first.slice(), glintcolorizer$bufferFromFloatArray(color));
                }

                GlintMetadata.getGlintColor(GlintLayer.FIRST, isArmor);

                renderPass.setUniform("Glint", glintcolorizer$first);
            } else if (this.renderPipeline == GlintPipeline.ITEM_GLINT_2ND_LAYER_PIPELINE || this.renderPipeline == GlintPipeline.SHINY_ITEM_GLINT_2ND_LAYER_PIPELINE || this.renderPipeline == GlintPipeline.ARMOR_GLINT_2ND_LAYER_PIPELINE) {
                final float[] color = GlintMetadata.getGlintColor(GlintLayer.SECOND, isArmor);
                if (glintcolorizer$second == null) {
                    glintcolorizer$second = RenderSystem.getDevice().createBuffer(() -> "Float Vector #1", GpuBuffer.USAGE_UNIFORM, glintcolorizer$bufferFromFloatArray(color));
                } else {
                    RenderSystem.getDevice().createCommandEncoder().writeToBuffer(glintcolorizer$second.slice(), glintcolorizer$bufferFromFloatArray(color));
                }

                renderPass.setUniform("Glint", glintcolorizer$second);
            }
        }
    }

    @Inject(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType$CompositeRenderType;clearRenderState()V", shift = At.Shift.BEFORE))
    private void glintcolorizer$clearBuffers(MeshData meshData, CallbackInfo ci) {
        if (glintcolorizer$first != null) {
            glintcolorizer$first.close();
        }

        if (glintcolorizer$second != null) {
            glintcolorizer$second.close();
        }
    }

    @Unique
    private static ByteBuffer glintcolorizer$bufferFromFloatArray(float[] floats) {
        try (final MemoryStack memoryStack = MemoryStack.stackPush()) {
            return MemoryUtil.memByteBuffer(memoryStack.floats(floats));
        }
    }
}
