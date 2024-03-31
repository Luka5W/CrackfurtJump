package com.luka5w.swinggame.obj;

import com.luka5w.crackfurtjump.math.Vertex;
import com.luka5w.crackfurtjump.util.GameEndedException;
import java.util.List;

/**
 * An abstract implementation of a ticking game object.
 */
public abstract class GameObj implements ITickingGameObj {

  protected Vertex pos;
  private double width;
  private double height;

  /**
   * Creates a new game object with the passed attributes.
   *
   * @param pos
   * @param width
   * @param height
   */
  public GameObj(Vertex pos, double width, double height) {
    this.pos = pos;
    this.width = width;
    this.height = height;
  }

  @Override
  public Vertex getPos() {
    return this.pos;
  }

  @Override
  public double getWidth() {
    return this.width;
  }

  @Override
  public double getHeight() {
    return this.height;
  }

  @Override
  public boolean tick(List<Integer> pressedKeys) throws GameEndedException {
    return false;
  }

  @Override
  public String toString() {
    return getClass().getName() + "[pos=" + this.pos.toShortString() + "]";
  }
}
