package com.liammwb.dontexplode_mod;

import com.liammwb.dontexplode_mod.command.impl.StartModCommand;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(DontExplode_Mod.MOD_ID)
public class DontExplode_Mod
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "dont_explode";




    public DontExplode_Mod() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }


    @SubscribeEvent
    public void playerLogin(final PlayerEvent.PlayerLoggedInEvent e)
    {
        MinecraftServer server = Minecraft.getInstance().getIntegratedServer();

        try
        {
            assert server != null;
            server.getCommandManager().getDispatcher().execute("gamerule sendCommandFeedback false", server.getCommandSource());
            server.getCommandManager().getDispatcher().execute("give @a written_book{pages:['[\"\",{\"text\":\"The \\'Don\\'t Explode\\' Challenge!\",\"bold\":true},{\"text\":\"\\\\n\\\\n\\\\nThis is a \\'challenge\\' mod and is best played with 3 or more players!\\\\n\\\\n\\\\nBut can be played with less and even solo!\\\\n\\\\n \",\"color\":\"reset\"}]','[\"\",{\"text\":\"There are pre-set times of:\\\\n\\\\n\"},{\"text\":\"10 Seconds (Extreme)\",\"color\":\"dark_red\"},{\"text\":\"\\\\n\\\\n\",\"color\":\"reset\"},{\"text\":\"30 Seconds (Hard)\",\"color\":\"dark_purple\"},{\"text\":\"\\\\n\\\\n\",\"color\":\"reset\"},{\"text\":\"60 Seconds (Normal)\",\"color\":\"aqua\"},{\"text\":\"\\\\n\\\\n\",\"color\":\"reset\"},{\"text\":\"90 Seconds (Easy)\",\"color\":\"dark_green\"}]','[\"\",{\"text\":\"To run the mod do:\",\"bold\":true},{\"text\":\"\\\\n\\\\n/start_exploding_ \\\\nthen select a time!\\\\n\\\\n\\\\n\",\"color\":\"reset\"},{\"text\":\"To stop the mod do:\",\"bold\":true},{\"text\":\"\\\\n\\\\n/stop_exploding\",\"color\":\"reset\"}]','[\"\",{\"text\":\"Thank You\\\\nfor Downloading! \",\"bold\":true},{\"text\":\"\\\\n\\\\n\\\\n\\\\n\",\"color\":\"reset\"},{\"text\":\"Enjoy! \",\"bold\":true,\"color\":\"gold\"},{\"text\":\"\\\\n\\\\n\\\\n\\\\n\\\\n\",\"color\":\"reset\"},{\"text\":\"Mod Creator:\",\"bold\":true},{\"text\":\"\\\\nLiamMWB\",\"color\":\"reset\"}]'],title:\"The Don't Explode Manual\",author:LiamMWB,display:{Lore:[\"g\"]}}", server.getCommandSource());

            StartModCommand.StopMod();
        }
        catch (CommandSyntaxException i)
        {
            i.printStackTrace();
        }
    }



}
