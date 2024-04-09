package com.luka5w.swinggame.gui;

import java.util.Objects;

public final class Padding {
    private final int top;
    private final int right;
    private final int bottom;
    private final int left;

  /**
   * This implements padding known from CSS.
   *
   * @param top
   * @param right
   * @param bottom
   * @param left
   */
  public Padding(int top, int right, int bottom, int left) {
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
    }

    public int top() {
        return top;
    }

    public int right() {
        return right;
    }

    public int bottom() {
        return bottom;
    }

    public int left() {
        return left;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Padding) obj;
        return this.top == that.top &&
                this.right == that.right &&
                this.bottom == that.bottom &&
                this.left == that.left;
    }

    @Override
    public int hashCode() {
        return Objects.hash(top, right, bottom, left);
    }

    @Override
    public String toString() {
        return "Padding[" +
                "top=" + top + ", " +
                "right=" + right + ", " +
                "bottom=" + bottom + ", " +
                "left=" + left + ']';
    }

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
