package com.example;

public class CHAOption implements IOptional {
	@Override
	public boolean test(Player player) {
		if (player.getCHA() > 10) {
			return true;
		}
		return false;
	}
}
