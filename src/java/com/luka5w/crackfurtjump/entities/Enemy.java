package com.luka5w.crackfurtjump.entities;

import com.luka5w.crackfurtjump.data.Style;
import com.luka5w.crackfurtjump.math.Vertex;
import com.luka5w.crackfurtjump.util.GameEndedException;
import com.luka5w.swinggame.gui.SwingUtils;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

/**
 * A basic enemy.
 */
public class Enemy extends LivingEntity {

  public static final double WIDTH = 35;
  public static final double HEIGHT = 50;
  private final Color color;

  public Enemy(Vertex pos, int hearts) {
    super(pos, WIDTH, HEIGHT, hearts);
    this.color = Style.getRandomEnemyColor();
  }

  public Enemy(Vertex pos, int hearts, Color color) {
    super(pos, WIDTH, HEIGHT, hearts);
    this.color = color;
  }

  @Override
  public void paintTo(Graphics2D g) {
    int x = (int) this.pos.getX();
    int y = (int) this.pos.getY();
    g.setColor(this.color);
    // @formatter:off
    // shape
    SwingUtils.drawFilledPoly(g,
        /* old code, used for width = 50
        new int[]{x+24,x+23,x+22,x+21,x+20,x+17,x+15,x+12,x+12,x+11,x+10,x+ 9,x+ 9,x+10,x+12,x+12,x+ 8,x+ 7,x+ 7,x+ 9,x+10,x+11,x+13,x+15,x+15,x+17,x+32,x+34,x+34,x+36,x+38,x+39,x+40,x+42,x+42,x+41,x+37,x+37,x+39,x+40,x+40,x+39,x+38,x+37,x+37,x+34,x+32,x+29,x+28,x+27,x+26,x+25},
         */
        new int[]{x+17,x+16,x+15,x+14,x+13,x+10,x+ 8,x+ 5,x+ 5,x+ 4,x+ 3,x+ 2,x+ 2,x+ 3,x+ 5,x+ 5,x+ 1,x   ,x   ,x+ 2,x+ 3,x+ 4,x+ 6,x+ 8,x+ 8,x+10,x+25,x+27,x+27,x+29,x+31,x+32,x+33,x+35,x+35,x+34,x+30,x+30,x+32,x+33,x+33,x+32,x+31,x+30,x+30,x+27,x+25,x+22,x+21,x+20,x+19,x+18},
        new int[]{y+ 4,y+ 5,y+ 7,y+ 5,y+ 5,y+ 8,y+12,y+12,y+ 7,y+ 7,y+ 8,y+10,y+13,y+15,y+17,y+18,y+20,y+22,y+28,y+32,y+33,y+33,y+37,y+39,y+43,y+45,y+45,y+43,y+39,y+37,y+33,y+33,y+32,y+28,y+22,y+20,y+18,y+17,y+15,y+13,y+10,y+ 8,y+ 7,y+ 7,y+12,y+12,y+ 8,y+ 5,y+ 5,y+ 7,y+ 5,y+ 4}
    );
    g.setColor(Style.COLOR_ENEMY_EYES);
    // left eye
    SwingUtils.drawFilledPoly(g,
        /* old code, used for width = 50
        new int[]{x+17,x+20,x+21,x+22,x+22,x+17,x+17,x+16,x+16,x+17},
         */
        new int[]{x+10,x+13,x+14,x+15,x+15,x+10,x+10,x+ 9,x+ 9,x+10},
        new int[]{y+14,y+17,y+17,y+18,y+19,y+19,y+18,y+17,y+16,y+15}
    );
    // right eye
    SwingUtils.drawFilledPoly(g,
        /* old code, used for width = 50
        new int[]{x+32,x+29,x+28,x+27,x+27,x+32,x+32,x+33,x+33,x+32},
         */
        new int[]{x+25,x+22,x+21,x+20,x+20,x+25,x+25,x+26,x+26,x+25},
        new int[]{y+14,y+17,y+17,y+18,y+19,y+19,y+18,y+17,y+16,y+15}
    );
    // mouth
    g.setColor(Style.COLOR_ENEMY_MOUTH);
    SwingUtils.drawFilledPoly(g,
        /* old code, used for width = 50
        new int[]{x+13,x+13,x+12,x+12,x+14,x+15,x+17,x+18,x+18,x+20,x+21,x+21,x+28,x+28,x+29,x+31,x+31,x+32,x+34,x+35,x+37,x+37,x+36,x+36,x+34,x+33,x+32,x+32,x+29,x+28,x+28,x+27,x+26,x+23,x+22,x+21,x+21,x+20,x+17,x+17,x+16,x+15,x+13},
         */
        new int[]{x+ 6,x+ 6,x+ 5,x+ 5,x+ 7,x+ 8,x+10,x+11,x+11,x+13,x+14,x+14,x+21,x+21,x+22,x+24,x+24,x+25,x+27,x+28,x+30,x+30,x+29,x+29,x+27,x+26,x+25,x+25,x+22,x+21,x+21,x+20,x+19,x+16,x+15,x+14,x+14,x+13,x+10,x+10,x+ 9,x+ 8,x+ 6},
        new int[]{y+24,y+26,y+27,y+28,y+32,y+33,y+37,y+38,y+37,y+35,y+35,y+39,y+39,y+35,y+35,y+37,y+38,y+37,y+33,y+32,y+28,y+27,y+26,y+24,y+26,y+26,y+27,y+29,y+32,y+32,y+28,y+28,y+27,y+27,y+28,y+28,y+32,y+32,y+29,y+27,y+26,y+26,y+24}
    );
    // @formatter:on
    // draw hit-boxes
    super.paintTo(g);
  }

  @Override
  public boolean tick(List<Integer> pressedKeys) throws GameEndedException {
    boolean changed = super.tick(pressedKeys);
    if (!this.isAlive()) {
      double y = this.jump.move();
      if (y != 0) {
        this.pos.add(new Vertex(0, y));
      }
    }
    return changed;
  }

  /**
   * Decreases the enemies hearts and initiates a die animation (a fall).
   * @param slapstick Whether to jump a bit and then fall ({@code true}) or just simply fall
   *                  ({@code false})
   * @return Whether this method has changed this enemy in any kind.
   */
  public boolean decreaseHearts(boolean slapstick) {
    this.decreaseHearts();
    if (!this.isAlive()) {
      if (slapstick) {
        this.jump.start(-1);
      } else {
        this.jump.fall();
      }
      return true;
    }
    return false;
  }
}
