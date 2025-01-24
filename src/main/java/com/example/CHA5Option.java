package com.example;

public class CHA5Option implements IOptional {
	@Override
	public boolean test(Player player) {
		if (player.getCHA() >= 5) {
			return true;
		}
		return false;
	}
}
