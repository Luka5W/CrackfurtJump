package com.luka5w.swinggame.gui;

import com.luka5w.crackfurtjump.math.Vertex;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.font.GlyphVector;
import java.awt.geom.RoundRectangle2D;

// Please ignore this class to prevent psychical damage :P

/**
 * Some utilities for working with swing or awt stuff.
 */
public class SwingUtils {

  public static final int ALIGN_L = 1, ALIGN_C = 2, ALIGN_R = 4, ALIGN_T = 8, ALIGN_M = 16, ALIGN_B = 32;

  public static void drawString(Graphics2D g, String str, int[] pos, Color fg) {
    g.setColor(fg);
    g.drawString(str, pos[0], pos[1]);
  }


  /**
   * Draws a String without background.
   *
   * @param g      The graphics object to draw to
   * @param str    The String to draw
   * @param x      The position of the string
   * @param y      The position of the string
   * @param align  The alignment of the string relative to the passed position. See * *
   *               {@link #ALIGN_L}, {@link #ALIGN_C}, {@link #ALIGN_R}, {@link #ALIGN_T}, * *
   *               {@link #ALIGN_M}, {@link #ALIGN_B}
   * @param fg     The text color
   * @param bounds The strings' bounds
   * @return The passed bounds
   */
  public static Rectangle drawString(Graphics2D g, String str, int x, int y, int align, Color fg,
      Rectangle bounds) {
    int[] pos = getAlign(bounds, x, y, align);
    drawString(g, str, pos, fg);
    return bounds;
  }

  /**
   * Draws a String without background.
   *
   * @param g     The graphics object to draw to
   * @param str   The String to draw
   * @param x     The position of the string
   * @param y     The position of the string
   * @param align The alignment of the string relative to the passed position. See * *
   *              {@link #ALIGN_L}, {@link #ALIGN_C}, {@link #ALIGN_R}, {@link #ALIGN_T}, * *
   *              {@link #ALIGN_M}, {@link #ALIGN_B}
   * @param fg    The text color
   * @return The strings' bounds (See {@link #getStringBounds(Graphics2D, String)})
   */
  public static Rectangle drawString(Graphics2D g, String str, int x, int y, int align, Color fg) {
    return drawString(g, str, x, y, align, fg, getStringBounds(g, str));
  }

  /**
   * Draws a String without background.
   *
   * @param g     The graphics object to draw to
   * @param str   The String to draw
   * @param pos   The position of the string
   * @param align The alignment of the string relative to the passed position. See * *
   *              {@link #ALIGN_L}, {@link #ALIGN_C}, {@link #ALIGN_R}, {@link #ALIGN_T}, * *
   *              {@link #ALIGN_M}, {@link #ALIGN_B}
   * @param fg    The text color
   * @return The strings' bounds (See {@link #getStringBounds(Graphics2D, String)})
   */
  public static Rectangle drawString(Graphics2D g, String str, Vertex pos, int align, Color fg) {
    return drawString(g, str, (int) pos.getX(), (int) pos.getY(), align, fg);
  }

  /**
   * Draws a String with background.
   *
   * @param g       The graphics object to draw to
   * @param str     The String to draw
   * @param x       The position of the string
   * @param y       The position of the string
   * @param align   The alignment of the string relative to the passed position. See *
   *                {@link #ALIGN_L}, {@link #ALIGN_C}, {@link #ALIGN_R}, {@link #ALIGN_T}, *
   *                {@link #ALIGN_M}, {@link #ALIGN_B}
   * @param bg      The background color
   * @param fg      The text color
   * @param padding The padding around the text
   * @return The strings' bounds (See {@link #getStringBounds(Graphics2D, String)})
   */
  public static Rectangle drawString(Graphics2D g, String str, int x, int y, int align, Color bg,
      Color fg, Padding padding) {
    Rectangle bounds = getStringBounds(g, str);
    int[] pos = getAlign(bounds, x, y, align);
    g.setColor(bg);
    drawFilledRect(g, pos[0] - padding.left(), pos[1] - bounds.height - padding.top(),
        padding.left() + bounds.width + padding.right(),
        padding.top() + bounds.height + padding.bottom());
    drawString(g, str, pos, fg);
    return bounds;
  }

  /**
   * Draws a String with background.
   *
   * @param g       The graphics object to draw to
   * @param str     The String to draw
   * @param pos     The position of the string
   * @param align   The alignment of the string relative to the passed position. See *
   *                {@link #ALIGN_L}, {@link #ALIGN_C}, {@link #ALIGN_R}, {@link #ALIGN_T}, *
   *                {@link #ALIGN_M}, {@link #ALIGN_B}
   * @param bg      The background color
   * @param fg      The text color
   * @param padding The padding around the text
   * @return The strings' bounds (See {@link #getStringBounds(Graphics2D, String)})
   */
  public static Rectangle drawString(Graphics2D g, String str, Vertex pos, int align, Color bg,
      Color fg, Padding padding) {
    return drawString(g, str, (int) pos.getX(), (int) pos.getY(), align, bg, fg, padding);
  }

  /**
   * Calculates the new position for a string dependent from its alignment.
   *
   * @param bounds The strings' bounds
   * @param x      The coordinate of the string
   * @param y      The coordinate of the string
   * @param align  The alignment of the string relative to the passed position. See
   *               {@link #ALIGN_L}, {@link #ALIGN_C}, {@link #ALIGN_R}, {@link #ALIGN_T},
   *               {@link #ALIGN_M}, {@link #ALIGN_B}
   * @return The new position of the string dependent from its alignment which should be passed to
   * {@link Graphics2D#drawString} without modification
   */
  private static int[] getAlign(Rectangle bounds, int x, int y, int align) {
    int[] pos = new int[]{x, y};
    if (!(align == ALIGN_L || align == ALIGN_B || align == (ALIGN_L | ALIGN_B))) {
      if ((align & ALIGN_C) != 0) {
        pos[0] -= bounds.width >> 1;
      }
      if ((align & ALIGN_R) != 0) {
        pos[0] -= bounds.width;
      }
      if ((align & ALIGN_T) != 0) {
        pos[1] += bounds.height;
      }
      if ((align & ALIGN_M) != 0) {
        pos[1] += bounds.height >> 1;
      }
    }
    return pos;
  }

  /**
   * Returns the dimension of the passed string (depending on currently selected font).
   *
   * @param g   The graphics object to draw to
   * @param str The string
   * @return The dimension of the passed string
   */
  public static Rectangle getStringBounds(Graphics2D g, String str) {
    GlyphVector gv = g.getFont().createGlyphVector(g.getFontRenderContext(), str);
    return gv.getPixelBounds(null, 0, 0);
  }


  /**
   * Draws a filled rectangle.
   *
   * @param g      The graphics object to draw to
   * @param x      The coordinate of the rectangles' upper left corner
   * @param y      The coordinate of the rectangles' upper left corner
   * @param width
   * @param height
   */
  public static void drawFilledRect(Graphics2D g, double x, double y, double width, double height) {
    drawFilledRect(g, (int) x, (int) y, (int) width, (int) height);
  }

  /**
   * Draws a filled rectangle.
   *
   * @param g      The graphics object to draw to
   * @param x      The coordinate of the rectangles' upper left corner
   * @param y      The coordinate of the rectangles' upper left corner
   * @param width
   * @param height
   */
  public static void drawFilledRect(Graphics2D g, int x, int y, int width, int height) {
    g.fillRect(x - 1, y - 1, width + 2, height + 2);
  }


  /**
   * Draws a filled round rectangle.
   *
   * @param g      The graphics object to draw to
   * @param x      The coordinate of the rectangles' upper left corner
   * @param y      The coordinate of the rectangles' upper left corner
   * @param width
   * @param height
   * @param arcw   The rectangles arch width
   * @param arch   The rectangles arch height
   */
  public static void drawFilledRoundRect(Graphics2D g, double x, double y, double width,
      double height, double arcw, double arch) {
    g.fill(new RoundRectangle2D.Double(x + width, y, width, height, arcw, arch));
  }

  /**
   * Draws a filled round rectangle.
   *
   * @param g      The graphics object to draw to
   * @param x      The coordinate of the rectangles' upper left corner
   * @param y      The coordinate of the rectangles' upper left corner
   * @param width
   * @param height
   * @param arc    The rectangles arch width and height
   */
  public static void drawFilledRoundRect(Graphics2D g, double x, double y, double width,
      double height, double arc) {
    drawFilledRoundRect(g, x, y, width, height, arc, arc);
  }

  /**
   * Draws a polygon with outline ({@link Graphics2D#drawPolygon(Polygon)}) and fills it out
   * ({@link Graphics2D#fillPolygon(Polygon)}).
   *
   * @param g
   * @param xs
   * @param ys
   * @see Graphics2D#drawPolygon(Polygon)
   */
  public static void drawFilledPoly(Graphics2D g, int[] xs, int[] ys) {
    // using the lengths of the different arrays will always cause an error if the array lengths
    // differ
    g.drawPolygon(new Polygon(xs, ys, xs.length));
    g.fillPolygon(new Polygon(xs, ys, ys.length));
  }

  /**
   * Draws the outline of a polygon ({@link Graphics2D#drawPolygon(Polygon)}).
   *
   * @param g
   * @param xs
   * @param ys
   * @see Graphics2D#drawPolygon(Polygon)
   */
  public static void drawPoly(Graphics2D g, int[] xs, int[] ys) {
    // using the lengths of the different arrays will always cause an error if the array lengths
    // differ
    g.drawPolygon(new Polygon(xs, ys, xs.length));
  }
}
