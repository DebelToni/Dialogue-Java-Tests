package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

public class AppTest {

	@Test
	public void testPlayGameThorwsTree() {
		App game = new App();
		DialogueTree tree = game.buildATree();
		Player player = new Player(100, 10, 5, 100, new ArrayList<String>());
		assertThrows(IllegalArgumentException.class, () -> game.playGame(player, null));
	}

	@Test
	public void testPlayGameThorwsPlayer() {
		App game = new App();
		DialogueTree tree = game.buildATree();
		Player player = new Player(100, 10, 5, 100, new ArrayList<String>());
		assertThrows(IllegalArgumentException.class, () -> game.playGame(null, tree));
	}
}
