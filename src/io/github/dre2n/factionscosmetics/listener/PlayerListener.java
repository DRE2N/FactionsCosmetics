package io.github.dre2n.factionscosmetics.listener;

import io.github.dre2n.commons.util.messageutil.MessageUtil;
import io.github.dre2n.factionscosmetics.FactionsCosmetics;
import io.github.dre2n.factionscosmetics.config.FCConfig;
import io.github.dre2n.factionscosmetics.territorymessage.TerritoryMessageType;
import io.github.dre2n.factionsone.api.Placeholders;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;

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
