package btw.lowercase.glintcolorizer;

import btw.lowercase.glintcolorizer.config.GlintColorizerConfig;
import btw.lowercase.glintcolorizer.config.category.BaseGlint;
import btw.lowercase.glintcolorizer.config.category.ShinyPotsCategory;
import net.minecraft.core.component.DataComponents;
//? if >=1.21.2
import net.minecraft.util.ARGB;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionContents;
import org.joml.Vector3f;

import java.awt.*;
import java.util.Objects;

public class GlintMetadata {
    public enum RenderMode {
        HELD,
        SHINY,
        GUI,
        DROPPED,
        FRAMED
    }

    private static RenderMode renderMode = RenderMode.HELD;
    private static ItemStack itemStack = ItemStack.EMPTY;

    public static void setRenderMode(RenderMode renderMode) {
        if (!(renderMode == RenderMode.GUI && GlintMetadata.renderMode == RenderMode.SHINY)) {
            // preserve shiny render mode !
            GlintMetadata.renderMode = renderMode;
        }
    }

    public static RenderMode getRenderMode() {
        return GlintMetadata.renderMode;
    }

    public static void setItemStack(ItemStack itemStack) {
        if (!ItemStack.matches(itemStack, GlintMetadata.itemStack)) {
            GlintMetadata.itemStack = itemStack;
        }
    }

    public static ItemStack getItemStack() {
        return GlintMetadata.itemStack;
    }

    public static BaseGlint getRenderingOptions() {
        return switch (renderMode) {
            case HELD -> GlintColorizerConfig.instance().heldItemGlint;
            case SHINY -> GlintColorizerConfig.instance().shinyPots;
            case GUI -> GlintColorizerConfig.instance().guiItemGlint;
            case DROPPED -> GlintColorizerConfig.instance().droppedItemGlint;
            case FRAMED -> GlintColorizerConfig.instance().framedItemGlint;
        };
    }

    public static
    //? if >=1.21.4 {
    /*float[] */
    //?} else {
    org.joml.Vector3f
    //?}
    getGlintColor(GlintLayer layer, boolean isArmor) {
        BaseGlint options = isArmor ? GlintColorizerConfig.instance().armorGlint : getRenderingOptions();
        if (itemStack.getItem() instanceof PotionItem && GlintColorizerConfig.instance().shinyPots.useCustomColor) {
            options = GlintColorizerConfig.instance().shinyPots;
            if (options instanceof ShinyPotsCategory shinyPotsCategory && shinyPotsCategory.usePotionBasedColor && itemStack.has(DataComponents.POTION_CONTENTS)) {
                PotionContents potionContents = Objects.requireNonNull(itemStack.getComponents().get(DataComponents.POTION_CONTENTS));
                final int color = potionContents.getColor();
                //? if >=1.21.4 {
                /*return new float[]{ARGB.redFloat(color), ARGB.greenFloat(color), ARGB.blueFloat(color)};*/
                //?} elif >=1.21.2 {
                return ARGB.vector3fFromRGB24(color);
                //?} elif <1.21.2 {
                /*return new Vector3f(new Color(color, true).getColorComponents(null));
                *///?}
            }
        }

        //? if >=1.21.4 {
        /*return (options.individualStrokes ? (layer == GlintLayer.FIRST ? options.strokeOneColor : options.strokeTwoColor) : options.color).getRGBColorComponents(null);*/
        //?} else {
        return new org.joml.Vector3f((options.individualStrokes ? (layer == GlintLayer.FIRST ? options.strokeOneColor : options.strokeTwoColor) : options.color).getRGBColorComponents(null));
        //?}
    }
}