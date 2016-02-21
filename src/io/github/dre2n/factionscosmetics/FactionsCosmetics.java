package io.github.dre2n.factionscosmetics;

import java.io.File;

import com.massivecraft.factions.Patch;

import io.github.dre2n.commons.compatibility.Internals;
import io.github.dre2n.commons.javaplugin.BRPlugin;
import io.github.dre2n.commons.javaplugin.BRPluginSettings;
import io.github.dre2n.factionscosmetics.config.FCConfig;
import io.github.dre2n.factionscosmetics.listener.FactionsListener;
import io.github.dre2n.factionscosmetics.listener.PlayerListener;

public class FactionsCosmetics extends BRPlugin {
	
	private static FactionsCosmetics instance;
	
	private FCConfig config;
	
	public FactionsCosmetics() {
		settings = new BRPluginSettings(Internals.INDEPENDENT, false, false, false, false);
	}
	
	@Override
	public void onEnable() {
		try {
			getLogger().info("Successfully hooked into " + Patch.getFullName() + ".");
			
		} catch (Exception exception) {
			getLogger().info("Could not find dependencies!");
		}
		
		super.onEnable();
		
		instance = this;
		
		loadFCConfig(new File(getDataFolder(), "config.yml"));
		
		manager.registerEvents(new PlayerListener(), this);
		manager.registerEvents(new FactionsListener(), this);
	}
	
	/**
	 * @return the plugin instance
	 */
	public static FactionsCosmetics getInstance() {
		return instance;
	}
	
	/**
	 * @return the loaded instance of FCConfig
	 */
	public FCConfig getFCConfig() {
		return config;
	}
	
	/**
	 * load / reload a new instance of FCConfig
	 */
	public void loadFCConfig(File file) {
		config = new FCConfig(file);
	}
	
}
