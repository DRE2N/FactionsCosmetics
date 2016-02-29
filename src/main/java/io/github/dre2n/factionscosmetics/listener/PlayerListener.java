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
import io.github.dre2n.factionscosmetics.territorymessage.TerritoryMessageType;
import io.github.dre2n.factionsone.api.Placeholders;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * @author Daniel Saukel
 */
public class PlayerListener implements Listener {

    protected static FCConfig config = FactionsCosmetics.getInstance().getFCConfig();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (config.getTerritoryMessageType() == TerritoryMessageType.DISABLED) {
            return;
        }

        Player player = event.getPlayer();
        Faction factionStandpoint = FPlayers.i.get(player).getFaction();
        Faction factionFrom = Board.getFactionAt(event.getFrom());
        Faction factionTo = Board.getFactionAt(event.getTo());

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
                MessageUtil.sendScreenMessage(player, "", title, config.getTerritoryMessageFadeIn(), config.getTerritoryMessageShow(), config.getTerritoryMessageFadeOut());
                break;
            case TITLE:
                MessageUtil.sendScreenMessage(player, title, "", config.getTerritoryMessageFadeIn(), config.getTerritoryMessageShow(), config.getTerritoryMessageFadeOut());
                break;
            case TITLE_AND_SUBMESSAGE:
                MessageUtil.sendScreenMessage(player, title, subtitle, config.getTerritoryMessageFadeIn(), config.getTerritoryMessageShow(), config.getTerritoryMessageFadeOut());
                break;
            default:
                break;
        }
    }

}
