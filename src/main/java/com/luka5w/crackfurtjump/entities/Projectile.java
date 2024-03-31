package com.luka5w.crackfurtjump.entities;

import com.luka5w.crackfurtjump.data.Style;
import com.luka5w.crackfurtjump.math.Vertex;
import com.luka5w.crackfurtjump.objects.Obj;
import com.luka5w.crackfurtjump.util.GameEndedException;
import com.luka5w.swinggame.obj.GameObj;
import com.luka5w.util.VoidConsumer;
import java.awt.Graphics2D;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public class Projectile extends Obj {

  private static final double SPEED = 5;

  private final GameObj dst;
  @Nullable
  private final VoidConsumer callback;

  /**
   * Creates a new game object with the passed attributes.
   *
   * @param src
   * @param dst
   * @param callback
   */
  public Projectile(GameObj src, GameObj dst, @Nullable VoidConsumer callback) {
    super(src.getPos().clone(), 10, 10);
    this.dst = dst;
    this.callback = callback;
  }

  @Override
  public boolean tick(List<Integer> pressedKeys) throws GameEndedException {
    boolean changed = super.tick(pressedKeys);
    this.pos.add(Vertex.velocityForPath(this.pos, this.dst.getPos()).mult(SPEED));
    return changed;
  }

  @Override
  public void paintTo(Graphics2D g) {
    g.setColor(Style.COLOR_PROJECTILE);
    g.fillOval((int) this.pos.getX(), (int) this.pos.getY(), (int) this.getWidth(),
        (int) this.getHeight());
    // draw hit-boxes
    super.paintTo(g);
  }

  /**
   * Returns whether the projectile has reached its target and kills it if it is a living entity.
   *
   * @return whether the projectile has reached its target
   */
  public boolean hasReachedDst() {
    if (this.touches(this.dst)) {
      if (this.dst instanceof Enemy e) {
        e.decreaseHearts(true);
      } else if (this.dst instanceof LivingEntity e) {
        e.decreaseHearts();
      }
      if (callback != null) {
        callback.apply();
      }
      return true;
    }
    return false;
  }

}
