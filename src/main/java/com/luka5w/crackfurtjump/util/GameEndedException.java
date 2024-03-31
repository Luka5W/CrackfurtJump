package com.luka5w.crackfurtjump.util;

/**
 * Throw when the game has ended and the {@link com.luka5w.crackfurtjump.gui.ScoreGUI} should be
 * displayed.
 */
public class GameEndedException extends Throwable {

  private final boolean success;

  public GameEndedException(boolean success) {
    this.success = success;
  }

  public boolean isSuccess() {
    return success;
  }
}
