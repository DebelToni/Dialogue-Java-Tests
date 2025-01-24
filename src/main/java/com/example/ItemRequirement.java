package com.example;


public class ItemRequirement implements IRequirement {
  private final String requiredItem;

  public ItemRequirement(String requiredItem) {
    if (requiredItem == null) {
      throw new IllegalArgumentException("Item cannot be null");
    }
    this.requiredItem = requiredItem;
  }

  @Override
  public void take(Player player) throws Exception {
    if (!player.getInventory().contains(requiredItem)) {
      throw new IllegalArgumentException("Required item not found: " + requiredItem);
    }
    player.getInventory().remove(requiredItem);
  }
}
