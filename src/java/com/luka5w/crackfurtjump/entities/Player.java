package com.luka5w.crackfurtjump.entities;

import com.luka5w.crackfurtjump.data.KeyBindings;
import com.luka5w.crackfurtjump.data.Style;
import com.luka5w.crackfurtjump.entities.Item.Type;
import com.luka5w.crackfurtjump.math.Vertex;
import com.luka5w.crackfurtjump.objects.Platform;
import com.luka5w.crackfurtjump.util.GameEndedException;
import com.luka5w.util.TriConsumer;
import com.luka5w.swinggame.obj.GameObj;
import com.luka5w.util.VoidConsumer;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * The player object.
 */
public class Player extends LivingEntity {

  public static final double WIDTH = 50;
  public static final double HEIGHT = 50;
  private static Player INSTANCE;

  /**
   * The width of the window.
   */
  private final double winWidth;
  /**
   * The height of the window.
   */
  private final int winHeight;
  /**
   * The half height of the window.
   */
  private final int winHalfHeight;

  /**
   * Consumer to update the GUI which displays the player's current height.
   */
  private final BiConsumer<Double, Long> updateStatsGUI;

  private final TriConsumer<GameObj, GameObj, VoidConsumer> createProjectile;

  /**
   * All platforms.
   */
  private final List<Platform> platforms;

  /**
   * The player's inventory.
   */
  private final Item[] items;

  /**
   * The current players' direction.
   */
  private Direction direction;

  /**
   * The amount of killed enemies.
   */
  private long kills;


  /**
   * Whether the action key was pressed but not yet handled.
   */
  private boolean actionPressed;

  /**
   * Whether an enemy should be killed with the bazooka but is not yet handled.
   */
  private boolean killEnemyWithBazooka;
  private boolean usingJetpack;

  /**
   * Creates a new player
   *
   * @param pos             The players' position
   * @param winWidth        The width of the window
   * @param winHeight       The height of the window
   * @param platforms       All platforms
   * @param updateStatsGUI A Consumer to update the GUI which displays the player's current height
   * @param items           An empty array (the players' inventory)
   */
  public Player(Vertex pos, int winWidth, int winHeight, List<Platform> platforms,
      BiConsumer<Double, Long> updateStatsGUI, TriConsumer<GameObj, GameObj, VoidConsumer> createProjectile,
      Item[] items) {
    super(pos, WIDTH, HEIGHT, 1);
    this.winWidth = winWidth;
    this.winHeight = winHeight;
    this.winHalfHeight = (int) Math.ceil(this.winHeight / 2D);
    this.updateStatsGUI = updateStatsGUI;
    this.createProjectile = createProjectile;
    this.jump.start();
    this.platforms = platforms;
    this.items = items;
    for (int i = 0; i < this.items.length; i++) {
      this.items[i] = new Item(this.pos, Type.values()[i]);
    }
    this.direction = Direction.NONE;
    this.actionPressed = false;
    this.killEnemyWithBazooka = false;
    this.usingJetpack = false;
    this.kills = 0;
    INSTANCE = this;
  }

  /**
   * Returns the players' direction.
   *
   * @return The players' direction
   */
  public static Direction getDirection() {
    return INSTANCE.direction;
  }

  @Override
  public void paintTo(Graphics2D g) {
    g.setColor(Style.COLOR_PLAYER);
    Shape shape = new RoundRectangle2D.Double(this.pos.getX(), this.pos.getY(),
        WIDTH, HEIGHT, 15, 15);
    g.draw(shape);
    g.fill(shape);
    for (Item item : this.items) {
      if (item.areUsagesLeft()) {
        item.paintTo(g);
      }
    }
    // draw hit-boxes
    super.paintTo(g);
  }

  @Override
  public boolean tick(List<Integer> pressedKeys) throws GameEndedException {
    boolean changed = super.tick(pressedKeys);
    this.tick_isOutOfWindow();
    double v = this.jump.move();
    if (this.isAlive()) {
      // override jump when using jetpack
      if (this.usingJetpack) {
        v = this.getPos().getY() == this.winHalfHeight
            ? 0
            : this.isAbove(this.winHalfHeight) ? 1 : -1;
        this.updateStatsGUI.accept(this.getPos().getY(), this.kills);
      }
      // jump & objects logic
      try {
        changed |= this.tick_jumpAndObjectsLogic();
      } catch (GameEndedException e) {
        return changed | e.isSuccess();
      }
      // control logic - get direction (for movement)
      changed |= this.tick_controlLogic(pressedKeys);
    }
    // apply movement
    if (!(this.direction == Direction.NONE && v == 0)) {
      this.pos.add(new Vertex(this.direction.getValue() * (this.isAlive() ? 2 : 1), v));
      changed = true;
    }
    // handle current item(s)
    changed |= this.tick_itemHandler(pressedKeys);
    return changed;
  }

  /**
   * Outsourced from {@link #tick(List)}. Kills the player if it's below the window bottom.
   *
   * @throws GameEndedException When the player is below the window bottom
   */
  private void tick_isOutOfWindow() throws GameEndedException {
    if (this.pos.getX() < 0 - WIDTH) {
      this.pos.setX(winWidth);
    }
    if (this.pos.getX() > winWidth) {
      this.pos.setX(0 - WIDTH);
    }
    if (this.pos.getY() > winHeight + HEIGHT) {
      this.jump.stop();
      this.setHearts(0);
      throw new GameEndedException(false);
    }
  }

  /**
   * Outsourced from {@link #tick(List)}. Handles the jump and object interaction logic.
   *
   * @return Whether this method has changed the screen
   * @throws GameEndedException thrown by
   *                            {@link #tick_jumpAndObjectsLogic_checkGameObjects(Platform,
   *                            boolean)}
   */
  private boolean tick_jumpAndObjectsLogic() throws GameEndedException {
    boolean changed = false;
    if (this.pos.getOldY() < this.pos.getY()) {
      // jump & objects logic
      for (Platform platform : this.platforms) {
        changed |= this.tick_jumpAndObjectsLogic_checkGameObjects(platform, true);
        if (this.isStandingOnTopOf(platform)) {
          if (!this.usingJetpack) {
            this.jump.start();
          }
          this.updateStatsGUI.accept(this.pos.getY(), this.kills);
          break;
        }
      }
    } else {
      // objects logic
      for (Platform platform : this.platforms) {
        changed |= this.tick_jumpAndObjectsLogic_checkGameObjects(platform, false);
      }
    }
    return changed;
  }

  /**
   * Outsourced from {@link #tick_jumpAndObjectsLogic()}. Handles the object interaction logic.
   *
   * @param platform The platform to check
   * @param kill     Override to allow or deny killing enemies
   * @return Whether this method has changed the screen
   * @throws GameEndedException When the game has ended because the player is touching an enemy. The
   *                            hearts are decreased but the exception should abort this game tick
   */
  private boolean tick_jumpAndObjectsLogic_checkGameObjects(Platform platform, boolean kill)
      throws GameEndedException {
    boolean changed = false;
    GameObj slot = platform.getSlot();
    if (slot instanceof Enemy enemy) {
      if (enemy.isAlive()) {
        // when selecting an enemy, it has to be visible and above player and a distance of >= 100px
        if (this.killEnemyWithBazooka
            && enemy.getPos().getY() > 0 - Enemy.HEIGHT
            && enemy.getPos().getY() < this.pos.getY()
            && this.pos.distance(enemy.getPos()) > 100) {
          this.killEnemyWithBazooka = false;
          this.createProjectile.accept(this, enemy, () -> {
            this.kills++;
            this.updateStatsGUI.accept(this.getPos().getY(), this.kills);
          });
        } else if (this.isStandingOnTopOf(enemy) && kill && !this.usingJetpack) {
          // enemy killed, starting new jump
          if (enemy.decreaseHearts(false)) {
            this.kills++;
            this.updateStatsGUI.accept(this.getPos().getY(), this.kills);
          }
          this.jump.start();
          changed = true;
        } else if (this.touches(enemy) && !this.items[Type.SHIELD.ordinal()].areUsagesLeft()) {
          this.decreaseHearts();
          changed = true;
          // game is (probably) over, stop this tick
          throw new GameEndedException(changed);
        }
      }
    } else if (slot instanceof Item item) {
      if (this.touches(item) && !(this.isUsingJetpack() && item.type == Type.JETPACK)) {
        this.items[item.type.ordinal()].incrementDurability();
        platform.clearSlot();
        changed = true;
      }
    }
    return changed;
  }

  /**
   * Outsourced from {@link #tick(List)}. Handles the player control logic (movement + action).
   *
   * @param pressedKeys All currently pressed keys
   * @return {@code false} (Whether this method has changed the screen)
   */
  private boolean tick_controlLogic(List<Integer> pressedKeys) {
    // player is alive so the direction must not be kept
    this.direction = Direction.NONE;
    this.actionPressed = false;
    for (int key : pressedKeys) {
      switch (key) {
        case KeyBindings.CTRL_MOVE_LEFT ->
            this.direction = this.direction == Direction.NONE ? Direction.LEFT : Direction.NONE;
        case KeyBindings.CTRL_MOVE_RIGHT ->
            this.direction = this.direction == Direction.NONE ? Direction.RIGHT : Direction.NONE;
        case KeyBindings.CTRL_ACTION -> this.actionPressed = true;
      }
    }
    return false;
  }

  /**
   * Outsourced from {@link #tick(List)}. Handles items and their behavior (uses, …).
   *
   * @param pressedKeys All currently pressed keys
   * @return Whether this method has changed the screen
   * @throws GameEndedException Thrown by {@link Item#tick(List)}
   */
  private boolean tick_itemHandler(List<Integer> pressedKeys) throws GameEndedException {
    boolean changed = false;
    this.killEnemyWithBazooka = false;
    for (Item item : this.items) {
      item.tick(pressedKeys);
      if (item.areUsagesLeft()) {
        switch (item.type) {
          case BAZOOKA:
            if (this.actionPressed && item.use()) {
              this.killEnemyWithBazooka = true;
              changed = true;
            }
            break;
          case JETPACK:
            if (item.use()) {
              this.usingJetpack = true;
              this.jump.stop();
              changed = true;
            }
            break;
          case SHIELD:
            if (item.use()) {
              changed = true;
            }
            break;
        }
      } else {
        switch (item.type) {
          case JETPACK:
            if (this.usingJetpack) {
              this.usingJetpack = false;
              this.jump.start(-1);
              changed = true;
            }
            break;
        }
      }
    }
    return changed;
  }

  /**
   * Returns whether the player is currently using a jetpack.
   *
   * @return whether the player is currently using a jetpack
   */
  public boolean isUsingJetpack() {
    return this.usingJetpack;
  }

  /**
   * The direction, the player is currently looking (and moving) to.
   */
  public enum Direction {
    LEFT, // -1
    NONE, // 0
    RIGHT; // 1

    /**
     * Returns the value of this direction. This is used as a factor for the movement.
     * <li>{@link #LEFT}: -1</li>
     * <li>{@link #NONE}: 0</li>
     * <li>{@link #RIGHT}: 1</li>
     *
     * @return the value of this direction
     */
    public int getValue() {
      return this.ordinal() - 1;
    }
  }
}
