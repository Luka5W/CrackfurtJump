package com.luka5w.crackfurtjump.data;

import java.awt.event.KeyEvent;

/**
 * Constants class containing key bindings.
 */
public class KeyBindings {

  /**
   * <b>Requires cheats enabled</b><br/>
   * When holding this key, an item will be summoned at the next opportunity.
   */
  public static final int CHEAT_SUMMON_ITEM = KeyEvent.VK_I;

  /**
   * <b>Requires cheats enabled</b><br/>
   * When holding this key, an enemy will be summoned at the next opportunity.
   */
  public static final int CHEAT_SUMMON_ENEMY = KeyEvent.VK_E;

  /**
   * <b>Player Controls</b><br/>
   * Moves the player to the left.
   */
  public static final int CTRL_MOVE_LEFT = KeyEvent.VK_LEFT;

  /**
   * <b>Player Controls</b><br/>
   * Moves the player to the right.
   */
  public static final int CTRL_MOVE_RIGHT = KeyEvent.VK_RIGHT;

  /**
   * <b>Player Controls</b><br/>
   * Activates (consumes) a random item in the players inventory.
   */
  public static final int CTRL_ACTION = KeyEvent.VK_SPACE;
}
