package btw.lowercase.glintcolorizer;

import btw.lowercase.glintcolorizer.command.GlintColorizerCommand;
import btw.lowercase.glintcolorizer.config.GlintColorizerConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.impl.resource.loader.ResourceManagerHelperImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

public class GlintColorizer implements ClientModInitializer {
    public static final String MOD_ID = "glintcolorizer";

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    @Override
    public void onInitializeClient() {
        // Config
        GlintColorizerConfig.load();

        // Builtin Pack
        ResourceManagerHelperImpl.registerBuiltinResourcePack(
                id(MOD_ID),
                MOD_ID,
                FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(),
                ResourcePackActivationType.ALWAYS_ENABLED
        );

        // Commands
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, context) -> dispatcher.register(GlintColorizerCommand.create()));
    }
}