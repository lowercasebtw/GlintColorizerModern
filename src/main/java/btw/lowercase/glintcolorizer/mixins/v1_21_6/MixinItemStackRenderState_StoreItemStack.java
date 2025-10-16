package btw.lowercase.glintcolorizer.mixins.v1_21_6;

import org.spongepowered.asm.mixin.Mixin;

import btw.lowercase.glintcolorizer.util.ItemRenderStateStorage;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ItemStackRenderState.class)
public abstract class MixinItemStackRenderState_StoreItemStack implements ItemRenderStateStorage {
    @Unique
    private ItemStack glintcolorizer$stack = ItemStack.EMPTY;

    @Override
    public ItemStack glintcolorizer$getItemStack() {
        return glintcolorizer$stack;
    }

    @Override
    public void glintcolorizer$setItemStack(ItemStack itemStack) {
        glintcolorizer$stack = itemStack;
    }
}
