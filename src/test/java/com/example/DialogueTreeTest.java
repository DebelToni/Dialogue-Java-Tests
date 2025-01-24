package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DialogueTreeTest {

	@Test
	public void testRootError() {
		Exception e = assertThrows(
				IllegalArgumentException.class,
				() -> {
					DialogueTree tree = new DialogueTree(null);
				});
		assertEquals("Root cannot be null", e.getMessage());
	}

	@Test
	public void testFindByIdSucced() {
		List<DialogueOption> options = new ArrayList<>();
		options.add(new DialogueOption("option1", null));
		DialogueStep root = new DialogueStep("durvo", options, null);
		DialogueTree tree = new DialogueTree(root);
		assertEquals(root, tree.findById(0));
	}
}
