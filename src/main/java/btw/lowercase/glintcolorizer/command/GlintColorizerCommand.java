package btw.lowercase.glintcolorizer.command;

import btw.lowercase.glintcolorizer.config.GlintColorizerConfig;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.Minecraft;

public class GlintColorizerCommand implements Command<FabricClientCommandSource> {
    public static LiteralArgumentBuilder<FabricClientCommandSource> create() {
        return ClientCommandManager.literal("glintcolorizer").executes(new GlintColorizerCommand());
    }

    @Override
    public int run(CommandContext<FabricClientCommandSource> context) {
        Minecraft client = context.getSource().getClient();
        client.schedule(() -> client.setScreen(GlintColorizerConfig.getConfigScreen(client.screen)));
        return Command.SINGLE_SUCCESS;
    }
}
