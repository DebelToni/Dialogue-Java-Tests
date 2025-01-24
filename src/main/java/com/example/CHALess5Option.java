package com.example;

public class CHALess5Option implements IOptional {
	@Override
	public boolean test(Player player) {
		if (player.getCHA() < 5) {
			return true;
		}
		return false;
	}
}
