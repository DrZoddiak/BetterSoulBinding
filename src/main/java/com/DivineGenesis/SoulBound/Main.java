package com.DivineGenesis.SoulBound;

import java.io.File;

import com.google.inject.Inject;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import com.DivineGenesis.SoulBound.EventListeners.EventListener;
import static com.DivineGenesis.SoulBound.Reference.*;


@Plugin(name = NAME, id = ID, version = VERSION, description = DESC, authors = AUTHORS)
public class Main {
	private static SBConfig config;

    @Inject
    public Main(Logger logger, @DefaultConfig(sharedRoot = false) File defaultCfg,
    		@DefaultConfig(sharedRoot = false) ConfigurationLoader<CommentedConfigurationNode> cfgMgr) {
    	config = new SBConfig(logger, defaultCfg, cfgMgr);
    }
	
    @Listener
    public void onPreInit(GamePreInitializationEvent event) 
    {
    	config.configCheck();
    }
    
    @Listener
    public void onReload(GameReloadEvent event) {
        config.configCheck();
    }

    @Listener
    public void onInit(GameInitializationEvent event) {
    	config.getLogger().info("Registering Events & Commands...");
        Sponge.getEventManager().registerListeners(this, new EventListener());
        Sponge.getCommandManager().register(this, new CmdLoader().sb, "sb");
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
    	config.getLogger().info("Hello world!");
    }
}