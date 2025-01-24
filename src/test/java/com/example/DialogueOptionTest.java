package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DialogueOptionTest {

  @Test
  public void testLineError() {
    Exception e =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              DialogueOption option = new DialogueOption(null, null);
            });
    assertEquals("Line cannot be null", e.getMessage());
  }
  // Виж DialogueOptioon за инфо:
  // @Test
  // public void testStepError() {
  //  Exception e =
  //      assertThrows(
  //          IllegalArgumentException.class,
  //          () -> {
  //            DialogueOption option = new DialogueOption("Hello", null);
  //          });
  //  assertEquals("Step cannot be null", e.getMessage());
  // }
}
