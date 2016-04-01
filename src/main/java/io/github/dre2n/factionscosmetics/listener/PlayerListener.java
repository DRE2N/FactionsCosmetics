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

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import io.github.dre2n.commons.util.messageutil.MessageUtil;
import io.github.dre2n.factionscosmetics.FactionsCosmetics;
import io.github.dre2n.factionscosmetics.config.FCConfig;
import io.github.dre2n.factionscosmetics.player.FCPlayer;
import io.github.dre2n.factionscosmetics.player.FCPlayers;
import io.github.dre2n.factionscosmetics.scoreboards.FScoreboard;
import io.github.dre2n.factionscosmetics.scoreboards.sidebar.FDefaultSidebar;
import io.github.dre2n.factionscosmetics.territorymessage.TerritoryMessageType;
import io.github.dre2n.factionsone.api.Placeholders;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * @author Daniel Saukel
 */
public class PlayerListener implements Listener {

    protected static FactionsCosmetics plugin = FactionsCosmetics.getInstance();
    protected static FCPlayers players = plugin.getFCPlayers();
    protected static FCConfig config = plugin.getFCConfig();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        sendTerritoryMessage(event.getPlayer(), event.getFrom(), event.getTo());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        sendTerritoryMessage(event.getPlayer(), event.getFrom(), event.getTo());
    }

    public void sendTerritoryMessage(Player player, Location from, Location to) {
        if (config.getTerritoryMessageType() == TerritoryMessageType.DISABLED) {
            return;
        }

        Faction factionStandpoint = FPlayers.i.get(player).getFaction();
        Faction factionFrom = Board.getFactionAt(from);
        Faction factionTo = Board.getFactionAt(to);

        if (factionFrom == factionTo) {
            return;
        }

        String title = Placeholders.replaceFactionPlaceholders(config.getTerritoryMessage(), factionTo);
        title = Placeholders.replaceRelationPlaceholders(title, factionStandpoint, factionTo);
        String subtitle = Placeholders.replaceFactionPlaceholders(config.getTerritorySubMessage(), factionFrom);
        subtitle = Placeholders.replaceRelationPlaceholders(subtitle, factionStandpoint, factionFrom);

        switch (config.getTerritoryMessageType()) {
            case CHAT_CENTERED:
                MessageUtil.sendCenteredMessage(player, title);
                break;
            case CHAT_CENTERED_AND_SUBMESSAGE:
                MessageUtil.sendCenteredMessage(player, title);
                MessageUtil.sendCenteredMessage(player, subtitle);
                break;
            case SUBTITLE:
                MessageUtil.sendTitleMessage(player, "", title, config.getTerritoryMessageFadeIn(), config.getTerritoryMessageShow(), config.getTerritoryMessageFadeOut());
                break;
            case TITLE:
                MessageUtil.sendTitleMessage(player, title, "", config.getTerritoryMessageFadeIn(), config.getTerritoryMessageShow(), config.getTerritoryMessageFadeOut());
                break;
            case TITLE_AND_SUBMESSAGE:
                MessageUtil.sendTitleMessage(player, title, subtitle, config.getTerritoryMessageFadeIn(), config.getTerritoryMessageShow(), config.getTerritoryMessageFadeOut());
                break;
            default:
                break;
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        FCPlayer fcPlayer = new FCPlayer(event.getPlayer());

        if (config.isScoreboardEnabledByDefault()) {
            FScoreboard.init(fcPlayer.getFPlayer());
            FScoreboard.get(fcPlayer.getFPlayer()).setDefaultSidebar(new FDefaultSidebar(), config.getScoreboardUpdateInterval());
            FScoreboard.get(fcPlayer.getFPlayer()).setSidebarVisibility(fcPlayer.getShowScoreboard());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        FCPlayer fcPlayer = players.getFCPlayer(event.getPlayer());
        FScoreboard.remove(fcPlayer.getFPlayer());
        fcPlayer.remove();
    }

}
