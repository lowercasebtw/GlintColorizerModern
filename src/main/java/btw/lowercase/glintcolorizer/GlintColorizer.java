package btw.lowercase.glintcolorizer;

import btw.lowercase.glintcolorizer.config.GlintColorizerConfig;
import com.mojang.brigadier.Command;
import dev.deftu.omnicore.api.OmniResourceLocation;
import dev.deftu.omnicore.api.client.commands.OmniClientCommands;
import dev.deftu.omnicore.api.client.screen.OmniScreens;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.polyfrost.oneconfig.internal.ui.OneConfigUI;

@Entrypoint
public class GlintColorizer implements ClientModInitializer {
	public static ResourceLocation id(String path) {
		return OmniResourceLocation.createOrThrow(GlintColorizerConstants.ID, path);
	}

	@Override
	public void onInitializeClient() {
		// Config
		GlintColorizerConfig.INSTANCE.preload();

		// Commands
		OmniClientCommands.register(OmniClientCommands.literal(GlintColorizerConstants.ID)
				.executes((context) -> {
					OmniScreens.openScreen(OneConfigUI.INSTANCE.create(), 1);
					return Command.SINGLE_SUCCESS;
				})
				.build());
	}
}
