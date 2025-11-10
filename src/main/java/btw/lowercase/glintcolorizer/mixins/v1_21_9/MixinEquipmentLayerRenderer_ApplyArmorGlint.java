package btw.lowercase.glintcolorizer.mixins.v1_21_9;

import org.spongepowered.asm.mixin.Mixin;

//? >=1.21.9 {
/*import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;

@Mixin(EquipmentLayerRenderer.class)
public abstract class MixinEquipmentLayerRenderer_ApplyArmorGlint {
    // TODO
}
*///?} else {
@Mixin(net.minecraft.client.Minecraft.class)
public abstract class MixinEquipmentLayerRenderer_ApplyArmorGlint {}
//?}
