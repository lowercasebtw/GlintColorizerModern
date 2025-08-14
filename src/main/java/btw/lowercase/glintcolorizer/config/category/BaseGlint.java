package btw.lowercase.glintcolorizer.config.category;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.controller.*;
import net.minecraft.network.chat.Component;

import java.awt.*;

public class BaseGlint {
    public boolean enabled = true;
    public boolean individualStrokes = false;
    public Color color = new Color(0x8040CC);
    public Color strokeOneColor = new Color(0xFF0000);
    public int strokeOneRotation = -50;
    public Color strokeTwoColor = new Color(0x0AEAFF);
    public int strokeTwoRotation = 10;
    public float speed = 1.0F;
    public float scale = 1.0F;

    public void build(ConfigCategory.Builder builder, BaseGlint defaults, BaseGlint config) {
        builder.option(Option.<Boolean>createBuilder()
                .name(Component.literal("Enabled"))
                .description(OptionDescription.of(Component.literal("Toggle the rendering of this glint.")))
                .binding(
                        defaults.enabled,
                        () -> config.enabled,
                        (newVal) -> config.enabled = newVal)
                .controller(TickBoxControllerBuilder::create)
                .build());
        builder.option(Option.<Boolean>createBuilder()
                .name(Component.literal("Individual Strokes"))
                .description(OptionDescription.of(Component.literal("Modify Strokes Individually.")))
                .binding(
                        defaults.individualStrokes,
                        () -> config.individualStrokes,
                        (newVal) -> config.individualStrokes = newVal)
                .controller(TickBoxControllerBuilder::create)
                .build());
        builder.option(Option.<Color>createBuilder()
                .name(Component.literal("Glint Color"))
                .description(OptionDescription.of(Component.literal("Modifies the color of the enchantment glint.")))
                .binding(
                        defaults.color,
                        () -> config.color,
                        (newVal) -> config.color = newVal)
                .controller(ColorControllerBuilder::create)
                .build());
        builder.option(Option.<Color>createBuilder()
                .name(Component.literal("Stroke 1 Color"))
                .description(OptionDescription.of(Component.literal("Modifies the first stroke of the enchantment glint effect.")))
                .binding(
                        defaults.strokeOneColor,
                        () -> config.strokeOneColor,
                        (newVal) -> config.strokeOneColor = newVal)
                .controller(ColorControllerBuilder::create)
                .build());
        builder.option(Option.<Integer>createBuilder()
                .name(Component.literal("Stroke 1 Rotation"))
                .description(OptionDescription.of(Component.literal("")))
                .binding(
                        defaults.strokeOneRotation,
                        () -> config.strokeOneRotation,
                        (newVal) -> config.strokeOneRotation = newVal)
                .controller((option) -> IntegerSliderControllerBuilder.create(option).step(1).range(-180, 180))
                .build());
        builder.option(Option.<Color>createBuilder()
                .name(Component.literal("Stroke 2 Color"))
                .description(OptionDescription.of(Component.literal("Modifies the second stroke of the enchantment glint effect.")))
                .binding(
                        defaults.strokeTwoColor,
                        () -> config.strokeTwoColor,
                        (newVal) -> config.strokeTwoColor = newVal)
                .controller(ColorControllerBuilder::create)
                .build());
        builder.option(Option.<Integer>createBuilder()
                .name(Component.literal("Stroke 2 Rotation"))
                .description(OptionDescription.of(Component.literal("")))
                .binding(
                        defaults.strokeTwoRotation,
                        () -> config.strokeTwoRotation,
                        (newVal) -> config.strokeTwoRotation = newVal)
                .controller((option) -> IntegerSliderControllerBuilder.create(option).step(1).range(-180, 180))
                .build());
        builder.option(Option.<Float>createBuilder()
                .name(Component.literal("Speed"))
                .description(OptionDescription.of(Component.literal("")))
                .binding(
                        defaults.speed,
                        () -> config.speed,
                        (newVal) -> config.speed = newVal)
                .controller((option) -> FloatSliderControllerBuilder.create(option).step(0.1F).range(0.1F, 10.0F))
                .build());
        builder.option(Option.<Float>createBuilder()
                .name(Component.literal("Scale"))
                .description(OptionDescription.of(Component.literal("")))
                .binding(
                        defaults.scale,
                        () -> config.scale,
                        (newVal) -> config.scale = newVal)
                .controller((option) -> FloatSliderControllerBuilder.create(option).step(0.5F).range(0.0F, 8.0F))
                .build());
    }
}
