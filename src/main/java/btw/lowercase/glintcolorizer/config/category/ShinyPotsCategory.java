package btw.lowercase.glintcolorizer.config.category;

import dev.isxander.yacl3.api.ConfigCategory;

public class ShinyPotsCategory extends BaseGlint {
    public boolean usePotionGlint = false;
    public boolean fullSlotShine = false;
    public boolean disableGlint = false;
    public boolean useCustomColor = false;
    public boolean usePotionBasedColor = false;

    @Override
    public void build(ConfigCategory.Builder builder, BaseGlint defaults, BaseGlint config) {
        super.build(builder, defaults, config);
        ShinyPotsCategory shinyPotsDefaults = (ShinyPotsCategory) defaults;
        ShinyPotsCategory shinyPotsConfig = (ShinyPotsCategory) config;
        // TODO
    }
}
