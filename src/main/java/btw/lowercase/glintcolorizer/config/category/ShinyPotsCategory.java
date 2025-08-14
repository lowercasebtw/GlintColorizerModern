package btw.lowercase.glintcolorizer.config.category;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.network.chat.Component;

public class ShinyPotsCategory extends BaseGlint {
    public boolean usePotionGlint = false;
    public boolean fullSlotShine = false;
    public boolean useCustomColor = false;
    public boolean usePotionBasedColor = false;

    public ShinyPotsCategory() {
        this.enabled = false;
    }

    @Override
    public void build(ConfigCategory.Builder builder, BaseGlint defaults, BaseGlint config) {
        ShinyPotsCategory shinyPotsDefaults = (ShinyPotsCategory) defaults;
        ShinyPotsCategory shinyPotsConfig = (ShinyPotsCategory) config;
        builder.option(Option.<Boolean>createBuilder()
                .name(Component.literal("Use Potion Glint"))
                .description(OptionDescription.of(Component.literal("")))
                .binding(
                        shinyPotsDefaults.usePotionGlint,
                        () -> shinyPotsConfig.usePotionGlint,
                        (newVal) -> shinyPotsConfig.usePotionGlint = newVal)
                .controller(TickBoxControllerBuilder::create)
                .build());
        builder.option(Option.<Boolean>createBuilder()
                .name(Component.literal("Use Full Slot Shine"))
                .description(OptionDescription.of(Component.literal("")))
                .binding(
                        shinyPotsDefaults.fullSlotShine,
                        () -> shinyPotsConfig.fullSlotShine,
                        (newVal) -> shinyPotsConfig.fullSlotShine = newVal)
                .controller(TickBoxControllerBuilder::create)
                .build());
        builder.option(Option.<Boolean>createBuilder()
                .name(Component.literal("Use Custom Color"))
                .description(OptionDescription.of(Component.literal("")))
                .binding(
                        shinyPotsDefaults.useCustomColor,
                        () -> shinyPotsConfig.useCustomColor,
                        (newVal) -> shinyPotsConfig.useCustomColor = newVal)
                .controller(TickBoxControllerBuilder::create)
                .build());
        builder.option(Option.<Boolean>createBuilder()
                .name(Component.literal("Use Potion Color For Glint"))
                .description(OptionDescription.of(Component.literal("")))
                .binding(
                        shinyPotsDefaults.usePotionBasedColor,
                        () -> shinyPotsConfig.usePotionBasedColor,
                        (newVal) -> shinyPotsConfig.usePotionBasedColor = newVal)
                .controller(TickBoxControllerBuilder::create)
                .build());
        super.build(builder, defaults, config);
    }
}
