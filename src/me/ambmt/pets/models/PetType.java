package me.ambmt.pets.models;

public enum PetType {
	TIGER("tiger"),
	DOLPHIN("dolphin"),
	PANDA("panda"),
	DRAGON("dragon"),
	GOBLIN("goblin");
	
	private String type;
	
	private PetType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
}