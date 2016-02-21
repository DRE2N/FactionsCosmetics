package io.github.dre2n.factionscosmetics.listener;

import io.github.dre2n.factionscosmetics.FactionsCosmetics;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionsListener implements Listener {
	
	protected static FactionsCosmetics plugin = FactionsCosmetics.getInstance();
	
	@EventHandler
	public void onReload() {
		plugin.loadFCConfig(plugin.getFCConfig().getFile());
	}
	
}
