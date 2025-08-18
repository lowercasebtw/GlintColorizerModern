package btw.lowercase.glintcolorizer.mixins.accessor;

//? if >=1.21.5
/*import com.mojang.blaze3d.pipeline.RenderPipeline;*/
//? if <1.21.5
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RenderType.class)
public interface RenderTypeAccessor {
    @Invoker("create")
    static RenderType.CompositeRenderType createRenderType(String string,
                                                           //? if <1.21.5
                                                           VertexFormat vertexFormat, VertexFormat.Mode mode,
                                                           int size,
                                                           //? if >=1.21.5
                                                           /*RenderPipeline renderPipeline,*/
                                                           RenderType.CompositeState compositeState) {
        return null;
    }
}
