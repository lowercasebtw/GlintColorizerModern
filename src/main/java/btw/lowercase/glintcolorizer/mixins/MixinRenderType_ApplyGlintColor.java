package btw.lowercase.glintcolorizer.mixins;

import btw.lowercase.glintcolorizer.util.GlintLayer;
import btw.lowercase.glintcolorizer.util.GlintMetadata;
import btw.lowercase.glintcolorizer.util.GlintPipeline;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.MeshData;
import net.minecraft.client.renderer.CompiledShaderProgram;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
		//? >=1.21.5 {
		/*RenderType.CompositeRenderType.class
		*///? } else {
		RenderType.class
		//? }
)
public abstract class MixinRenderType_ApplyGlintColor {
	@Inject(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;setupRenderState()V", shift = At.Shift.AFTER))
	private void glintcolorizer$applyShaderSettings(final MeshData meshData, final CallbackInfo ci) {
		final CompiledShaderProgram shaderProgram = RenderSystem.getShader();
		if (shaderProgram != null) {
			shaderProgram.safeGetUniform("GlintColor").set(GlintMetadata.getGlintColor(glintcolorizer$getLayer(), glintcolorizer$isArmor()));
		}
	}

	@Unique
	private boolean glintcolorizer$isArmor() {
		final RenderType thiz = (RenderType) (Object) this;
		return thiz == GlintPipeline.ARMOR_GLINT_1ST_LAYER_RENDERTYPE
				|| thiz == GlintPipeline.ARMOR_GLINT_2ND_LAYER_RENDERTYPE;
	}

	@Unique
	private GlintLayer glintcolorizer$getLayer() {
		final RenderType thiz = (RenderType) (Object) this;
		if (thiz == GlintPipeline.ITEM_GLINT_1ST_LAYER_RENDERTYPE
				|| thiz == GlintPipeline.ARMOR_GLINT_1ST_LAYER_RENDERTYPE
				|| thiz == GlintPipeline.SHINY_ITEM_GLINT_1ST_LAYER_RENDERTYPE) {
			return GlintLayer.FIRST;
		} else {
			return GlintLayer.SECOND;
		}
	}
}