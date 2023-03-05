package com.luka5w.crackfurtjump;

import com.luka5w.crackfurtjump.data.HighscoreHandler;
import com.luka5w.crackfurtjump.data.KeyBindings;
import com.luka5w.crackfurtjump.entities.Item;
import com.luka5w.crackfurtjump.entities.Item.Type;
import com.luka5w.crackfurtjump.entities.Player;
import com.luka5w.crackfurtjump.entities.Projectile;
import com.luka5w.crackfurtjump.gui.StatsGUI;
import com.luka5w.crackfurtjump.gui.InventoryGUI;
import com.luka5w.crackfurtjump.gui.ScoreGUI;
import com.luka5w.crackfurtjump.math.Random;
import com.luka5w.crackfurtjump.math.Vertex;
import com.luka5w.crackfurtjump.objects.Background;
import com.luka5w.crackfurtjump.objects.HighScoreMarker;
import com.luka5w.crackfurtjump.objects.Platform;
import com.luka5w.crackfurtjump.util.EnumSummonObject;
import com.luka5w.swinggame.Game;
import com.luka5w.swinggame.SwingScreen;
import com.luka5w.swinggame.obj.GameObj;
import com.luka5w.util.VoidConsumer;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Entrypoint of the game.
 */
public class CrackfurtJump extends Game {

  private static CrackfurtJump INSTANCE;

  public static final int WIDTH = 384;
  public static final int HEIGHT = 768;
  /**
   * The inverse duration of a {@link SwingScreen#TICK} in seconds.
   */
  public static final double TICK_TIME = 1000D / SwingScreen.TICK;
  /**
   * The default scroll speed.
   */
  private static final double SCROLL_SPEED = 0.5;
  /**
   * The factor of px to meters; 100px = 1m.
   */
  private static final double METER = 100;
  /**
   * Whether cheats are enabled.
   */
  private final boolean dev;
  /**
   * All platforms.
   */
  private ArrayList<Platform> platforms;
  /**
   * All current projectiles.
   */
  private ArrayList<Projectile> projectiles;
  /**
   * The player object.
   */
  private Player player;
  /**
   * The GUI displaying the players' current height.
   */
  private StatsGUI statsGUI;
  /**
   * The GUI displaying the players' current items.
   */
  private InventoryGUI inventoryGUI;
  /**
   * The relative height of the world. Will be increased on each game tick by
   * {@link #SCROLL_SPEED}.
   */
  private double relativeHeight;
  /**
   * Whether the game is running.
   */
  private boolean running;
  /**
   * Whether the game has ended successfully.
   */
  private boolean success;
  /**
   * (Cheats) Whether to summon something definitely.
   */
  private EnumSummonObject summonOverride;

  /**
   * Creates a new game instance (Only one should exist).
   *
   * @param dev Whether cheats and development utilities are enabled
   */
  public CrackfurtJump(boolean dev) {
    super(WIDTH, HEIGHT, 5);
    INSTANCE = this;
    this.dev = dev;
    this.start();
    super.start();
  }

  @Override
  public boolean tick(List<Integer> pressedKeys) {
    if (this.dev) {
      for (int key : pressedKeys) {
        this.summonOverride = switch (key) {
          case KeyBindings.CHEAT_SUMMON_ITEM -> EnumSummonObject.ITEM;
          case KeyBindings.CHEAT_SUMMON_ENEMY -> EnumSummonObject.ENEMY;
          default -> EnumSummonObject.NONE;
        };
      }
    }
    if (this.running) {
      boolean changed = super.tick(pressedKeys);
      this.relativeHeight += this.getSpeed();
      this.projectiles.removeIf(Projectile::hasReachedDst);
      return changed;
    } else {
      if (pressedKeys.contains(KeyEvent.VK_SPACE)) {
        super.stop(this.success);
        this.start();
        this.restartTick();
      }
      return false;
    }
  }

  @Override
  public void start() {
    // setup values
    this.relativeHeight = 0;
    this.running = true;
    this.success = false;
    this.summonOverride = EnumSummonObject.NONE;
    HighscoreHandler.load();
    // setup background
    this.setBackground(new Background(new Vertex(0, 0)));
    // setup environment
    this.platforms = new ArrayList<>();
    this.spreadPlatforms(10, new Vertex((WIDTH >> 1) - Platform.WIDTH / 2, HEIGHT - 100));
    ArrayList<GameObj> environment = createHighScoreMarkers();
    environment.addAll(this.platforms);
    this.setEnvironment(environment);
    // setup projectiles
    this.projectiles = new ArrayList<>();
    this.setEntities(4, this.projectiles);
    // setup player
    Item[] items = new Item[Type.values().length];
    this.player = new Player(new Vertex(WIDTH >> 1, HEIGHT), WIDTH, HEIGHT, this.platforms,
        this::updateStatsGUI, this::createProjectile, items);
    this.setEntities(2, this.player);
    // setup gui
    this.statsGUI = new StatsGUI(new Vertex(5, 5));
    this.inventoryGUI = new InventoryGUI(new Vertex(0, HEIGHT), items);
    this.setEntities(3, this.statsGUI, this.inventoryGUI);
  }

  @Override
  protected void stop(boolean success) {
    this.running = false;
    this.success = success;
    // get high score
    long highScore = 0;
    for (long score : HighscoreHandler.get()) {
      if (highScore < score) {
        highScore = score;
      }
    }
    // if score is a high score, update the current one and save it
    if (this.statsGUI.height() > highScore) {
      highScore = this.statsGUI.height();
      HighscoreHandler.get().add(highScore);
      HighscoreHandler.save();
    }
    // get average score
    double avgScore = 0;
    for (long score : HighscoreHandler.get()) {
      avgScore += score;
      if (highScore < score) {
        highScore = score;
      }
    }
    avgScore /= HighscoreHandler.get().size();
    // display scores
    this.addEntity(3, new ScoreGUI(new Vertex(0, (HEIGHT >> 1) - ScoreGUI.HEIGHT / 2),
        this.statsGUI.height(), highScore, avgScore));
  }

  /**
   * Spreads all platforms over the current visible world.
   *
   * @param platforms The list to add the platforms to
   * @param pos       The position of the 1st platform
   */
  private void spreadPlatforms(int platforms, Vertex pos) {
    this.platforms.add(new Platform(pos, this::getSpeed, this::shouldSummonObject));
    if (platforms >= 0) {
      Vertex newPos = new Vertex(Random.getNextDouble(5, WIDTH - Platform.WIDTH - 5),
          pos.getY() - Random.getNextDouble(Platform.HEIGHT, 120));
      spreadPlatforms(platforms - 1, newPos);
    }
  }

  /**
   * Creates the high score marker objects.
   *
   * @return The list of markers.
   */
  private ArrayList<GameObj> createHighScoreMarkers() {
    ArrayList<GameObj> markers = new ArrayList<>();
    for (Long highScore : HighscoreHandler.get()) {
      markers.add(new HighScoreMarker(new Vertex(WIDTH - HighScoreMarker.WIDTH,
          this.relativeHeight + HEIGHT - highScore * METER), this::getSpeed, highScore));
    }
    return markers;
  }

  /**
   * Calculates the current scroll speed (depending on the players relative and absolute position).
   *
   * @return The current scroll speed.
   */
  private double getSpeed() {
    if (this.player.isUsingJetpack()) {
      return HEIGHT / 70D;
    }
    // TODO: 04.02.23 find best speeds
    double relativePlayerPos = HEIGHT - this.player.getPos().getY();
    double absolutePlayerPos = (this.relativeHeight + relativePlayerPos);
    //return SCROLL_SPEED + relativePlayerPos / HEIGHT / 10
    //    + relativePlayerPos * SCROLL_SPEED / 50;
    double speed = SCROLL_SPEED + relativePlayerPos / HEIGHT + absolutePlayerPos / HEIGHT / 50;
    return speed >= 100 ? 100 : speed;
    //return SCROLL_SPEED + (HEIGHT - this.player.getPos().getY() + relativeHeight / METER) /
    // HEIGHT;
    //    return 0D;
  }

  /**
   * Determines if an object should be summoned in the slot of a platform.
   *
   * @return whether and which type of object should be summoned
   */
  private EnumSummonObject shouldSummonObject() {
    // TODO: 03.03.23 adjust styleguide to enforce this -.-
    // @formatter:off
    return this.summonOverride == EnumSummonObject.NONE
        ? this.statsGUI.height() >= 10 && Random.getNextBool(3)
          ? Random.getNextBool()
            ? EnumSummonObject.ITEM
            : EnumSummonObject.ENEMY
          : EnumSummonObject.NONE
        : this.summonOverride;
    // @formatter:on
  }

  /**
   * Updates the height GUI.
   *
   * @param playerVertPos The players' current position
   * @param kills The players' kills
   */
  private void updateStatsGUI(Double playerVertPos, long kills) {
    this.statsGUI.set(
        (long) Math.floor((this.relativeHeight + HEIGHT - playerVertPos) / METER), kills);
  }

  private void createProjectile(GameObj src, GameObj dst, VoidConsumer callback) {
    this.projectiles.add(new Projectile(src, dst, callback));
  }

  /**
   * The real entry point of the game. Valid arguments are:
   * <li>--cheats: Enables cheats</li>
   *
   * @param args The arguments passed to the game.
   */
  public static void main(String[] args) {
    boolean dev = Arrays.asList(args).contains("--dev");
    System.out.println("Starting game (" + (dev ? "development" : "normal") + " mode)");
    new CrackfurtJump(dev);
  }

  public static boolean isDevEnabled() {
    return INSTANCE.dev;
  }
}
