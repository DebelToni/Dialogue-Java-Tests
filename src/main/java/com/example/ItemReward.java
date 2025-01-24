package com.example;

public class ItemReward implements IReward {
  private String item;

  public ItemReward(String item) {
    this.item = item;
  }

  @Override
  public void reward(Player player) {
    player.addItem(item);
  }
}
