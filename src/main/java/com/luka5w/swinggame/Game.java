package com.luka5w.swinggame;

import com.luka5w.crackfurtjump.util.GameEndedException;
import com.luka5w.swinggame.obj.IGameObj;
import com.luka5w.swinggame.obj.ITickingGameObj;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public abstract class Game implements IGameLogic {

  private final int width;
  private final int height;

  private final ArrayList<ArrayList<? extends IGameObj>> objects;
  private SwingScreen screen;

  /**
   * Creates a new instance of a game.
   *
   * @param width The width of the window
   * @param height The height of the window
   * @param layerCount The amount of layers for the {@link IGameObj}s (including background and
   *                  environment)
   */
  public Game(int width, int height, int layerCount) {
    this.width = width;
    this.height = height;
    this.objects = new ArrayList<>();
    for (int i = 0; i < layerCount; i++) {
      this.objects.add(new ArrayList<>());
    }
  }

  @Override
  public boolean tick(List<Integer> pressedKeys) {
    boolean screenUpdated = false;
    for (ArrayList<? extends IGameObj> objects : this.objects) {
      for (IGameObj object : objects) {
        if (object instanceof ITickingGameObj tickingGameObject) {
          try {
            if (tickingGameObject.tick(pressedKeys)) {
              screenUpdated = true;
            }
          } catch (GameEndedException e) {
            this.stop(e.isSuccess());
          }
        }
      }
    }
    return screenUpdated;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public void paintTo(Graphics2D g) {
    for (ArrayList<? extends IGameObj> its : this.objects) {
      for (IGameObj it : its) {
        it.paintTo(g);
      }
    }
  }

  /**
   * <b>Einstiegspunkt des Spiels</b>;<br/>
   * Die Methode, die das Spiel und das Fenster initiiert.
   */
  public void start() {
    var f = new javax.swing.JFrame();
    f.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    this.screen = new SwingScreen(this);
    f.add(this.screen);
    f.pack();
    f.setVisible(true);
    f.setResizable(false);
  }

  /**
   * Called when the game has ended.
   *
   * @param success Whether the game has ended successful or not
   */
  protected void stop(boolean success) {
    this.screen.stopTick();
  }

  protected void restartTick() {
    this.screen.restartTick();
  }

  /**
   * Sets the background. If a background already exists, the background will be replaced.
   *
   * @param o The background.
   */
  protected void setBackground(IGameObj o) {
    this.objects.set(0, new ArrayList<>(List.of(o)));
  }

  protected void setEnvironment(IGameObj... o) {
    this.setEnvironment(new ArrayList<>(List.of(o)));
  }

  protected void setEnvironment(ArrayList<? extends IGameObj> o) {
    this.objects.set(1, o);
  }

  protected void addEnvironment(IGameObj o) {
    ((ArrayList<IGameObj>) this.objects.get(0)).add(o);
  }

  protected void setEntities(int layer, IGameObj... o) {
    checkLayer(layer);
    this.setEntities(layer, new ArrayList<>(List.of(o)));
  }

  protected void setEntities(int layer, ArrayList<? extends IGameObj> o) {
    checkLayer(layer);
    this.objects.set(layer, o);
  }

  protected void addEntity(int layer, IGameObj o) {
    checkLayer(layer);
    ((ArrayList<IGameObj>) this.objects.get(layer)).add(o);
  }

  /**
   * Checks if the passed layer is in the range of the available layers.
   *
   * @param layer The layer to check.
   * @throws IndexOutOfBoundsException When the passed layer is out of the range of the available
   *                                   layers.
   */
  private void checkLayer(int layer) throws IndexOutOfBoundsException {
    if (layer < 0 || layer >= this.objects.size()) {
      throw new IndexOutOfBoundsException(
          "Can't add an object to an undefined layer (" + layer + ")");
    }
  }
}
