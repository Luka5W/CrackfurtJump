package com.luka5w.swinggame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Visualisierung und Container des Spiels.
 */
public class SwingScreen extends JPanel {

  private static final long serialVersionUID = 1403492898373497054L;

  public static final int TICK = 13; // 13 ms = 60 FPS
  private final IGameLogic logic;
  private final List<Integer> pressedKeys;
  private final Timer tickTimer;

  /**
   * Initiiert ein neues Spiel mit der übergebenen Spiellogik.
   * @param logic Die Spiellogik
   */
  public SwingScreen(IGameLogic logic) {
    this.logic = logic;

    this.pressedKeys = new ArrayList<>();

    // setup & start game tick
    this.tickTimer = new Timer(TICK, event -> {
      if (this.logic.tick(pressedKeys)) {
        this.repaint();
        this.getToolkit().sync();
      }
      this.requestFocusInWindow();
    });
    this.tickTimer.start();

    // key events
    this.addKeyListener(new KeyAdapter() {

      @Override
      public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (!pressedKeys.contains(keyCode)) {
          pressedKeys.add(keyCode);
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (pressedKeys.contains(keyCode)) {
          // cast to call the method remove(Object o) instead of remove(int index)
          pressedKeys.remove((Object) keyCode);
        }
      }
    });
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(this.logic.getWidth(), this.logic.getHeight());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    this.logic.paintTo(g2);
  }

  /**
   * Stops the timer.
   */
  public void stopTick() {
    this.tickTimer.stop();
  }

  /**
   * Restarts the timer.
   */
  public void restartTick() {
    this.tickTimer.restart();
  }
}
