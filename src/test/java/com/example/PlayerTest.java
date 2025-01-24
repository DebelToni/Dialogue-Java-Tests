package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class PlayerTest {
	@Test
	public void testPlayerConstructorValid() {
		List<String> inventory = new ArrayList<>();
		inventory.add("sword");
		Player p = new Player(100, 10, 5, 50, inventory);

		assertEquals(100, p.getHP());
		assertEquals(10, p.getSTR());
		assertEquals(5, p.getCHA());
		assertEquals(50, p.getGOLD());
		assertEquals(1, p.getInventory().size());
	}

	@Test
	public void testPlayerConstructorInvalidHP() {
		List<String> inv = new ArrayList<>();
		Exception ex = assertThrows(
				IllegalArgumentException.class,
				() -> {
					new Player(-10, 10, 5, 50, inv);
				});
		assertTrue(ex.getMessage().contains("HP cannot be negative"));
	}

	@Test
	public void testAddItem() {
		Player p = new Player(100, 10, 5, 50, new ArrayList<>());
		p.addItem("potion");
		assertTrue(p.getInventory().contains("potion"));
	}

	@Test
	public void testRemoveItemSuccess() throws Exception {
		ArrayList<String> items = new ArrayList<>();
		items.add("potion");
		Player p = new Player(100, 10, 5, 50, items);
		p.removeItem("potion");
		assertFalse(p.getInventory().contains("potion"));
	}

	@Test
	public void testRemoveItemFail() {
		Player p = new Player(100, 10, 5, 50, new ArrayList<>());
		Exception ex = assertThrows(
				IllegalArgumentException.class,
				() -> {
					p.removeItem("sword");
				});
		assertTrue(ex.getMessage().contains("Item not found: sword"));
	}

	@Test
	public void testSetHP() {
		Player p = new Player(100, 10, 5, 50, new ArrayList<>());
		p.setHP(50);
		assertEquals(50, p.getHP());
	}

	@Test
	public void testSetSTR() {
		Player p = new Player(100, 10, 5, 50, new ArrayList<>());
		p.setSTR(20);
		assertEquals(20, p.getSTR());
	}

	@Test
	public void testSetCHA() {
		Player p = new Player(100, 10, 5, 50, new ArrayList<>());
		p.setCHA(10);
		assertEquals(10, p.getCHA());
	}

	@Test
	public void testSetGOLD() {
		Player p = new Player(100, 10, 5, 50, new ArrayList<>());
		p.setGOLD(100);
		assertEquals(100, p.getGOLD());
	}

	@Test
	public void testGetInventory() {
		Player p = new Player(100, 10, 5, 50, new ArrayList<>());
		assertEquals(0, p.getInventory().size());
	}

	@Test
	public void testPlayerErrorSTR() {
		List<String> inventory = new ArrayList<>();
		assertThrows(
				IllegalArgumentException.class,
				() -> {
					new Player(100, -10, 5, 50, inventory);
				});
	}

	@Test
	public void testPlayerErrorCHA() {
		List<String> inventory = new ArrayList<>();
		assertThrows(
				IllegalArgumentException.class,
				() -> {
					new Player(100, 10, -5, 50, inventory);
				});
	}

	@Test
	public void testPlayerErrorGOLD() {
		List<String> inventory = new ArrayList<>();
		assertThrows(
				IllegalArgumentException.class,
				() -> {
					new Player(100, 10, 5, -50, inventory);
				});
	}

	@Test
	public void testPlayerErrorInventory() {
		List<String> inventory = new ArrayList<>();
		assertThrows(
				IllegalArgumentException.class,
				() -> {
					new Player(100, 10, 5, 50, null);
				});
	}

	@Test
	public void testPlayerErrorHP() {
		List<String> inventory = new ArrayList<>();
		assertThrows(
				IllegalArgumentException.class,
				() -> {
					new Player(-100, 10, 5, 50, inventory);
				});
	}
}
