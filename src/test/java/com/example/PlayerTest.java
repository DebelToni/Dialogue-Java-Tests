package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
		Exception ex = assertThrows(IllegalArgumentException.class, () -> {
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
		Exception ex = assertThrows(IllegalArgumentException.class, () -> {
			p.removeItem("sword");
		});
		assertTrue(ex.getMessage().contains("Item not found: sword"));
	}
}
