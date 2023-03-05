package com.luka5w.crackfurtjump.objects;

import com.luka5w.crackfurtjump.CrackfurtJump;
import com.luka5w.crackfurtjump.data.Style;
import com.luka5w.crackfurtjump.entities.Enemy;
import com.luka5w.crackfurtjump.entities.Item;
import com.luka5w.crackfurtjump.math.Random;
import com.luka5w.crackfurtjump.math.Vertex;
import com.luka5w.crackfurtjump.util.EnumSummonObject;
import com.luka5w.crackfurtjump.util.GameEndedException;
import com.luka5w.swinggame.obj.GameObj;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.util.List;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import org.jetbrains.annotations.Nullable;

public class Platform extends Obj {

  public static final double WIDTH = 70;
  public static final double HEIGHT = 15;

  /**
   * The distance between object in the slot and platform.
   */
  private static final int SLOT_DISTANCE_Y = 5;

  /**
   * A supplier to determine if a game object (item or enemy) should be spawned in the platforms'
   * slot.
   */
  private Supplier<EnumSummonObject> shouldSummonObject;

  /**
   * A supplier to retrieve the current scroll speed.
   */
  private final DoubleSupplier calcSpeed;

  private GameObj slot;

  /**
   * Creates a new platform.
   *
   * @param pos                The upper left corner of this object
   * @param calcSpeed          A supplier to retrieve the current scroll speed
   * @param shouldSummonObject A supplier to determine if a game object (item or enemy) should be
   *                           spawned in the platforms' slot
   */
  public Platform(Vertex pos, DoubleSupplier calcSpeed,
      Supplier<EnumSummonObject> shouldSummonObject) {
    super(pos, WIDTH, HEIGHT);
    this.shouldSummonObject = shouldSummonObject;
    this.calcSpeed = calcSpeed;
    this.slot = null;
  }

  @Override
  public boolean tick(List<Integer> pressedKeys) throws GameEndedException {
    boolean changed = super.tick(pressedKeys);
    if (this.isAbove(CrackfurtJump.HEIGHT + (this.slot == null ? HEIGHT :
        HEIGHT + SLOT_DISTANCE_Y + this.slot.getHeight()))) {
      double y = this.calcSpeed.getAsDouble();
      if (y != 0) {
        this.pos.add(new Vertex(0, y));
        if (this.slot != null && (!(this.slot instanceof Enemy enemy) || enemy.isAlive())) {
          this.slot.getPos().add(new Vertex(0, y));
        }
        changed = true;
      }
      if (this.slot != null) {
        changed |= this.slot.tick(pressedKeys);
      }
    } else {
      this.getPos().setX(Random.getNextDouble(5, CrackfurtJump.WIDTH - WIDTH - 5));
      this.getPos().setY(-Random.getNextDouble(HEIGHT, HEIGHT + 20));
      EnumSummonObject sso = this.shouldSummonObject.get();
      if (sso == EnumSummonObject.NONE) {
        this.clearSlot();
      } else {
        double x = this.pos.getX() + WIDTH / 2;
        double y = this.pos.getY() - SLOT_DISTANCE_Y;
        this.slot = sso == EnumSummonObject.ENEMY
            ? new Enemy(new Vertex(x - (Enemy.WIDTH / 2), y - Enemy.HEIGHT), 1)
            : new Item(new Vertex(x - (Item.WIDTH / 2), y - Item.HEIGHT));
      }
      changed = true;
    }
    return changed;
  }

  @Override
  public void paintTo(Graphics2D g) {
    g.setColor(Style.COLOR_PLATFORM);
    Shape shape = new RoundRectangle2D.Double(this.pos.getX(), this.pos.getY(),
        WIDTH, HEIGHT, 15, 15);
    g.draw(shape);
    g.fill(shape);
    if (this.slot != null) {
      this.slot.paintTo(g);
    }
    // draw hit-boxes
    super.paintTo(g);
  }

  /**
   * Returns the platforms' slot.
   *
   * @return The platforms' slot
   */
  @Nullable
  public GameObj getSlot() {
    return this.slot;
  }

  /**
   * Removes the platforms' slot by setting it to {@code null}.
   */
  public void clearSlot() {
    this.slot = null;
  }
}
