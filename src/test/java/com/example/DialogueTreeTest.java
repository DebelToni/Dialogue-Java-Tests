package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DialogueTreeTest {

  @Test
  public void testFindByIdError() {
    assertThrows(IllegalArgumentException.class, () -> {
      DialogueTree tree = new DialogueTree();
      tree.findById(0);
    });
  }
}
