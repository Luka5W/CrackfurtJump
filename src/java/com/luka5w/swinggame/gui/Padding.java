package com.luka5w.swinggame.gui;

/**
 * This implements padding known from CSS.
 * @param top
 * @param right
 * @param bottom
 * @param left
 */
public record Padding(int top, int right, int bottom, int left) {

  public Padding(int trbl) {
    this(trbl, trbl, trbl, trbl);
  }

  public Padding(int tb, int rl) {
    this(tb, rl, tb, rl);
  }

  public Padding(int t, int rl, int b) {
    this(t, rl, b, rl);
  }
}
