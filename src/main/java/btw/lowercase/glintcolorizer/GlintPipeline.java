package btw.lowercase.glintcolorizer;

import btw.lowercase.glintcolorizer.mixins.accessor.RenderPipelinesAccessor;
import btw.lowercase.glintcolorizer.mixins.accessor.RenderTypeAccessor;
import btw.lowercase.glintcolorizer.mixins.accessor.RenderTypeCompositeStateBuilderAccessor;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.TriState;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

public class GlintPipeline {
    public static final ResourceLocation GLINT_TEXTURE_PATH = ResourceLocation.fromNamespaceAndPath(GlintColorizer.MOD_ID, "textures/misc/enchanted_item_glint.png");

    private static final RenderPipeline.Snippet GLINT_PIPELINE_SNIPPET =
            RenderPipeline.builder(RenderPipelinesAccessor.getMatricesColorFogSnippet())
                    .withVertexShader(GlintColorizer.id("core/glint"))
                    .withFragmentShader(GlintColorizer.id("core/glint"))
                    .withColorWrite(true, false)
                    .withCull(true)
                    .withBlend(BlendFunction.GLINT)
                    .withDepthTestFunction(DepthTestFunction.EQUAL_DEPTH_TEST)
                    .withVertexFormat(DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS)
                    .withSampler("Sampler0")
                    .withUniform("GlintColor", UniformType.VEC3)
                    .buildSnippet();

    public static final RenderPipeline GLINT_1ST_LAYER_PIPELINE = RenderPipelines.register(
            RenderPipeline.builder(GLINT_PIPELINE_SNIPPET)
                    .withLocation(GlintColorizer.id("pipeline/glint_layer_1"))
                    .build());

    public static final RenderPipeline GLINT_2ND_LAYER_PIPELINE = RenderPipelines.register(
            RenderPipeline.builder(GLINT_PIPELINE_SNIPPET)
                    .withLocation(GlintColorizer.id("pipeline/glint_layer_2"))
                    .build());

    public static final RenderType GLINT_1ST_LAYER_RENDERTYPE = makeItemGlintLayer(new RenderStateShard.TexturingStateShard(
            "glint_layer_1_texturing",
            () -> {
                final float tilt = (getSystemTime() % 3000L) / 3000.0F / 8.0F;
                Matrix4f matrix4f = new Matrix4f();
                matrix4f.scale(8.0F * GlintMetadata.getRenderingOptions().scale);
                matrix4f.translate(tilt * GlintMetadata.getRenderingOptions().speed, 0.0F, 0.0F);
                matrix4f.rotateZ(GlintMetadata.getRenderingOptions().strokeOneRotation * (float) Math.PI / 180.0F);
                RenderSystem.setTextureMatrix(matrix4f);
            },
            RenderSystem::resetTextureMatrix
    ), GlintLayer.FIRST);

    public static final RenderType GLINT_2ND_LAYER_RENDERTYPE = makeItemGlintLayer(new RenderStateShard.TexturingStateShard(
            "glint_layer_2_texturing",
            () -> {
                final float tilt = (getSystemTime() % 4873L) / 4873.0F / 8.0F;
                Matrix4f matrix4f = new Matrix4f();
                matrix4f.scale(8.0F * GlintMetadata.getRenderingOptions().scale);
                matrix4f.translate(-tilt * GlintMetadata.getRenderingOptions().speed, 0.0F, 0.0F);
                matrix4f.rotateZ(GlintMetadata.getRenderingOptions().strokeTwoRotation * (float) Math.PI / 180.0F);
                RenderSystem.setTextureMatrix(matrix4f);
            },
            RenderSystem::resetTextureMatrix
    ), GlintLayer.SECOND);

    private static RenderType makeItemGlintLayer(RenderStateShard.TexturingStateShard texturingStateShard, GlintLayer layer) {
        RenderTypeCompositeStateBuilderAccessor compositeStateBuilder = (RenderTypeCompositeStateBuilderAccessor) RenderType.CompositeState.builder();
        compositeStateBuilder.withTextureState(new RenderStateShard.TextureStateShard(GLINT_TEXTURE_PATH, TriState.DEFAULT, false));
        compositeStateBuilder.withTexturingState(texturingStateShard);
        compositeStateBuilder.withOutputState(RenderType.MAIN_TARGET);
        return RenderTypeAccessor.createRenderType(
                "glint_layer_" + (layer.ordinal() + 1),
                1536,
                layer == GlintLayer.FIRST ? GLINT_1ST_LAYER_PIPELINE : GLINT_2ND_LAYER_PIPELINE,
                compositeStateBuilder.buildCompositeState(false));
    }

    private static float getSystemTime() {
        return (float) (GLFW.glfwGetTime() * 1000.0F);
    }
}
