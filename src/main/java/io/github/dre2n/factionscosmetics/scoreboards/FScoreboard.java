/*
 * Copyright (C) 2014-2016 Trent Hensler
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
package io.github.dre2n.factionscosmetics.scoreboards;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import io.github.dre2n.factionscosmetics.FactionsCosmetics;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

/**
 * @author Trent Hensler, Daniel Saukel
 */
public class FScoreboard {

    protected static FactionsCosmetics plugin = FactionsCosmetics.getInstance();

    private static final Map<FPlayer, FScoreboard> fscoreboards = new HashMap<>();

    private final Scoreboard scoreboard;
    private final FPlayer fplayer;
    private final BufferedObjective bufferedObjective;
    private FSidebarProvider defaultProvider;
    private FSidebarProvider temporaryProvider;
    private boolean removed = false;

    // Glowstone doesn't support scoreboards.
    // All references to this and related workarounds can be safely
    // removed when scoreboards are supported.
    public static boolean isSupportedByServer() {
        return Bukkit.getScoreboardManager() != null;
    }

    public static void init(FPlayer fplayer) {
        FScoreboard fboard = new FScoreboard(fplayer);
        fscoreboards.put(fplayer, fboard);

        if (fplayer.hasFaction()) {
            FTeamWrapper.applyUpdates(fplayer.getFaction());
        }
        FTeamWrapper.track(fboard);
    }

    public static void remove(FPlayer fplayer) {
        FScoreboard fboard = fscoreboards.remove(fplayer);

        if (fboard != null) {
            fboard.removed = true;
            FTeamWrapper.untrack(fboard);
        }
    }

    public static FScoreboard get(FPlayer fplayer) {
        return fscoreboards.get(fplayer);
    }

    public static FScoreboard get(Player player) {
        return fscoreboards.get(FPlayers.i.get(player));
    }

    private FScoreboard(FPlayer fplayer) {
        this.fplayer = fplayer;

        if (isSupportedByServer()) {
            this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            this.bufferedObjective = new BufferedObjective(scoreboard);

            fplayer.getPlayer().setScoreboard(scoreboard);

        } else {
            this.scoreboard = null;
            this.bufferedObjective = null;
        }
    }

    protected FPlayer getFPlayer() {
        return fplayer;
    }

    protected Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void setSidebarVisibility(boolean visible) {
        if (!isSupportedByServer()) {
            return;
        }

        bufferedObjective.setDisplaySlot(visible ? DisplaySlot.SIDEBAR : null);
    }

    public void setDefaultSidebar(final FSidebarProvider provider, int updateInterval) {
        if (!isSupportedByServer()) {
            return;
        }

        defaultProvider = provider;
        if (temporaryProvider == null) {
            // We have no temporary provider; update the BufferedObjective!
            updateObjective();
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (removed || provider != defaultProvider) {
                    cancel();
                    return;
                }

                if (temporaryProvider == null) {
                    updateObjective();
                }
            }
        }.runTaskTimer(plugin, updateInterval, updateInterval);
    }

    public void setTemporarySidebar(final FSidebarProvider provider) {
        if (!isSupportedByServer()) {
            return;
        }

        temporaryProvider = provider;
        updateObjective();

        new BukkitRunnable() {
            @Override
            public void run() {
                if (removed) {
                    return;
                }

                if (temporaryProvider == provider) {
                    temporaryProvider = null;
                    updateObjective();
                }
            }
        }.runTaskLater(plugin, plugin.getFCConfig().getScoreboardExpiration());
    }

    private void updateObjective() {
        FSidebarProvider provider = temporaryProvider != null ? temporaryProvider : defaultProvider;

        if (provider == null) {
            bufferedObjective.hide();

        } else {
            bufferedObjective.setTitle(provider.getTitle(fplayer));
            bufferedObjective.setAllLines(provider.getLines(fplayer));
            bufferedObjective.flip();
        }
    }

}
