package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DialogueStepTest {

  @Test
  public void testLineError() {
    Exception e =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              List<DialogueOption> opt = new ArrayList<DialogueOption>();
              opt.add(new DialogueOption("Hello", null));

              DialogueStep step = new DialogueStep(null, opt, null);
            });
    assertEquals("Line cannot be null", e.getMessage());
  }

  @Test
  public void testStepError() {
    Exception e =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              DialogueStep step = new DialogueStep("Hello", null, null);
            });
    assertEquals("Must have player options or a next step", e.getMessage());
  }
}
