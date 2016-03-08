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
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.P;
import io.github.dre2n.factionscosmetics.FactionsCosmetics;
import io.github.dre2n.factionsone.api.Placeholders;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * @author Trent Hensler, Daniel Saukel
 */
public class FTeamWrapper {

    protected static FactionsCosmetics plugin = FactionsCosmetics.getInstance();

    private static final Map<Faction, FTeamWrapper> wrappers = new HashMap<>();
    private static final List<FScoreboard> tracking = new ArrayList<>();
    private static int factionTeamPtr;
    private static final Set<Faction> updating = new HashSet<>();

    private final Map<FScoreboard, Team> teams = new HashMap<>();
    private final String teamName;
    private final Faction faction;
    private final Set<OfflinePlayer> members = new HashSet<>();

    public static void applyUpdatesLater(final Faction faction) {
        if (!FScoreboard.isSupportedByServer()) {
            return;
        }

        if (faction.isNone()) {
            return;
        }

        if (!plugin.getFCConfig().getDefaultScoreboardPrefixes()) {
            return;
        }

        if (updating.add(faction)) {
            Bukkit.getScheduler().runTask(P.p, new Runnable() {
                @Override
                public void run() {
                    updating.remove(faction);
                    applyUpdates(faction);
                }
            });
        }
    }

    public static void applyUpdates(Faction faction) {
        if (!FScoreboard.isSupportedByServer()) {
            return;
        }

        if (faction.isNone()) {
            return;
        }

        if (!plugin.getFCConfig().getDefaultScoreboardPrefixes()) {
            return;
        }

        if (updating.contains(faction)) {
            // Faction will be updated soon.
            return;
        }

        FTeamWrapper wrapper = wrappers.get(faction);
        Set<FPlayer> factionMembers = faction.getFPlayers();

        if (wrapper != null && Factions.i.get(faction.getId()) == null) {
            // Faction was disbanded
            wrapper.unregister();
            wrappers.remove(faction);
            return;
        }

        if (wrapper == null) {
            wrapper = new FTeamWrapper(faction);
            wrappers.put(faction, wrapper);
        }

        for (OfflinePlayer player : wrapper.getPlayers()) {
            if (!player.isOnline() || !factionMembers.contains(FPlayers.i.get(player))) {
                // Player is offline or no longer in faction
                wrapper.removePlayer(player);
            }
        }

        for (FPlayer fmember : factionMembers) {
            if (!fmember.isOnline()) {
                continue;
            }

            // Scoreboard might not have player; add him/her
            wrapper.addPlayer(fmember.getPlayer());
        }

        wrapper.updatePrefixes();
    }

    public static void updatePrefixes(Faction faction) {
        if (!FScoreboard.isSupportedByServer()) {
            return;
        }

        if (!wrappers.containsKey(faction)) {
            applyUpdates(faction);
        } else {
            wrappers.get(faction).updatePrefixes();
        }
    }

    protected static void track(FScoreboard fboard) {
        if (!FScoreboard.isSupportedByServer()) {
            return;
        }
        tracking.add(fboard);
        for (FTeamWrapper wrapper : wrappers.values()) {
            wrapper.add(fboard);
        }
    }

    protected static void untrack(FScoreboard fboard) {
        if (!FScoreboard.isSupportedByServer()) {
            return;
        }
        tracking.remove(fboard);
        for (FTeamWrapper wrapper : wrappers.values()) {
            wrapper.remove(fboard);
        }
    }

    private FTeamWrapper(Faction faction) {
        this.teamName = "faction_" + (factionTeamPtr++);
        this.faction = faction;

        for (FScoreboard fboard : tracking) {
            add(fboard);
        }
    }

    private void add(FScoreboard fboard) {
        Scoreboard board = fboard.getScoreboard();
        Team team = board.registerNewTeam(teamName);
        teams.put(fboard, team);

        for (OfflinePlayer player : getPlayers()) {
            team.addPlayer(player);
        }

        updatePrefix(fboard);
    }

    private void remove(FScoreboard fboard) {
        teams.remove(fboard).unregister();
    }

    private void updatePrefixes() {
        if (plugin.getFCConfig().getDefaultScoreboardPrefixes()) {
            for (FScoreboard fboard : teams.keySet()) {
                updatePrefix(fboard);
            }
        }
    }

    private void updatePrefix(FScoreboard fboard) {
        if (plugin.getFCConfig().getDefaultScoreboardPrefixes()) {
            FPlayer fplayer = fboard.getFPlayer();
            Team team = teams.get(fboard);

            String prefix = plugin.getFCConfig().getNametagPrefix();
            prefix = Placeholders.replaceRelationPlaceholders(prefix, faction, fplayer);
            prefix = Placeholders.replaceFactionPlaceholders(prefix, faction);
            // Avoid client bug
            if (prefix.length() > 16) {
                prefix = prefix.substring(0, 15);
            }

            if (team.getPrefix() == null || !team.getPrefix().equals(prefix)) {
                team.setPrefix(prefix);
            }
        }
    }

    private void addPlayer(OfflinePlayer player) {
        if (members.add(player)) {
            for (Team team : teams.values()) {
                team.addPlayer(player);
            }
        }
    }

    private void removePlayer(OfflinePlayer player) {
        if (members.remove(player)) {
            for (Team team : teams.values()) {
                team.removePlayer(player);
            }
        }
    }

    private Set<OfflinePlayer> getPlayers() {
        return new HashSet<>(this.members);
    }

    private void unregister() {
        for (Team team : teams.values()) {
            team.unregister();
        }
        teams.clear();
    }

}
