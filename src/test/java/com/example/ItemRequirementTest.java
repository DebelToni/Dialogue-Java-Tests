package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ItemRequirementTest {

  @Test
  public void testItemRequirementError() {
    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      ItemRequirement itemRequirement = new ItemRequirement(null);
    });
    assertEquals("Item cannot be null", e.getMessage());
  }
}
