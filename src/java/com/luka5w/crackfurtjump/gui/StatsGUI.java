package com.luka5w.crackfurtjump.gui;

import com.luka5w.crackfurtjump.CrackfurtJump;
import com.luka5w.crackfurtjump.data.Style;
import com.luka5w.crackfurtjump.math.Vertex;
import com.luka5w.swinggame.gui.Padding;
import com.luka5w.swinggame.gui.SwingUtils;
import com.luka5w.swinggame.obj.GUIObj;
import java.awt.Graphics2D;

/**
 * The GUI which displays the players' height.
 */
public class StatsGUI extends GUIObj {

  /**
   * The players' height.
   */
  private long height;
  private long kills;

  // pos =

  /**
   * Creates a new GUI object (There should be only one).
   *
   * @param pos The upper left corner of the GUI
   */
  public StatsGUI(Vertex pos) {
    super(pos, CrackfurtJump.WIDTH - 10, 0);
    this.height = 0;
    this.kills = 0;
  }

  /**
   * Sets the players' height and flags the screen as changed.
   *
   * @param height The new height to display
   */
  public void set(long height, long kills) {
    this.height = height;
    this.kills = kills;
    this.setScreenChanged();
  }

  @Override
  public void paintTo(Graphics2D g) {
    super.paintTo(g);
    g.setFont(Style.TEXT_FONT);
    SwingUtils.drawString(g, this.height + "m", this.pos,
        SwingUtils.ALIGN_L | SwingUtils.ALIGN_T, Style.BG_COLOR, Style.TEXT_COLOR,
        new Padding(5));
    SwingUtils.drawString(g, this.kills + "x", (int) (this.pos.getX() + this.getWidth()),
        (int) this.pos.getY(), SwingUtils.ALIGN_R | SwingUtils.ALIGN_T, Style.BG_COLOR,
        Style.TEXT_COLOR, new Padding(5));
  }

  /**
   * Returns the players' height (<b>Not</b> this objects' height!).
   *
   * @return The players' height
   */
  public long height() {
    return this.height;
  }
}
