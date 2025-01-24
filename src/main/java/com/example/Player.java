package com.example;

import java.util.ArrayList;
import java.util.List;

public class Player {

  private float HP;
  private float STR;
  private float CHA;
  private float GOLD;
  // private List<Item> inventory = new ArrayList<Item>();
  private List<String> inventory = new ArrayList<String>();

  public Player(float HP, float STR, float CHA, float GOLD, List<String> inventory) {
     if (HP < 0) {
      throw new IllegalArgumentException("HP cannot be negative");
    }
    if (STR < 0) {
      throw new IllegalArgumentException("STR cannot be negative");
    }
    if (CHA < 0) {
      throw new IllegalArgumentException("CHA cannot be negative");
    }
    if (GOLD < 0) {
      throw new IllegalArgumentException("GOLD cannot be negative");
    }
    if (inventory == null) {
      throw new IllegalArgumentException("Inventory cannot be null");
    }
    this.HP = HP;
    this.STR = STR;
    this.CHA = CHA;
    this.GOLD = GOLD;
    this.inventory = inventory;
  }

  public float getHP() {
    return HP;
  }

  public void setHP(float hP) {
    HP = hP;
  }

  public float getSTR() {
    return STR;
  }

  public void setSTR(float sTR) {
    STR = sTR;
  }

  public float getCHA() {
    return CHA;
  }

  public void setCHA(float cHA) {
    CHA = cHA;
  }

  public float getGOLD() {
    return GOLD;
  }

  public void setGOLD(float gOLD) {
    GOLD = gOLD;
  }

  public List<String> getInventory() {
    return inventory;
  }

  public void addItem(String item) {
    inventory.add(item);
  }

  public void removeItem(String item) throws Exception {
    if (!inventory.contains(item)) {
      throw new IllegalArgumentException("Item not found: " + item);
    }
    inventory.remove(item);
  }
}
