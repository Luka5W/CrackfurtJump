package com.luka5w.swinggame;

import java.awt.Graphics2D;
import java.util.List;

public interface IGameLogic {

  /**
   * Called for a single game tick (each 13ms).
   *
   * @param pressedKeys A list containing all currently pressed keys
   * @return {@code true} when the display has changed, {@code false} otherwise
   */
  boolean tick(List<Integer> pressedKeys);

  /**
   * Returns the width of the screen in pixels.
   *
   * @return the width of the screen in pixels.
   */
  int getWidth();

  /**
   * Returns the height of the screen in pixels.
   *
   * @return the height of the screen in pixels.
   */
  int getHeight();

  /**
   * Paints all components to the graphics object.
   *
   * @param g The object to paint to
   */
  void paintTo(Graphics2D g);
}
