package com.luka5w.crackfurtjump.gui;

import com.luka5w.crackfurtjump.CrackfurtJump;
import com.luka5w.crackfurtjump.data.Style;
import com.luka5w.crackfurtjump.math.Vertex;
import com.luka5w.swinggame.gui.SwingUtils;
import com.luka5w.swinggame.obj.GUIObj;
import java.awt.Graphics2D;

/**
 * The GUI displayed when the game has ended.
 */
public class ScoreGUI extends GUIObj {

  public static final double WIDTH = CrackfurtJump.WIDTH;
  public static final double HALF = WIDTH / 2;
  public static final double HEIGHT = CrackfurtJump.HEIGHT / 4D;
  public static final double OFFSET = HEIGHT / 5D + 2D;
  private final String score;
  private final String highScore;
  private final String avgScore;

  /**
   * Creates a new GUI displaying some stats.
   *
   * @param pos       The upper left corner of this GUI
   * @param score     The last games score
   * @param highScore The high score
   * @param avgScore  The average of all high scores
   */
  public ScoreGUI(Vertex pos, long score, long highScore, double avgScore) {
    super(pos, WIDTH, HEIGHT);
    this.score = score == highScore ? "HIGH SCORE!" : "SCORE: " + score + "m";
    this.highScore = (score == highScore ? "SCORE: " : "HIGH SCORE: ") + highScore + "m";
    this.avgScore = "AVERAGE SCORE: " + (Math.round(avgScore * 100) / 100D) + "m";
  }

  @Override
  public void paintTo(Graphics2D g) {
    super.paintTo(g);
    g.setColor(Style.BG_COLOR);
    SwingUtils.drawFilledRect(g, this.pos.getX(), this.pos.getY(), WIDTH, HEIGHT);
    g.setFont(Style.TEXT_FONT_BIG);
    drawString(g, this.score, 1);
    g.setFont(Style.TEXT_FONT);
    drawString(g, this.highScore, 2);
    drawString(g, this.avgScore, 3);
    g.setFont(Style.TEXT_FONT_SMALL);
    drawString(g, "Press Space To Play Again", 4);
  }

  /**
   * Utility method to draw a centered line.
   *
   * @param g
   * @param str    The string to write
   * @param offset The line number (will be multiplied with {@link #OFFSET})
   */
  private void drawString(Graphics2D g, String str, double offset) {
    SwingUtils.drawString(g, str, (int) HALF,
        (int) (this.pos.getY() + offset * OFFSET),
        SwingUtils.ALIGN_C | SwingUtils.ALIGN_M, Style.TEXT_COLOR);
  }
}
