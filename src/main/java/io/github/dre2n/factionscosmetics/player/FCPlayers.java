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
package io.github.dre2n.factionscosmetics.player;

import com.massivecraft.factions.FPlayer;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.entity.Player;

/**
 * @author Daniel Saukel
 */
public class FCPlayers {

    private Set<FCPlayer> players = new HashSet<>();

    /**
     * @return
     * a copy of the player set
     */
    public Set<FCPlayer> getFCPlayers() {
        return new HashSet<>(players);
    }

    /**
     * @param player
     * the matching Player
     */
    public FCPlayer getFCPlayer(Player player) {
        for (FCPlayer fcPlayer : players) {
            if (fcPlayer.getFPlayer().getPlayer().equals(player)) {
                return fcPlayer;
            }
        }

        return null;
    }

    /**
     * @param fPlayer
     * the matching FPlayer
     */
    public FCPlayer getFCPlayer(FPlayer fPlayer) {
        for (FCPlayer fcPlayer : players) {
            if (fcPlayer.getFPlayer().equals(fPlayer)) {
                return fcPlayer;
            }
        }

        return null;
    }

    void add(FCPlayer player) {
        players.add(player);
    }

    void remove(FCPlayer player) {
        players.remove(player);
    }

}
