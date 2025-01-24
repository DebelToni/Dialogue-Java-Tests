package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class DialogueFileParserTest {

		@Test
		public void testParseDialogueFile() throws IOException {
				//Paths.get(getClass().getResource("/ExampleTree.txt").toURI())
		DialogueTree tree = DialogueFileParser.parseDialogueFile("src/test/resources/ExampleTree.txt");

			assertNotNull(tree);
			DialogueStep root = tree.getRoot();
			assertNotNull(root);
		}
}
