package io.github.dre2n.factionscosmetics.territorymessage;

public enum TerritoryMessageType {
	
	CHAT_CENTERED(false),
	CHAT_CENTERED_AND_SUBMESSAGE(false),
	DISABLED(false),
	SUBTITLE(true),
	TITLE(true),
	TITLE_AND_SUBMESSAGE(true);
	
	private boolean title;
	
	TerritoryMessageType(boolean title) {
		this.title = title;
	}
	
	/**
	 * @return the title
	 */
	public boolean isTitle() {
		return title;
	}
	
}
