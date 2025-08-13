package btw.lowercase.glintcolorizer.mixins;

import btw.lowercase.glintcolorizer.GlintPipeline;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderBuffers.class)
public abstract class MixinRenderBuffers {
    @Inject(method = "put", at = @At("HEAD"))
    private static void glintcolorizer$addGlintLayers(Object2ObjectLinkedOpenHashMap<RenderType, ByteBufferBuilder> map, RenderType renderType, CallbackInfo ci) {
        glintcolorizer$addRenderType(GlintPipeline.GLINT_1ST_LAYER_RENDERTYPE, map);
        glintcolorizer$addRenderType(GlintPipeline.GLINT_2ND_LAYER_RENDERTYPE, map);
    }

    @Unique
    private static void glintcolorizer$addRenderType(RenderType renderType, Object2ObjectLinkedOpenHashMap<RenderType, ByteBufferBuilder> map) {
        if (!map.containsKey(renderType)) {
            map.put(renderType, new ByteBufferBuilder(renderType.bufferSize()));
        }
    }
}
