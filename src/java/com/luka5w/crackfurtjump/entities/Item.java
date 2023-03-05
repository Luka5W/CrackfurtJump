package com.luka5w.crackfurtjump.entities;

import com.luka5w.crackfurtjump.CrackfurtJump;
import com.luka5w.crackfurtjump.data.Style;
import com.luka5w.crackfurtjump.math.Random;
import com.luka5w.crackfurtjump.math.Vertex;
import com.luka5w.crackfurtjump.util.GameEndedException;
import com.luka5w.swinggame.gui.SwingUtils;
import com.luka5w.swinggame.obj.GameObj;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.List;

// TODO: 18.02.23 what is object orientation? lol. rewrite!

/**
 * An item which can have different shapes and uses.
 */
public class Item extends GameObj {

  public static final double WIDTH = 30;
  public static final double HEIGHT = 30;

  /**
   * The type of the item.
   */
  public final Type type;

  /**
   * Whether this item is not real (only an item in the inventory, nothing real in the world).
   */
  private final boolean dummy;
  /**
   * The items' durability in game ticks.
   * <p>
   * When the item is equipped, it will be decreased by 1 during each game tick. When the item is
   * consumable (i.e. must be activated) the items' durability should only be decreased when it is
   * activated.
   * </p>
   */
  private int durability;
  /**
   * The items' cooldown in game ticks.
   * <p>
   * When the item is consumed (durability = 0 or durability-- for items which can be activated) the
   * cooldown is applied and prevents repeated uses until it is decreased to 0.
   * </p>
   */
  private int cooldown;

  /**
   * Creates a new (real) random item.
   *
   * @param pos The position of the item
   */
  public Item(Vertex pos) {
    this(pos, Type.values()[Random.getNextInt(0, Type.values().length)]);
  }

  /**
   * Creates a new (real) item of a specific type.
   *
   * @param pos  The position of the item
   * @param type The type of the item
   */
  public Item(Vertex pos, Type type) {
    super(pos, WIDTH, HEIGHT);
    this.type = type;
    this.dummy = false;
    this.durability = 0;
    this.cooldown = 0;
  }

  /**
   * Creates a new (dummy) item (used to be displayed in the inventory) which holds the durability
   * and cooldown for equipped/ collected items.
   *
   * @param pos  The position of the item
   * @param item The type of the item
   */
  public Item(Vertex pos, Item item) {
    super(pos, WIDTH, HEIGHT);
    this.type = item.type;
    this.dummy = true;
    this.durability = item.durability;
    this.cooldown = item.cooldown;
  }

  @Override
  public boolean tick(List<Integer> pressedKeys) throws GameEndedException {
    boolean changed = super.tick(pressedKeys);
    if (this.cooldownActive()) {
      this.cooldown--;
    }
    return changed;
  }

  @Override
  public void paintTo(Graphics2D g) {
    double x = this.pos.getX();
    double y = this.pos.getY();
    // the item can be displayed:
    // 1. in the inventory
    // 2. attached at the players back
    // 3. in the world
    switch (this.type) {
      case BAZOOKA:
        // when in inventory, the item can have a cooldown. check if it's active to display the
        // item in a different color.
        g.setColor(this.cooldownActive() ? Style.COLOR_ITEM_COOLDOWN : Style.COLOR_ITEM_BAZOOKA);
        if (!this.dummy && this.areUsagesLeft()) {
          // draw item: 2
          y += (Player.HEIGHT - HEIGHT) / 2;
          switch (Player.getDirection()) {
            case LEFT:
              paintTo_drawBazooka15x30Side(g, x + Player.WIDTH - WIDTH / 4 + 1, y);
              break;
            case RIGHT:
              paintTo_drawBazooka15x30Side(g, x - WIDTH / 2, y);
              break;
            case NONE:
              paintTo_drawBazooka30x30Front(g, x + (Player.WIDTH - WIDTH) / 2, y);
          }
        } else {
          // draw item: 1 || 3
          paintTo_drawBazooka30x30Front(g, x, y);
        }
        break;
      case COIN:
        if (this.dummy || !this.areUsagesLeft()) {
          // draw item: 1 || 3
          // draw coin
          g.setColor(Style.COLOR_ITEM_COIN_PRIMARY);
          g.fillOval((int) this.pos.getX(), (int) this.pos.getY(), (int) this.getWidth(),
              (int) this.getHeight());
          // draw edge
          g.setColor(Style.COLOR_ITEM_COIN_SECONDARY);
          for (int i = 0; i < 5; i++) {
            g.drawOval((int) this.pos.getX() + i, (int) this.pos.getY() + i,
                (int) this.getWidth() - (2 * i), (int) this.getHeight() - (2 * i));
          }
        }
        break;
      case HEROIN:
        if (this.dummy || !this.areUsagesLeft()) {
          paintTo_drawNeedle(g, (int) this.pos.getX(), (int) this.pos.getY(),
              Style.COLOR_ITEM_HEROIN_PRIMARY, Style.COLOR_ITEM_HEROIN_SECONDARY);
        }
        break;
      case JETPACK:
        double arc = 10;
        double width = WIDTH / 2;
        g.setColor(Style.COLOR_ITEM_JETPACK);
        if (!this.dummy && this.areUsagesLeft()) {
          // draw item: 2
          y += Player.HEIGHT / 2 - HEIGHT / 2;
          switch (Player.getDirection()) {
            case LEFT:
              x += Player.WIDTH;
              break;
            case RIGHT:
              x -= width;
              break;
            case NONE:
              x += Player.WIDTH / 2 - width;
              SwingUtils.drawFilledRoundRect(g, x, y, width, HEIGHT, arc);
              break;
          }
          SwingUtils.drawFilledRoundRect(g, x - width, y, width, HEIGHT, arc);
        } else {
          // draw item: 1 or 3
          SwingUtils.drawFilledRoundRect(g, x - width, y, width, HEIGHT, arc);
          SwingUtils.drawFilledRoundRect(g, x, y, width, HEIGHT, arc);
        }
        break;
      case LSD:
        if (this.dummy || !this.areUsagesLeft()) {
          // draw item: 1 || 3
          g.setColor(Style.COLOR_ITEM_LSD_PRIMARY);
          g.fillOval((int) this.getPos().getX(), (int) this.getPos().getY(), (int) this.getWidth(),
              (int) this.getHeight());
          g.setColor(Style.COLOR_ITEM_LSD_SECONDARY);
          g.drawOval((int) this.getPos().getX(), (int) this.getPos().getY(), (int) this.getWidth(),
              (int) this.getHeight());
          g.drawArc((int) (this.getPos().getX() + this.getWidth() / 4D),
              (int) (this.getPos().getY() + (this.getHeight() / 4D) * 2.5D),
              (int) (this.getWidth() / 2D), (int) (this.getHeight() / 4D), -10, -160);
          g.drawOval((int) (this.getPos().getX() + this.getWidth() / 4D),
              (int) (this.getPos().getY() + (this.getHeight() / 4D)), (int) (this.getWidth() / 5D),
              (int) (this.getHeight() / 3D));
          g.drawOval(
              (int) (this.getPos().getX() + this.getWidth() / 4D * 3D - this.getWidth() / 5D),
              (int) (this.getPos().getY() + (this.getHeight() / 4D)),
              (int) (this.getWidth() / 5D), (int) (this.getHeight() / 3D));
        }
        break;
      case SHIELD:
        g.setColor(Style.COLOR_ITEM_SHIELD);
        if (!this.dummy && this.areUsagesLeft()) {
          // draw item: 2
          x -= Player.WIDTH / 4;
          y -= Player.HEIGHT / 4;
          double w = Player.WIDTH * 1.5;
          double h = Player.HEIGHT * 1.5;
          g.draw(new Ellipse2D.Double(x, y, w, h));
          g.draw(new Ellipse2D.Double(x + 1, y + 1, w - 2, h - 2));
        } else {
          // draw item: 1 or 3
          g.draw(new Ellipse2D.Double(x, y, this.getWidth(), this.getHeight()));
          g.draw(new Ellipse2D.Double(x + 1, y + 1, this.getWidth() - 2, this.getHeight() - 2));
        }
        break;
      case WEED:
        if (this.dummy || !this.areUsagesLeft()) {
          paintTo_drawWeed(g, (int) this.pos.getX(), (int) this.pos.getY());
        }
        break;
      default:
        throw new IllegalStateException("Unknown Item");
    }
    // draw hit-boxes
    if (CrackfurtJump.isDevEnabled()) {
      g.setColor(Style.COLOR_HITBOX_ITEM);
      super.paintTo(g);
    }
  }

  /**
   * Draws the bazooka (front view)
   *
   * @param g
   * @param posX The x-coordinate of the upper left corner
   * @param posY The y-coordinate of the upper left corner
   */
  private static void paintTo_drawBazooka30x30Front(Graphics2D g, double posX, double posY) {
    int x = (int) posX;
    int y = (int) posY;
    // @formatter:off
    // inspired by https://static.vecteezy.com/system/resources/previews/000/354/730/original/launcher-vector-icon.jpg
    // draw bazuka-rear
    SwingUtils.drawFilledPoly(g,
        new int[]{x+ 0,x+ 1,x+ 3,x+ 6,x+ 5,x+ 0},
        new int[]{y+24,y+23,y+23,y+26,y+29,y+24});
    // draw bazuka-main
    SwingUtils.drawFilledPoly(g,
        new int[]{x+ 4,x+16,x+15,x+16,x+17,x+20,x+21,x+23,x+23,x+16,x+18,x+18,x+20,x+19,x+18,x+14,x+11,x+11,x+13,x+13,x+12,x+10,x+ 9,x+ 7,x+ 4},
        new int[]{y+22,y+10,y+ 9,y+ 8,y+ 9,y+ 6,y+ 6,y+ 8,y+ 9,y+16,y+18,y+19,y+21,y+22,y+22,y+18,y+21,y+22,y+24,y+25,y+25,y+23,y+23,y+25,y+22});
    // draw bazuka-bullet
    SwingUtils.drawFilledPoly(g,
        new int[]{x+22,x+25,x+29,x+29,x+24,x+22},
        new int[]{ y+5, y+0, y+0, y+4, y+7, y+5});
    // @formatter:on
  }

  /**
   * Draws the bazooka (side view)
   *
   * @param g
   * @param x The x-coordinate of the upper left corner
   * @param y The y-coordinate of the upper left corner
   */
  private static void paintTo_drawBazooka15x30Side(Graphics2D g, double x, double y) {
    SwingUtils.drawFilledRoundRect(g, x, y, WIDTH / 4, HEIGHT, 5);
  }

  /**
   * Draws all sorts of needles.
   *
   * @param g
   * @param x
   * @param y
   * @param liquid
   * @param needle
   */
  private static void paintTo_drawNeedle(Graphics2D g, int x, int y, Color liquid, Color needle) {
    // @formatter:off
    // draw liquid
    g.setColor(liquid);
    SwingUtils.drawFilledPoly(g,
        new int[]{x+ 5, x+ 8, x+12, x+ 9, x+ 5},
        new int[]{y+20, y+17, y+21, y+24, y+20}
    );
    // draw needle itself
    g.setColor(needle);
    SwingUtils.drawFilledPoly(g,
        new int[]{x   ,x   ,x+ 6,x+ 7,x+ 1,x},
        new int[]{y+29,y+28,y+22,y+23,y+29,y+29}
    );
    // draw tank
    SwingUtils.drawPoly(g,
        new int[]{x+ 4,x+19,x+24,x+ 9,x+ 4},
        new int[]{y+20,y+ 5,y+10,y+25,y+20}
    );
    // draw push thing
    SwingUtils.drawFilledPoly(g,
        new int[]{x+11,x+10,x+ 9,x+ 9,x+12,x+13,x+13,x+12,x+22,x+27,x+28,x+28,x+23,x+27,x+28,x+29,x+29,x+26,x+25,x+25,x+26,x+22,x+17,x+16,x+16,x+21,x+11},
        new int[]{y+17,y+16,y+16,y+17,y+20,y+20,y+19,y+18,y+ 8,y+13,y+13,y+12,y+ 7,y+ 3,y+ 4,y+ 4,y+ 3,y   ,y   ,y+ 1,y+ 2,y+ 6,y+ 1,y+ 1,y+ 2,y+ 7,y+17}
    );
    // @formatter:on
  }

  /**
   * Draws a weed leaf.
   *
   * @param g
   * @param x
   * @param y
   */
  private static void paintTo_drawWeed(Graphics2D g, int x, int y) {
    g.setColor(Style.COLOR_ITEM_WEED);
    // @formatter:off
    SwingUtils.drawFilledPoly(g,
        new int[]{x+15,x+16,x+16,x+18,x+18,x+25,x+25,x+23,x+23,x+19,x+19,x+20,x+21,x+23,x+26,x+29,x+27,x+26,x+24,x+21,x+18,x+17,x+19,x+21,x+20,x+18,x+15,x+14,x+11,x+ 9,x+ 8,x+10,x+12,x+11,x+ 8,x+ 5,x+ 3,x+ 2,x   ,x+ 3,x+ 6,x+ 8,x+ 9,x+10,x+10,x+ 6,x+ 6,x+ 4,x+ 4,x+11,x+11,x+13,x+13,x+14,x+14},
        new int[]{y+ 3,y+ 5,y+16,y+16,y+14,y+ 7,y+10,y+12,y+14,y+18,y+19,y+20,y+19,y+18,y+17,y+17,y+19,y+19,y+21,y+22,y+22,y+23,y+24,y+26,y+26,y+25,y+22,y+22,y+25,y+26,y+26,y+24,y+23,y+22,y+22,y+21,y+19,y+19,y+17,y+17,y+18,y+19,y+20,y+19,y+18,y+14,y+12,y+10,y+ 7,y+14,y+16,y+16,y+ 5,y+ 3,y+ 4}
    );
    // @formatter:on
  }

  /**
   * Consumes the item if it can be consumed, decreases the durability, sets the cooldown if
   * applicable.
   *
   * @return Whether the item was used (i.e. the durability was decreased)
   */
  public boolean use() {
    if (this.areUsagesLeft() && !this.cooldownActive()) {
      this.durability--;
      if (this.type.cooldown != 0 && this.areUsagesLeft()) {
        this.cooldown = this.type.cooldown;
      }
      return true;
    }
    return false;
  }

  /**
   * Increments the items' durability by the passed durability.
   *
   * @param durability The durability to add to the items' durability
   */
  public void incrementDurability(int durability) {
    this.durability += durability;
  }

  /**
   * Increments the items' durability by the default durability of this items' type.
   */
  public void incrementDurability() {
    this.incrementDurability(this.type.durability);
  }

  /**
   * Returns this items' current durability.
   *
   * @return This items' current durability
   */
  public int getDurability() {
    return this.durability;
  }

  /**
   * Returns whether this item is still usable (i.e. the durability is above 0).
   *
   * @return Whether this item is still usable (i.e. the durability is above 0)
   */
  public boolean areUsagesLeft() {
    return this.durability > 0;
  }

  /**
   * Returns whether a cooldown is currently active for this item (and above 0).
   *
   * @return Whether a cooldown is currently active for this item (and above 0).
   */
  public boolean cooldownActive() {
    return this.cooldown > 0;
  }

  /**
   * The different item types.
   */
  public enum Type {
    /**
     * The Bazooka; An item which can be activated to instant-kill a monster which is in the
     * screen.
     */
    BAZOOKA(1, DurabilityUnit.USES, (int) CrackfurtJump.TICK_TIME, true),

    /**
     * The Coin; An item which can be collected (Maybe for powerups ? ;)).
     */
    COIN(1, DurabilityUnit.USES, 0, false),

    /**
     * Heroin; Drugs are bad mkay?
     */
    HEROIN((int) (10 * CrackfurtJump.TICK_TIME), DurabilityUnit.TICKS, 0, false),

    /**
     * The Jetpack; An item which boosts the player up a few meters.
     */
    JETPACK((int) (10 * CrackfurtJump.TICK_TIME), DurabilityUnit.TICKS, 0, false),

    /**
     * LSD; Drugs are bad mkay?
     */
    LSD((int) (10 * CrackfurtJump.TICK_TIME), DurabilityUnit.TICKS, 0, false),

    /**
     * The Shield; An item which makes the player immortal.
     */
    SHIELD((int) (10 * CrackfurtJump.TICK_TIME), DurabilityUnit.TICKS, 0, false),

    /**
     * Weed; Drugs are bad mkay?
     */
    WEED((int) (10 * CrackfurtJump.TICK_TIME), DurabilityUnit.TICKS, 0, false);

    /**
     * The initial durability for items of this type.
     */
    public final int durability;

    /**
     * The unit for the durability (game ticks or uses)
     */
    public final DurabilityUnit unit;

    /**
     * The cooldown applied for items of this type after usage.
     */
    public final int cooldown;

    /**
     * Whether an action is required to use items of this type.
     */
    public final boolean actionRequired;

    Type(int durability, DurabilityUnit unit, int cooldown, boolean actionRequired) {
      this.durability = durability;
      this.unit = unit;
      this.cooldown = cooldown;
      this.actionRequired = actionRequired;
    }
  }

  public enum DurabilityUnit {
    TICKS("s"), USES("x");

    private final String str;

    DurabilityUnit(String str) {
      this.str = str;
    }

    public String asString() {
      return this.str;
    }
  }
}
