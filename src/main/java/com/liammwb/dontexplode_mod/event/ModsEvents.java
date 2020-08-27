package com.liammwb.dontexplode_mod.event;

import com.liammwb.dontexplode_mod.DontExplode_Mod;
import com.liammwb.dontexplode_mod.command.impl.StartModCommand;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@net.minecraftforge.fml.common.Mod.EventBusSubscriber(modid = DontExplode_Mod.MOD_ID)
public class ModsEvents
{
    @SubscribeEvent
    public static void onServerStarting(final FMLServerStartingEvent event)
    {
        StartModCommand.register(event.getCommandDispatcher());
    }
}
