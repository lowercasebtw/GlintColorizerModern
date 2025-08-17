package btw.lowercase.glintcolorizer.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShaderInstance.class)
public abstract class MixinShaderInstance {
    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;withDefaultNamespace(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;"))
    private ResourceLocation glintcolorizer$useCustomNamespace$init(String string, Operation<ResourceLocation> original, @Local(argsOnly = true) String shaderIdentifier) {
        ResourceLocation shaderLocation = ResourceLocation.parse(shaderIdentifier);
        return ResourceLocation.fromNamespaceAndPath(shaderLocation.getNamespace(), "shaders/core/" + shaderLocation.getPath() + ".json");
    }

    @WrapOperation(method = "getOrCreate", at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;withDefaultNamespace(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;"))
    private static ResourceLocation glintcolorizer$useCustomNamespace$getOrCreate(String string, Operation<ResourceLocation> original) {
        return ResourceLocation.parse(string);
    }
}
