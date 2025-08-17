package btw.lowercase.glintcolorizer;

import btw.lowercase.glintcolorizer.config.GlintColorizerConfig;
import btw.lowercase.glintcolorizer.config.category.BaseGlint;
import btw.lowercase.glintcolorizer.config.category.ShinyPotsCategory;
import net.minecraft.core.component.DataComponents;
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

    public static Vector3f getGlintColor(GlintLayer layer, boolean isArmor) {
        BaseGlint options = isArmor ? GlintColorizerConfig.instance().armorGlint : getRenderingOptions();
        if (GlintColorizerConfig.instance().shinyPots.useCustomColor && itemStack.getItem() instanceof PotionItem) {
            options = GlintColorizerConfig.instance().shinyPots;
            if (options instanceof ShinyPotsCategory shinyPotsCategory && shinyPotsCategory.usePotionBasedColor && itemStack.has(DataComponents.POTION_CONTENTS)) {
                PotionContents potionContents = Objects.requireNonNull(itemStack.getComponents().get(DataComponents.POTION_CONTENTS));
                return new Vector3f(new Color(potionContents.getColor(), true).getColorComponents(null));
            }
        }

        return new Vector3f((options.individualStrokes ? (layer == GlintLayer.FIRST ? options.strokeOneColor : options.strokeTwoColor) : options.color).getRGBColorComponents(null));
    }
}