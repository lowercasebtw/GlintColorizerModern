package btw.lowercase.glintcolorizer;

import btw.lowercase.glintcolorizer.command.GlintColorizerCommand;
import btw.lowercase.glintcolorizer.config.GlintColorizerConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.resources.ResourceLocation;


import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
//? if fabric
import net.fabricmc.api.ModInitializer;
//? if neoforge {
/*import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
*///?}


//? if neoforge {
/*@Mod(value = "@MODID@", dist = Dist.CLIENT)
 *///?} else {
@Entrypoint
//?}
public class GlintColorizer /*? if fabric {*/ implements ModInitializer /*?}*/ {
    public static final String MOD_ID = "glintcolorizer";

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    //? if fabric {
    @Override
    public void onInitialize() {
        // Config
        GlintColorizerConfig.load();

        // Commands
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, context) -> dispatcher.register(GlintColorizerCommand.create()));
    }
    //?}

    //? if neoforge {
    /*public ExampleMod() {
        GlintColorizerConfig.load();
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (client, parent) -> ExampleConfig.configScreen(parent));
    }
	*///?}
}
