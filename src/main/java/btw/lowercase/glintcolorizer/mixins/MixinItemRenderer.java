package btw.lowercase.glintcolorizer.mixins;

import btw.lowercase.glintcolorizer.GlintMetadata;
import btw.lowercase.glintcolorizer.GlintPipeline;
import btw.lowercase.glintcolorizer.config.GlintColorizerConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {
    @Inject(method = "renderItem", at = @At("HEAD"))
    private static void glintcolorizer$storeDisplayType(ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, int[] is, List<BakedQuad> list, RenderType renderType, ItemStackRenderState.FoilType foilType, CallbackInfo ci) {
        GlintMetadata.setRenderMode(switch (itemDisplayContext) {
            case FIRST_PERSON_RIGHT_HAND, THIRD_PERSON_RIGHT_HAND, FIRST_PERSON_LEFT_HAND, THIRD_PERSON_LEFT_HAND ->
                    GlintMetadata.RenderMode.HELD;
            case FIXED -> GlintMetadata.RenderMode.FRAMED;
            case GROUND -> GlintMetadata.RenderMode.DROPPED;
            default -> GlintMetadata.RenderMode.GUI;
        });
    }

    @Inject(method = "getFoilBuffer", at = @At("RETURN"), cancellable = true)
    private static void glintcolorizer$replaceWithCustomRenderer$default(MultiBufferSource multiBufferSource, RenderType renderType, boolean isEntity, boolean hasFoil, CallbackInfoReturnable<VertexConsumer> cir) {
        if (GlintColorizerConfig.instance().useCustomRenderer && hasFoil) {
            VertexConsumer firstGlintLayerVertexConsumer = multiBufferSource.getBuffer(GlintPipeline.GLINT_1ST_LAYER_RENDERTYPE);
            VertexConsumer secondGlintLayerVertexConsumer = multiBufferSource.getBuffer(GlintPipeline.GLINT_2ND_LAYER_RENDERTYPE);
            VertexConsumer itemVertexConsumer = multiBufferSource.getBuffer(renderType);
            cir.setReturnValue(VertexMultiConsumer.create(firstGlintLayerVertexConsumer, VertexMultiConsumer.create(secondGlintLayerVertexConsumer, itemVertexConsumer)));
        }
    }
}
