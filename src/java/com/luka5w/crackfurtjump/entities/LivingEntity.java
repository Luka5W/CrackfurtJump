package com.luka5w.crackfurtjump.entities;

import com.luka5w.crackfurtjump.CrackfurtJump;
import com.luka5w.crackfurtjump.data.Style;
import com.luka5w.crackfurtjump.math.Jump;
import com.luka5w.crackfurtjump.math.Vertex;
import com.luka5w.swinggame.obj.GameObj;
import java.awt.Graphics2D;

/**
 * A base for all living entities.
 */
public abstract class LivingEntity extends GameObj {

  /**
   * The hearts of the entities.
   * <p>
   * The entity is considered as dead if there are no hearts left.
   * </p>
   */
  protected int hearts;

  /**
   * The jump object of the entity.
   */
  protected final Jump jump;

  /**
   * Creates a new living entity.
   *
   * @param pos    The position of the entity to be created
   * @param width  The width of the entity
   * @param height The height of the entity
   * @param hearts The initial hearts of the entity
   */
  public LivingEntity(Vertex pos, double width, double height, int hearts) {
    super(pos, width, height);
    this.hearts = hearts;
    this.jump = new Jump();
  }

  /**
   * Returns the remaining hearts of this entity.
   *
   * @return The remaining hearts of this entity
   */
  public int getHearts() {
    return this.hearts;
  }

  /**
   * Sets the remaining hearts of this entity to the passed ones.
   *
   * @param hearts The new remaining hearts of this entity
   * @return true if the hearts were changed
   */
  public boolean setHearts(int hearts) {
    if (this.hearts == hearts) {
      return false;
    }
    this.hearts = hearts;
    return true;
  }

  /**
   * Decreases this entity's hearts by 1.
   */
  protected void decreaseHearts() {
    this.hearts--;
  }

  /**
   * Returns whether this entity is still alive.
   *
   * @return whether this entity's hearts are above 0
   */
  public boolean isAlive() {
    return this.getHearts() > 0;
  }

  @Override
  public void paintTo(Graphics2D g) {
    if (CrackfurtJump.isDevEnabled()) {
      g.setColor(Style.COLOR_HITBOX_LIVING_ENTITY);
      super.paintTo(g);
    }
  }
}
