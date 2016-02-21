package io.github.dre2n.factionscosmetics.config;

import java.io.File;

import io.github.dre2n.commons.config.BRConfig;
import io.github.dre2n.commons.util.EnumUtil;
import io.github.dre2n.factionscosmetics.territorymessage.TerritoryMessageType;
import io.github.dre2n.factionsone.api.Placeholders;

public class FCConfig extends BRConfig {
	
	private TerritoryMessageType territoryMessageType = TerritoryMessageType.DISABLED;
	private String territoryMessage = Placeholders.RELATION_COLOR.toString() + Placeholders.FACTION_TAG.toString();
	private String territorySubMessage = "&eLeaving " + Placeholders.RELATION_COLOR.toString() + Placeholders.FACTION_TAG.toString();
	private int territoryMessageFadeIn = 20;
	private int territoryMessageShow = 60;
	private int territoryMessageFadeOut = 20;
	
	public FCConfig(File file) {
		super(file);
		
		if (config.contains("territoryMessage.type")) {
			if (EnumUtil.isValidEnum(TerritoryMessageType.class, config.getString("territoryMessage.type"))) {
				territoryMessageType = TerritoryMessageType.valueOf(config.getString("territoryMessage.type"));
			}
		}
		
		if (config.contains("territoryMessage.message")) {
			territoryMessage = config.getString("territoryMessage.message");
		}
		
		if (config.contains("territoryMessage.subMessage")) {
			territorySubMessage = config.getString("territoryMessage.subMessage");
		}
		
		if (territoryMessageType == TerritoryMessageType.TITLE || territoryMessageType == TerritoryMessageType.SUBTITLE) {
			if (config.contains("territoryMessage.fadeIn")) {
				territoryMessageFadeIn = (int) (config.getDouble("territoryMessage.fadeIn") * 20);
			}
			
			if (config.contains("territoryMessage.show")) {
				territoryMessageShow = (int) (config.getDouble("territoryMessage.show") * 20);
			}
			
			if (config.contains("territoryMessage.fadeOut")) {
				territoryMessageFadeOut = (int) (config.getDouble("territoryMessage.fadeOut") * 20);
			}
		}
	}
	
	/**
	 * @return the territory message type
	 */
	public TerritoryMessageType getTerritoryMessageType() {
		return territoryMessageType;
	}
	
	/**
	 * @return the territory message
	 */
	public String getTerritoryMessage() {
		return territoryMessage;
	}
	
	/**
	 * @return the second line of the territory message
	 */
	public String getTerritorySubMessage() {
		return territorySubMessage;
	}
	
	/**
	 * @return the time in ticks it takes for the territory message to fade in
	 */
	public int getTerritoryMessageFadeIn() {
		return territoryMessageFadeIn;
	}
	
	/**
	 * @return the time in ticks the territory message stays
	 */
	public int getTerritoryMessageShow() {
		return territoryMessageShow;
	}
	
	/**
	 * @return the time in ticks it takes for the territory message to fade out
	 */
	public int getTerritoryMessageFadeOut() {
		return territoryMessageFadeOut;
	}
	
}
