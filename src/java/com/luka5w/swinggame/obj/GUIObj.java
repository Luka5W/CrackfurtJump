package com.luka5w.swinggame.obj;

import com.luka5w.crackfurtjump.math.Vertex;
import com.luka5w.crackfurtjump.util.GameEndedException;
import java.awt.Graphics2D;
import java.util.List;

/**
 * A simple implementation of a GUI game object.
 */
public class GUIObj extends GameObj {

  private boolean screenChanged;


  /**
   * Creates a new GUI game object with the passed attributes.
   * @param pos
   * @param width
   * @param height
   */
  public GUIObj(Vertex pos, double width, double height) {
    super(pos, width, height);
    this.screenChanged = true;
  }

  /**
   * Flags the screen as changed (the GUI was updated).
   */
  public void setScreenChanged() {
    this.screenChanged = true;
  }

  @Override
  public void paintTo(Graphics2D g) {
    this.screenChanged = false;
  }

  @Override
  public boolean tick(List<Integer> pressedKeys) throws GameEndedException {
    return super.tick(pressedKeys) || this.screenChanged;
  }
}
