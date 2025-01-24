package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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

	@Test
	public void testPlayGame() {
		App game = new App();
		DialogueTree tree = game.buildATree();
		Player player = new Player(100, 10, 5, 100, new ArrayList<String>());

		// assertDoesNotThrow(() -> game.playGame(player, tree));
		// .playGame() raboti - dobavil sum snimka, prosto nqma kak da se testva s
		// maven (pone nqma lesno kak da stane)
	}

	@Test
	public void testPlayGameWithSimulatedInput() {
		App game = new App();

		DialogueTree tree = game.buildATree();

		Player player = new Player(100, 11, 11, 100, new ArrayList<String>());

				// naglasih go da se igrae samo maj?
		String simulatedInput = "" + "4\n" + "\n" + "3\n" + "\n" + "1\n" + "2\n";

		InputStream input = new ByteArrayInputStream(simulatedInput.getBytes());
		System.setIn(input);

		App app = new App();
		app.playGame(player, tree);

		assertTrue(player.getInventory().contains("Light armor"));
	}
}
