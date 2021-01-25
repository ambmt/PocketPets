package me.ambmt.pets.models;

public enum PetRarity {
	COMMON("common"),
	UNCOMMON("uncommon"),
	RARE("rare"),
	EPIC("epic"),
	LEGENDARY("legendary"),
	MYTHIC("mythic");
	
	private String rarity;
	
	private PetRarity(String rarity) {
		this.rarity = rarity;
	}
	
	public String getRarity() {
		return rarity;
	}
}
