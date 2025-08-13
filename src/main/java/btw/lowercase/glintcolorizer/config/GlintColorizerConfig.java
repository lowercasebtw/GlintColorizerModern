package btw.lowercase.glintcolorizer.config;

import btw.lowercase.glintcolorizer.config.category.*;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class GlintColorizerConfig {
    private static final ConfigClassHandler<GlintColorizerConfig> CONFIG = ConfigClassHandler.createBuilder(GlintColorizerConfig.class)
            .serializer((config) -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("glintcolorizer.json"))
                    .build()
            ).build();

    private static ConfigCategory.Builder createGlintCategory(String name, Consumer<ConfigCategory.Builder> consumer) {
        ConfigCategory.Builder category = ConfigCategory.createBuilder();
        category.name(Component.literal(name));
        consumer.accept(category);
        return category;
    }

    public static Screen getConfigScreen(@Nullable Screen parent) {
        return YetAnotherConfigLib.create(CONFIG, (defaults, config, builder) -> {
            builder.title(Component.literal("Glint Colorizer"));

            ConfigCategory.Builder generalCategory = ConfigCategory.createBuilder();
            generalCategory.name(Component.literal("General"));
            generalCategory.option(Option.<Boolean>createBuilder()
                    .name(Component.literal("Use Custom Renderer"))
                    .description(OptionDescription.of(Component.literal("Replaces the vanilla glint renderer with a custom one to allow customizability.")))
                    .binding(
                            defaults.useCustomRenderer,
                            () -> config.useCustomRenderer,
                            (newVal) -> config.useCustomRenderer = newVal)
                    .controller(TickBoxControllerBuilder::create)
                    .build());
            builder.category(generalCategory.build());

            builder.category(createGlintCategory("Armor Glint", (category) -> config.armorGlint.build(category, defaults.armorGlint, config.armorGlint)).build());
            builder.category(createGlintCategory("Held Item Glint", (category) -> config.heldItemGlint.build(category, defaults.heldItemGlint, config.heldItemGlint)).build());
            builder.category(createGlintCategory("Gui Item Glint", (category) -> config.guiItemGlint.build(category, defaults.guiItemGlint, config.guiItemGlint)).build());
            builder.category(createGlintCategory("Framed Item Glint", (category) -> config.framedItemGlint.build(category, defaults.framedItemGlint, config.framedItemGlint)).build());
            builder.category(createGlintCategory("Dropped Item Glint", (category) -> config.droppedItemGlint.build(category, defaults.droppedItemGlint, config.droppedItemGlint)).build());
            builder.category(createGlintCategory("Shiny Pots", (category) -> config.shinyPots.build(category, defaults.shinyPots, config.shinyPots)).build());

            return builder;
        }).generateScreen(parent);
    }

    public static void load() {
        CONFIG.load();
    }

    public static GlintColorizerConfig instance() {
        return CONFIG.instance();
    }

    // General
    @SerialEntry
    public boolean useCustomRenderer = true;

    // Armor Glint
    @SerialEntry
    public ArmorGlintCategory armorGlint = new ArmorGlintCategory();

    // Held Item Glint
    @SerialEntry
    public HeldItemGlintCategory heldItemGlint = new HeldItemGlintCategory();

    // Gui Item Glint
    @SerialEntry
    public GuiItemGlintCategory guiItemGlint = new GuiItemGlintCategory();

    // Framed Item Glint
    @SerialEntry
    public FramedItemGlintCategory framedItemGlint = new FramedItemGlintCategory();

    // Dropped Item Glint
    @SerialEntry
    public DroppedItemGlintCategory droppedItemGlint = new DroppedItemGlintCategory();

    // Shiny Pots
    @SerialEntry
    @AutoGen(category = "shiny_pots")
    public ShinyPotsCategory shinyPots = new ShinyPotsCategory();
}
