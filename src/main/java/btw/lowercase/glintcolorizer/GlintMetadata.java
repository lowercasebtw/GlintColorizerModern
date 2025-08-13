package btw.lowercase.glintcolorizer;

import btw.lowercase.glintcolorizer.config.GlintColorizerConfig;
import btw.lowercase.glintcolorizer.config.category.BaseGlint;

public class GlintMetadata {
    public enum RenderMode {
        HELD,
        SHINY,
        GUI,
        DROPPED,
        FRAMED
    }

    private static RenderMode renderMode = RenderMode.HELD;

    public static void setRenderMode(RenderMode renderMode) {
        if (!(renderMode == RenderMode.GUI && GlintMetadata.renderMode == RenderMode.SHINY)) {
            // preserve shiny render mode !
            GlintMetadata.renderMode = renderMode;
        }
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

    public static float[] getColor(GlintLayer layer) {
        BaseGlint options = getRenderingOptions();
        if (options.individualStrokes) {
            if (layer == GlintLayer.FIRST) {
                return options.strokeOneColor.getRGBColorComponents(null);
            } else {
                return options.strokeTwoColor.getRGBColorComponents(null);
            }
        }

        return options.color.getRGBColorComponents(null);
    }
}