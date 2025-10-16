package btw.lowercase.glintcolorizer.util;

//? >=1.21.6 {
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemStack;

// TODO/NOTE: Possibly instead store inside original w/ mixin incase a different mod overrides like we do
public class CustomItemStackRenderState extends ItemStackRenderState {
    private final ItemStack itemStack;

    public CustomItemStackRenderState(ItemStack itemStack) {
        super();
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
//? } else {
/*public class CustomItemStackRenderState {}
*///? }
