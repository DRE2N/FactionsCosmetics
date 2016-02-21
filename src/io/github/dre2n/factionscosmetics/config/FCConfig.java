package io.github.dre2n.factionscosmetics.config;

import java.io.File;

import io.github.dre2n.commons.config.BRConfig;
import io.github.dre2n.commons.util.EnumUtil;
import io.github.dre2n.factionscosmetics.territorymessage.TerritoryMessageType;
import io.github.dre2n.factionsone.api.Placeholders;

public class FCConfig extends BRConfig {
	
	public static final int CONFIG_VERSION = 1;
	
	private TerritoryMessageType territoryMessageType = TerritoryMessageType.TITLE_AND_SUBMESSAGE;
	private String territoryMessage = Placeholders.RELATION_COLOR.toString() + Placeholders.FACTION_TAG.toString();
	private String territorySubMessage = "&eLeaving " + Placeholders.RELATION_COLOR.toString() + Placeholders.FACTION_TAG.toString();
	private double territoryMessageFadeIn = 1;
	private double territoryMessageShow = 3;
	private double territoryMessageFadeOut = 1;
	
	public FCConfig(File file) {
		super(file, CONFIG_VERSION);
		
		if (initialize) {
			initialize();
		}
		load();
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
		return (int) (territoryMessageFadeIn * 20);
	}
	
	/**
	 * @return the time in ticks the territory message stays
	 */
	public int getTerritoryMessageShow() {
		return (int) (territoryMessageShow * 20);
	}
	
	/**
	 * @return the time in ticks it takes for the territory message to fade out
	 */
	public int getTerritoryMessageFadeOut() {
		return (int) (territoryMessageFadeOut * 20);
	}
	
	@Override
	public void initialize() {
		if ( !config.contains("territoryMessage.type")) {
			config.set("territoryMessage.type", territoryMessageType.toString());
		}
		
		if ( !config.contains("territoryMessage.message")) {
			config.set("territoryMessage.message", territoryMessage);
		}
		
		if ( !config.contains("territoryMessage.subMessage")) {
			config.set("territoryMessage.subMessage", territorySubMessage);
		}
		
		if ( !config.contains("territoryMessage.fadeIn")) {
			config.set("territoryMessage.fadeIn", territoryMessageFadeIn);
		}
		
		if ( !config.contains("territoryMessage.show")) {
			config.set("territoryMessage.show", territoryMessageShow);
		}
		
		if ( !config.contains("territoryMessage.fadeOut")) {
			config.set("territoryMessage.fadeOut", territoryMessageFadeOut);
		}
		
		save();
	}
	
	@Override
	public void load() {
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
		
		if (config.contains("territoryMessage.fadeIn")) {
			territoryMessageFadeIn = config.getDouble("territoryMessage.fadeIn");
		}
		
		if (config.contains("territoryMessage.show")) {
			territoryMessageShow = config.getDouble("territoryMessage.show");
		}
		
		if (config.contains("territoryMessage.fadeOut")) {
			territoryMessageFadeOut = config.getDouble("territoryMessage.fadeOut");
		}
	}
	
}
