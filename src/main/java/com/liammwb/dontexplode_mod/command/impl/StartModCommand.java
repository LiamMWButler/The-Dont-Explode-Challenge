package com.liammwb.dontexplode_mod.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.*;

public class StartModCommand
{
    private static int TotalPlayersInGame;

    private static PlayerEntity SoloPlayer;
    private static PlayerEntity ChosenPlayer;
    private static int RandomPlayer;

    public static Timer CountdownTimer_10;
    public static Timer CountdownTimer_30;
    public static Timer CountdownTimer_60;
    public static Timer CountdownTimer_90;

    private static Timer Countdown_PreTimer;


    public static boolean ModActivated_10 = false;
    public static boolean ModActivated_30 = false;
    public static boolean ModActivated_60 = false;
    public static boolean ModActivated_90 = false;

    private static boolean ModStoppedManually = false;


    private static final SuggestionProvider<CommandSource> SUGGEST_TIME = (source, builder) ->
            ISuggestionProvider.suggest(new String[]{"10s", "30s", "60s", "90s"}, builder);



    // ----------------------------------------------------------------------------------------------------------------------------------------- \\



    public static void register(CommandDispatcher<CommandSource> dispatcher)
    {

        dispatcher.register(Commands.literal("start_exploding").then(Commands.argument("time", StringArgumentType.string()).suggests(SUGGEST_TIME).executes(source ->
        {
            ModStoppedManually = false;
            return SortingInput(source.getSource().asPlayer(), source.getSource().getWorld(), StringArgumentType.getString(source, "time"));

        })));


        dispatcher.register(Commands.literal("stop_exploding").executes(source ->
        {
            if(ModActivated_10 || ModActivated_30 || ModActivated_60 || ModActivated_90)
            {
                ModStoppedManually = true;
                return StopMod();
            }
            else
            {
                return ModNotActive(source.getSource().asPlayer());
            }
        }));
    }



// ----------------------------------------------------------------------------------------------------------------------------------------- \\



    private static int SortingInput(PlayerEntity entity, World world, String time)
    {

        switch (time)
        {
            case "10s":
                if (!ModActivated_10)
                {
                    if (ModActivated_30)
                    {
                        CountdownTimer_30.cancel();
                    }
                    else if (ModActivated_60)
                    {
                        CountdownTimer_60.cancel();
                    }
                    else if (ModActivated_90)
                    {
                        CountdownTimer_90.cancel();
                    }

                    ModActivated_10 = true;
                    ModActivated_30 = false;
                    ModActivated_60 = false;
                    ModActivated_90 = false;

                    CountdownTimer_10 = new Timer();
                    Countdown_PreTimer = new Timer();


                    return TriggerStart(entity, world);
                }
                else {
                    return ModActive(entity);
                }

            case "30s":
                if (!ModActivated_30)
                {
                    if (ModActivated_10)
                    {
                        CountdownTimer_10.cancel();
                    }
                    else if (ModActivated_60)
                    {
                        CountdownTimer_60.cancel();
                    }
                    else if (ModActivated_90)
                    {
                        CountdownTimer_90.cancel();
                    }


                    ModActivated_10 = false;
                    ModActivated_30 = true;
                    ModActivated_60 = false;
                    ModActivated_90 = false;

                    CountdownTimer_30 = new Timer();
                    Countdown_PreTimer = new Timer();


                    return TriggerStart(entity, world);
                }
                else {
                    return ModActive(entity);
                }

            case "60s":
                if (!ModActivated_60)
                {
                    if (ModActivated_10)
                    {
                        CountdownTimer_10.cancel();
                    }
                    else if (ModActivated_30)
                    {
                        CountdownTimer_30.cancel();
                    }
                    else if (ModActivated_90)
                    {
                        CountdownTimer_90.cancel();
                    }

                    ModActivated_10 = false;
                    ModActivated_30 = false;
                    ModActivated_60 = true;
                    ModActivated_90 = false;

                    CountdownTimer_60 = new Timer();
                    Countdown_PreTimer = new Timer();

                    return TriggerStart(entity, world);
                }
                else {
                    return ModActive(entity);
                }

            case "90s":
                if (!ModActivated_90)
                {
                    if (ModActivated_10)
                    {
                        CountdownTimer_10.cancel();
                    }
                    else if (ModActivated_30)
                    {
                        CountdownTimer_30.cancel();
                    }
                    else if (ModActivated_60)
                    {
                        CountdownTimer_60.cancel();
                    }

                    ModActivated_10 = false;
                    ModActivated_30 = false;
                    ModActivated_60 = false;
                    ModActivated_90 = true;

                    CountdownTimer_90 = new Timer();
                    Countdown_PreTimer = new Timer();

                    return TriggerStart(entity, world);

                }
                else {
                    return ModActive(entity);
                }
            default:
                return 1;
        }
    }



// ----------------------------------------------------------------------------------------------------------------------------------------- \\



    private static int TriggerStart(PlayerEntity entity, World world)
    {
        MinecraftServer server = Minecraft.getInstance().getIntegratedServer();

        for (int i = 0; i < world.getPlayers().size(); i++)
        {
            if(ModActivated_10)
            {
                try
                {
                    assert server != null;
                    server.getCommandManager().getDispatcher().execute("title @a times 20 40 40", server.getCommandSource());

                    server.getCommandManager().getDispatcher().execute("title @a title {\"text\":\"Don't Explode\", \"bold\":true}", server.getCommandSource());
                    server.getCommandManager().getDispatcher().execute("title @a subtitle {\"text\":\"Activated For Every '10s'\",\"color\":\"gold\"}", server.getCommandSource());
                }
                catch (CommandSyntaxException e)
                {
                    e.printStackTrace();
                }
            }
            else if(ModActivated_30)
            {
                try
                {
                    assert server != null;
                    server.getCommandManager().getDispatcher().execute("title @a times 20 40 40", server.getCommandSource());

                    server.getCommandManager().getDispatcher().execute("title @a title {\"text\":\"Don't Explode\", \"bold\":true}", server.getCommandSource());
                    server.getCommandManager().getDispatcher().execute("title @a subtitle {\"text\":\"Activated For Every '30s'\",\"color\":\"gold\"}", server.getCommandSource());
                }
                catch (CommandSyntaxException e)
                {
                    e.printStackTrace();
                }
            }
            else if(ModActivated_60)
            {
                try
                {
                    assert server != null;
                    server.getCommandManager().getDispatcher().execute("title @a times 20 40 40", server.getCommandSource());

                    server.getCommandManager().getDispatcher().execute("title @a title {\"text\":\"Don't Explode\", \"bold\":true}", server.getCommandSource());
                    server.getCommandManager().getDispatcher().execute("title @a subtitle {\"text\":\"Activated For Every '60s'\",\"color\":\"gold\"}", server.getCommandSource());
                }
                catch (CommandSyntaxException e)
                {
                    e.printStackTrace();
                }
            }
            else if(ModActivated_90)
            {
                try
                {
                    assert server != null;
                    server.getCommandManager().getDispatcher().execute("title @a times 20 40 40", server.getCommandSource());

                    server.getCommandManager().getDispatcher().execute("title @a title {\"text\":\"Don't Explode\", \"bold\":true}", server.getCommandSource());
                    server.getCommandManager().getDispatcher().execute("title @a subtitle {\"text\":\"Activated For Every '90s'\",\"color\":\"gold\"}", server.getCommandSource());
                }
                catch (CommandSyntaxException e)
                {
                    e.printStackTrace();
                }
            }
        }





        Countdown_PreTimer.schedule(new TimerTask()
        {
            int CurrentNum = 3;

            public void run()
            {
                if(CurrentNum == 3)
                {
                    try
                    {
                        assert server != null;
                        server.getCommandManager().getDispatcher().execute("title @a actionbar  {\"text\":\"3\", \"color\":\"dark_green\"}", server.getCommandSource());
                    }
                    catch (CommandSyntaxException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(CurrentNum == 2)
                {
                    try
                    {
                        assert server != null;
                        server.getCommandManager().getDispatcher().execute("title @a actionbar  {\"text\":\"2\", \"color\":\"gold\"}", server.getCommandSource());
                    }
                    catch (CommandSyntaxException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(CurrentNum == 1)
                {
                    try
                    {
                        assert server != null;
                        server.getCommandManager().getDispatcher().execute("title @a actionbar  {\"text\":\"1\", \"color\":\"red\"}", server.getCommandSource());
                    }
                    catch (CommandSyntaxException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Countdown_PreTimer.cancel();
                }

                CurrentNum --;
            }

        }, 700, 1000);//Perform this every Once






        if(ModActivated_10)
        {
            CountdownTimer_10.schedule(new TimerTask()
            {
                public void run()
                {
                    TotalPlayersInGame = world.getPlayers().size();
                    SoloPlayer = world.getPlayers().get(0);

                    if(TotalPlayersInGame == 1)
                    {
                        world.createExplosion(null, SoloPlayer.getPosX(), SoloPlayer.getPosY(), SoloPlayer.getPosZ(), (float) 5,
                                Explosion.Mode.BREAK);
                    }

                    else
                    {
                        RandomPlayer = (int) (Math.random() * TotalPlayersInGame);

                        ChosenPlayer = world.getPlayers().get(RandomPlayer);

                        world.createExplosion(null, ChosenPlayer.getPosX(), ChosenPlayer.getPosY(), ChosenPlayer.getPosZ(), (float) 5,
                                Explosion.Mode.BREAK);
                    }

                }

            }, 3*1000,   10000);//Perform this every 10 seconds with 3 Seconds Dela
        }

        else if(ModActivated_30)
        {
            CountdownTimer_30.schedule(new TimerTask()
            {
                public void run()
                {
                    TotalPlayersInGame = world.getPlayers().size();
                    SoloPlayer = entity;

                    if(TotalPlayersInGame == 1)
                    {
                        world.createExplosion(null, SoloPlayer.getPosX(), SoloPlayer.getPosY(), SoloPlayer.getPosZ(), (float) 5,
                                Explosion.Mode.BREAK);
                    }

                    else
                    {
                        RandomPlayer = (int) (Math.random() * TotalPlayersInGame) + 1;

                        ChosenPlayer = world.getPlayers().get(RandomPlayer);

                        world.createExplosion(null, ChosenPlayer.getPosX(), ChosenPlayer.getPosY(), ChosenPlayer.getPosZ(), (float) 5,
                                Explosion.Mode.BREAK);
                    }

                }

            }, 3*1000, 30000);//Perform this every 10 seconds with 3 Seconds Dela
        }

        else if(ModActivated_60)
        {
            CountdownTimer_60.schedule(new TimerTask()
            {
                public void run()
                {
                    TotalPlayersInGame = world.getPlayers().size();
                    SoloPlayer = entity;

                    if(TotalPlayersInGame == 1)
                    {
                        world.createExplosion(null, SoloPlayer.getPosX(), SoloPlayer.getPosY(), SoloPlayer.getPosZ(), (float) 5,
                                Explosion.Mode.BREAK);
                    }

                    else
                    {
                        RandomPlayer = (int) (Math.random() * TotalPlayersInGame) + 1;

                        ChosenPlayer = world.getPlayers().get(RandomPlayer);

                        world.createExplosion(null, ChosenPlayer.getPosX(), ChosenPlayer.getPosY(), ChosenPlayer.getPosZ(), (float) 5,
                                Explosion.Mode.BREAK);
                    }

                }

            }, 3*1000, 60000);//Perform this every 10 seconds with 3 Seconds Dela
        }

        else if(ModActivated_90)
        {
            CountdownTimer_90.schedule(new TimerTask()
            {
                public void run()
                {
                    TotalPlayersInGame = world.getPlayers().size();
                    SoloPlayer = entity;

                    if(TotalPlayersInGame == 1)
                    {
                        world.createExplosion(null, SoloPlayer.getPosX(), SoloPlayer.getPosY(), SoloPlayer.getPosZ(), (float) 5,
                                Explosion.Mode.BREAK);
                    }

                    else
                    {
                        RandomPlayer = (int) (Math.random() * TotalPlayersInGame) + 1;

                        ChosenPlayer = world.getPlayers().get(RandomPlayer);

                        world.createExplosion(null, ChosenPlayer.getPosX(), ChosenPlayer.getPosY(), ChosenPlayer.getPosZ(), (float) 5,
                                Explosion.Mode.BREAK);
                    }

                }

            }, 3*1000, 90000);//Perform this every 10 seconds with 3 Seconds Dela
        }

        return 1;
    }



// ----------------------------------------------------------------------------------------------------------------------------------------- \\



    private static int ModActive(PlayerEntity entity)
    {
        if(ModActivated_10)
        {
            entity.sendMessage(new TranslationTextComponent( "Exploding is already set to 10s"), entity.getUniqueID());
        }
        else if(ModActivated_30)
        {
            entity.sendMessage(new TranslationTextComponent( "Exploding is already set to 30s"), entity.getUniqueID());
        }
        else if(ModActivated_60)
        {
            entity.sendMessage(new TranslationTextComponent( "Exploding is already set to 60s"), entity.getUniqueID());
        }
        else if(ModActivated_90)
        {
            entity.sendMessage(new TranslationTextComponent( "Exploding is already set to 90s"), entity.getUniqueID());
        }


        return 1;
    }



// ----------------------------------------------------------------------------------------------------------------------------------------- \\



    public static int StopMod()
    {
        MinecraftServer server = Minecraft.getInstance().getIntegratedServer();

        if(ModActivated_10)
        {
            ModActivated_10 = false;
            ModActivated_30 = false;
            ModActivated_60 = false;
            ModActivated_90 = false;

            CountdownTimer_10.cancel();
        }

        else if(ModActivated_30)
        {
            ModActivated_10 = false;
            ModActivated_30 = false;
            ModActivated_60 = false;
            ModActivated_90 = false;

            CountdownTimer_30.cancel();
        }

        else if(ModActivated_60)
        {
            ModActivated_10 = false;
            ModActivated_30 = false;
            ModActivated_60 = false;
            ModActivated_90 = false;

            CountdownTimer_60.cancel();
        }

        else if(ModActivated_90)
        {
            ModActivated_10 = false;
            ModActivated_30 = false;
            ModActivated_60 = false;
            ModActivated_90 = false;

            CountdownTimer_90.cancel();
        }

            if(!ModStoppedManually)
            {
                //Do Nothing (Player Left the World or just Starting)
            }
            else
            {
                try
                {
                    assert server != null;
                    server.getCommandManager().getDispatcher().execute("title @a times 20 40 40", server.getCommandSource());

                    server.getCommandManager().getDispatcher().execute("title @a title {\"text\":\"Don't Explode\", \"bold\":true}", server.getCommandSource());
                    server.getCommandManager().getDispatcher().execute("title @a subtitle {\"text\":\"Deactivated!\",\"color\":\"red\"}", server.getCommandSource());
                }
                catch (CommandSyntaxException e)
                {
                    e.printStackTrace();
                }
            }


        return 1;
    }



// ----------------------------------------------------------------------------------------------------------------------------------------- \\



    private static int ModNotActive(PlayerEntity entity)
    {
        entity.sendMessage(new TranslationTextComponent( "Mod is not Active to Stop"), entity.getUniqueID());

        return 1;
    }




}
