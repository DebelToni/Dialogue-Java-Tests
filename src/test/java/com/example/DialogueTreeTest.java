package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DialogueTreeTest {

  @Test
  public void testRootError() {
      Exception e = assertThrows(IllegalArgumentException.class, () -> {
        DialogueTree tree = new DialogueTree(null);
      });
      assertEquals("Root cannot be null", e.getMessage());
    }

    @Test
    public void testFindByIdSucced() {
      // DialogueTree tree = new DialogueTree();
      // assertEquals(tree.findById(1), );
    }
  }
