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
import com.massivecraft.factions.FPlayers;
import io.github.dre2n.factionscosmetics.FactionsCosmetics;
import org.bukkit.entity.Player;

/**
 * @author Daniel Saukel
 */
public class FCPlayer {

    protected static FCPlayers fcPlayers = FactionsCosmetics.getInstance().getFCPlayers();

    private FPlayer fPlayer;

    private boolean showScoreboard;

    public FCPlayer(Player player) {
        fPlayer = FPlayers.i.get(player);
        fcPlayers.add(this);
    }

    /**
     * @return
     * the FCPlayer as an FPlayer
     */
    public FPlayer getFPlayer() {
        return fPlayer;
    }

    /**
     * @return
     * if the FCPlayer sees a scoreboard
     */
    public boolean getShowScoreboard() {
        return showScoreboard;
    }

    /**
     * @param showScoreboard
     * if the FCPlayer sees a scoreboard
     */
    public void setShowScoreboard(boolean showScoreboard) {
        this.showScoreboard = showScoreboard;
    }

    /**
     * Removes the instance
     */
    public void remove() {
        fcPlayers.remove(this);
    }

}
