package btw.lowercase.glintcolorizer.mixins.v1_21_6;

import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;

//? >=1.21.6 {
import btw.lowercase.glintcolorizer.util.CustomItemStackRenderState;
import btw.lowercase.glintcolorizer.config.GlintColorizerConfig;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.item.ItemStackRenderState;

@Mixin(GuiGraphics.class)
public abstract class MixinGuiGraphics_SaveGuiItemStack {
    @WrapOperation(
            method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V",
            at = @At(
                    value = "NEW",
                    target = "()Lnet/minecraft/client/renderer/item/ItemStackRenderState;"
            )
    )
    private ItemStackRenderState glintcolorizer$storeItemGui(Operation<ItemStackRenderState> original, @Local(argsOnly = true) ItemStack itemStack) {
        if (GlintColorizerConfig.instance().useCustomRenderer) {
            return new CustomItemStackRenderState(itemStack);
        } else {
            return original.call();
        }
    }
}
//? } else {
/*@Mixin(GuiGraphics.class)
public abstract class MixinGuiGraphics {}
*///? }
