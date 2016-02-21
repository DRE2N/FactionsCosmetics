package io.github.dre2n.factionscosmetics.listener;

import io.github.dre2n.factionscosmetics.FactionsCosmetics;
import io.github.dre2n.factionsone.api.event.FactionsReloadEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionsListener implements Listener {
	
	protected static FactionsCosmetics plugin = FactionsCosmetics.getInstance();
	
	@EventHandler
	public void onReload(FactionsReloadEvent event) {
		plugin.loadFCConfig(plugin.getFCConfig().getFile());
	}
	
}
