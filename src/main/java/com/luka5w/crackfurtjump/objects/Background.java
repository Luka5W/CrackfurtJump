package com.luka5w.crackfurtjump.objects;

import com.luka5w.crackfurtjump.CrackfurtJump;
import com.luka5w.crackfurtjump.data.Style;
import com.luka5w.crackfurtjump.math.Vertex;
import com.luka5w.crackfurtjump.util.GameEndedException;
import com.luka5w.swinggame.obj.GameObj;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

/**
 * The background of the game.
 */
public class Background extends GameObj {

  private int colorTick;
  private Color color;

  /**
   * Creates a new game object for the background (there should be only one)
   * @param pos The position of the background (usually 0|0)
   */
  public Background(Vertex pos) {
    super(pos, CrackfurtJump.WIDTH, CrackfurtJump.HEIGHT);
    this.color = Style.COLOR_BACKGROUND;
    this.colorTick = -1;
  }

  @Override
  public void paintTo(Graphics2D g) {
    // TODO: 27.01.23 add background
    g.setColor(this.color);
    g.fillRect((int) this.pos.getX(), (int) this.pos.getY(), (int) this.getWidth(),
        (int) this.getHeight());
  }

  @Override
  public boolean tick(List<Integer> pressedKeys) throws GameEndedException {
    // TODO: 18.02.23 scrolling background
    if (this.colorTick != -1) {
      if (this.colorTick++ >= CrackfurtJump.TICK_TIME) {
        this.colorTick = 0;
        this.color = Style.getRandomBackgroundColor();
      }
    }
    return super.tick(pressedKeys);
  }

  public void setRandomBackground(boolean enable) {
    if (enable && this.colorTick == -1) {
      this.colorTick = 0;
    } else if (!enable && this.colorTick != -1) {
      this.color = Style.COLOR_BACKGROUND;
      this.colorTick = -1;
    }
  }
}
