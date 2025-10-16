package btw.lowercase.glintcolorizer.mixins.v1_21_6;

import btw.lowercase.glintcolorizer.util.ItemRenderStateStorage;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;

//? >=1.21.6 {
import btw.lowercase.glintcolorizer.config.GlintColorizerConfig;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

//? >=1.21.9 {
import net.minecraft.client.renderer.item.TrackingItemStackRenderState;
//? } else {
/*import net.minecraft.client.renderer.item.ItemStackRenderState;
 *///? }

@Mixin(GuiGraphics.class)
public abstract class MixinGuiGraphics_SaveGuiItemStack {
    @WrapOperation(
            method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V",
            at = @At(
                    value = "NEW",
                    //? >=1.21.9 {
                    target = "()Lnet/minecraft/client/renderer/item/TrackingItemStackRenderState;"
                    //? } else {
                    /*target = "()Lnet/minecraft/client/renderer/item/ItemStackRenderState;"
                    *///? }
            )
    )
    private
    //? >=1.21.9 {
    TrackingItemStackRenderState
    //? } else {
    /*ItemStackRenderState
    *///? }
    glintcolorizer$storeItemGui(
            //? >=1.21.9 {
            Operation<TrackingItemStackRenderState> original,
            //? } else {
            /*Operation<ItemStackRenderState> original,
            *///? }
            @Local(argsOnly = true) ItemStack itemStack
    ) {
        //? >=1.21.9 {
        TrackingItemStackRenderState
        //? } else {
        /*ItemStackRenderState
         *///? }
        itemStackRenderState = original.call();
        if (GlintColorizerConfig.instance().useCustomRenderer) {
            ((ItemRenderStateStorage) itemStackRenderState).glintcolorizer$setItemStack(itemStack);
        }

        return itemStackRenderState;
    }
}
//?} else {
/*@Mixin(GuiGraphics.class)
public abstract class MixinGuiGraphics_SaveGuiItemStack {}
*///?}
