/*
 * Copyright (C) 2016 Daniel Saukel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.dre2n.factionscosmetics.listener;

import io.github.dre2n.factionscosmetics.FactionsCosmetics;
import io.github.dre2n.factionsone.api.event.FactionsReloadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Daniel Saukel
 */
public class FactionsListener implements Listener {

    protected static FactionsCosmetics plugin = FactionsCosmetics.getInstance();

    @EventHandler
    public void onReload(FactionsReloadEvent event) {
        plugin.loadFCConfig(plugin.getFCConfig().getFile());
    }

}
