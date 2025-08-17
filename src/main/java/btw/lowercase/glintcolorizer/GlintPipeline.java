package btw.lowercase.glintcolorizer;

import btw.lowercase.glintcolorizer.config.GlintColorizerConfig;
import btw.lowercase.glintcolorizer.mixins.accessor.RenderTypeAccessor;
import btw.lowercase.glintcolorizer.mixins.accessor.RenderTypeCompositeStateBuilderAccessor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.TriState;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

// TODO: BlendFunction.GLINT
public class GlintPipeline {
    public static final ResourceLocation GLINT_TEXTURE_PATH = ResourceLocation.fromNamespaceAndPath(GlintColorizer.MOD_ID, "textures/misc/enchanted_item_glint.png");

    public static final ShaderProgram GLINT_SHADER = new ShaderProgram(
            GlintColorizer.id("core/glint"),
            DefaultVertexFormat.POSITION_TEX,
            ShaderDefines.EMPTY
    );

    public static final RenderStateShard.ShaderStateShard GLINT_SHADER_SHARD = new RenderStateShard.ShaderStateShard(GLINT_SHADER);

    // Item
    public static final RenderType ITEM_GLINT_1ST_LAYER_RENDERTYPE = makeItemGlintLayer(new RenderStateShard.TexturingStateShard(
            "item_glint_layer_1_texturing",
            GlintPipeline::updateGlintLayer1Texturing,
            RenderSystem::resetTextureMatrix
    ), GlintLayer.FIRST, false);

    public static final RenderType SHINY_ITEM_GLINT_1ST_LAYER_RENDERTYPE = makeItemGlintLayer(new RenderStateShard.TexturingStateShard(
            "shiny_item_glint_layer_1_texturing",
            GlintPipeline::updateGlintLayer1Texturing,
            RenderSystem::resetTextureMatrix
    ), GlintLayer.FIRST, true);

    public static final RenderType ITEM_GLINT_2ND_LAYER_RENDERTYPE = makeItemGlintLayer(new RenderStateShard.TexturingStateShard(
            "item_glint_layer_2_texturing",
            GlintPipeline::updateGlintLayer2Texturing,
            RenderSystem::resetTextureMatrix
    ), GlintLayer.SECOND, false);

    public static final RenderType SHINY_ITEM_GLINT_2ND_LAYER_RENDERTYPE = makeItemGlintLayer(new RenderStateShard.TexturingStateShard(
            "shiny_item_glint_layer_2_texturing",
            GlintPipeline::updateGlintLayer2Texturing,
            RenderSystem::resetTextureMatrix
    ), GlintLayer.SECOND, true);

    private static RenderType makeItemGlintLayer(RenderStateShard.TexturingStateShard texturingStateShard, GlintLayer layer, boolean shiny) {
        RenderTypeCompositeStateBuilderAccessor compositeStateBuilder = (RenderTypeCompositeStateBuilderAccessor) RenderType.CompositeState.builder();
        compositeStateBuilder.withShaderState(GLINT_SHADER_SHARD);
        compositeStateBuilder.withTextureState(new RenderStateShard.TextureStateShard(GLINT_TEXTURE_PATH, TriState.DEFAULT, false));
        compositeStateBuilder.withTexturingState(texturingStateShard);
        compositeStateBuilder.withDepthTestState(shiny ? RenderType.NO_DEPTH_TEST : RenderType.EQUAL_DEPTH_TEST);
        compositeStateBuilder.withWriteMaskState(RenderType.COLOR_WRITE);
        compositeStateBuilder.withCullState(RenderType.CULL);
        return RenderTypeAccessor.createRenderType(
                (shiny ? "shiny_" : "") + "item_glint_layer_" + (layer.ordinal() + 1),
                DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.QUADS,
                1536,
                compositeStateBuilder.buildCompositeState(false));
    }

    private static void updateGlintLayer1Texturing() {
        final float tilt = (getSystemTime() % 3000L) / 3000.0F / 8.0F;
        RenderSystem.setTextureMatrix(new Matrix4f()
                .scale(8.0F * GlintMetadata.getRenderingOptions().scale)
                .translate(tilt * GlintMetadata.getRenderingOptions().speed, 0.0F, 0.0F)
                .rotateZ((float) Math.toRadians(GlintMetadata.getRenderingOptions().strokeOneRotation)));
    }

    private static void updateGlintLayer2Texturing() {
        final float tilt = (getSystemTime() % 4873L) / 4873.0F / 8.0F;
        RenderSystem.setTextureMatrix(new Matrix4f()
                .scale(8.0F * GlintMetadata.getRenderingOptions().scale)
                .translate(-tilt * GlintMetadata.getRenderingOptions().speed, 0.0F, 0.0F)
                .rotateZ((float) Math.toRadians(GlintMetadata.getRenderingOptions().strokeTwoRotation)));
    }

    // Armor
    public static final RenderType ARMOR_GLINT_1ST_LAYER_RENDERTYPE = makeArmorGlintLayer(new RenderStateShard.TexturingStateShard(
            "armor_glint_layer_1_texturing",
            () -> {
                final float scale = 0.33333334F * GlintColorizerConfig.instance().armorGlint.scale;
                RenderSystem.setTextureMatrix(new Matrix4f()
                        .scale(scale)
                        .rotateZ((float) Math.toRadians(30.0F - GlintColorizerConfig.instance().armorGlint.strokeOneRotation))
                        .translate(0.0F, getArmorTilt() * 0.001F * 20.0F, 0.0F));
            },
            RenderSystem::resetTextureMatrix
    ), GlintLayer.FIRST);

    public static final RenderType ARMOR_GLINT_2ND_LAYER_RENDERTYPE = makeArmorGlintLayer(new RenderStateShard.TexturingStateShard(
            "armor_glint_layer_2_texturing",
            () -> {
                final float scale = 0.33333334F * GlintColorizerConfig.instance().armorGlint.scale;
                RenderSystem.setTextureMatrix(new Matrix4f()
                        .scale(scale)
                        .rotateZ((float) Math.toRadians(30.0F - GlintColorizerConfig.instance().armorGlint.strokeTwoRotation))
                        .translate(0.0F, getArmorTilt() * (0.001F + 0.003F) * 20.0F, 0.0F));
            },
            RenderSystem::resetTextureMatrix
    ), GlintLayer.SECOND);

    private static RenderType makeArmorGlintLayer(RenderStateShard.TexturingStateShard texturingStateShard, GlintLayer layer) {
        RenderTypeCompositeStateBuilderAccessor compositeStateBuilder = (RenderTypeCompositeStateBuilderAccessor) RenderType.CompositeState.builder();
        compositeStateBuilder.withShaderState(GLINT_SHADER_SHARD);
        compositeStateBuilder.withTextureState(new RenderStateShard.TextureStateShard(GLINT_TEXTURE_PATH, TriState.DEFAULT, false));
        compositeStateBuilder.withTexturingState(texturingStateShard);
        compositeStateBuilder.withLayeringState(RenderType.VIEW_OFFSET_Z_LAYERING);
        compositeStateBuilder.withDepthTestState(RenderType.EQUAL_DEPTH_TEST);
        compositeStateBuilder.withWriteMaskState(RenderType.COLOR_WRITE);
        compositeStateBuilder.withCullState(RenderType.NO_CULL);
        return RenderTypeAccessor.createRenderType(
                "armor_glint_layer_" + (layer.ordinal() + 1),
                DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.QUADS,
                1536,
                compositeStateBuilder.buildCompositeState(false));
    }

    // Utility
    private static float getArmorTilt() {
        return (float) ((Util.getMillis() * GlintColorizerConfig.instance().armorGlint.speed * 8.0) % 300000L) / 500.0F;
    }

    private static float getSystemTime() {
        return (float) (GLFW.glfwGetTime() * 1000.0F);
    }

    static {
        CoreShaders.getProgramsToPreload().add(GLINT_SHADER);
    }
}
