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
package io.github.dre2n.factionscosmetics.command;

import com.massivecraft.factions.cmd.FCommand;
import io.github.dre2n.factionscosmetics.FactionsCosmetics;
import io.github.dre2n.factionscosmetics.player.FCPlayer;
import io.github.dre2n.factionscosmetics.scoreboards.FScoreboard;

/**
 * @author Daniel Saukel
 */
public class ScoreboardCommand extends FCommand {

    public ScoreboardCommand() {
        aliases.add("scoreboard");
        aliases.add("sb");
        permission = "factionscosmetics.scoreboard";
        senderMustBePlayer = true;
    }

    @Override
    public void perform() {
        FCPlayer fcPlayer = FactionsCosmetics.getInstance().getFCPlayers().getFCPlayer(fme);
        FScoreboard board = FScoreboard.get(fme);

        board.setSidebarVisibility(!fcPlayer.getShowScoreboard());
        fcPlayer.setShowScoreboard(!fcPlayer.getShowScoreboard());
    }

}
