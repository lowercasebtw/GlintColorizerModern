package btw.lowercase.glintcolorizer.mixins;

import btw.lowercase.glintcolorizer.config.GlintColorizerConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Item.class)
public abstract class MixinItem {
    @WrapOperation(method = "isFoil", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEnchanted()Z"))
    private boolean glintcolorizer$enablePotionGlint(ItemStack instance, Operation<Boolean> original) {
        return (instance.getItem() instanceof PotionItem && GlintColorizerConfig.instance().shinyPots.enabled) || original.call(instance);
    }
}
