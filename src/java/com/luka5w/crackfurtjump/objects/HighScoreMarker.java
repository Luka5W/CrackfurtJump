package com.luka5w.crackfurtjump.objects;

import com.luka5w.crackfurtjump.data.Style;
import com.luka5w.crackfurtjump.math.Vertex;
import com.luka5w.crackfurtjump.util.GameEndedException;
import com.luka5w.swinggame.gui.SwingUtils;
import com.luka5w.swinggame.obj.GameObj;
import java.awt.Graphics2D;
import java.util.List;
import java.util.function.DoubleSupplier;

/**
 * An object which marks a past high score in the world.
 */
public class HighScoreMarker extends GameObj {

  public static final double WIDTH = 50;
  public static final double HEIGHT = 2;

  /**
   * A supplier to retrieve the current scroll speed.
   */
  private final DoubleSupplier calcSpeed;

  /**
   * The text to display.
   */
  private final String label;

  /**
   * Creates a new marker.
   *
   * @param pos The upper left corner of the new marker
   * @param calcSpeed A supplier to retrieve the current scroll speed
   * @param score The score to display
   */
  public HighScoreMarker(Vertex pos, DoubleSupplier calcSpeed, long score) {
    super(pos, WIDTH, HEIGHT);
    this.calcSpeed = calcSpeed;
    this.label = score + "m";
  }

  @Override
  public void paintTo(Graphics2D g) {
    g.setColor(Style.BG_COLOR);
    g.drawLine((int) this.pos.getX(), (int) this.pos.getY(),
        (int) (this.pos.getX() + this.getWidth()),
        (int) this.pos.getY());
    g.setFont(Style.TEXT_FONT_SMALL);
    SwingUtils.drawString(g, this.label, this.pos, SwingUtils.ALIGN_L | SwingUtils.ALIGN_B, Style.BG_COLOR);
  }

  @Override
  public boolean tick(List<Integer> pressedKeys) throws GameEndedException {
    boolean changed = super.tick(pressedKeys);
    double y = this.calcSpeed.getAsDouble();
    if (y != 0) {
      this.pos.add(new Vertex(0, y));
      changed = true;
    }
    return changed;
  }
}
