package com.luka5w.crackfurtjump.data;

import com.luka5w.crackfurtjump.math.Random;
import java.awt.Color;
import java.awt.Font;

/**
 * Constants class containing styles (colors, fonts, etc.).
 */
public class Style {

  /**
   * Default text color.
   */
  public static final Color TEXT_COLOR = new Color(255, 255, 255);

  /**
   * Default text font.
   */
  public static final Font TEXT_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 20);

  /**
   * Small text color.
   */
  public static final Font TEXT_FONT_SMALL = new Font(Font.MONOSPACED, Font.PLAIN, 10);

  /**
   * Big text color.
   */
  public static final Font TEXT_FONT_BIG = new Font(Font.MONOSPACED, Font.PLAIN, 40);

  /**
   * Default background color.
   */
  public static final Color BG_COLOR = new Color(64, 64, 64, 192);

  /**
   * Color for the figure: Player.
   */
  public static final Color COLOR_PLAYER = Color.GREEN;

  /**
   * Color for the figure: Platform.
   */
  public static final Color COLOR_PLATFORM = Color.LIGHT_GRAY;

  /**
   * Color for the figure: Item (in inventory during cooldown).
   */
  public static final Color COLOR_ITEM_COOLDOWN = Color.DARK_GRAY;

  /**
   * Color for the figure: Item: Bazooka.
   */
  public static final Color COLOR_ITEM_BAZOOKA = Color.MAGENTA;

  /**
   * Color for the figure: Item: Coin (the coin itself).
   */
  public static final Color COLOR_ITEM_COIN_PRIMARY = Color.YELLOW;

  /**
   * Color for the figure: Item: Coin (the coins edge).
   */
  public static final Color COLOR_ITEM_COIN_SECONDARY = Color.ORANGE;

  /**
   * Color for the figure: Item: Heroin (liquid).
   */
  public static final Color COLOR_ITEM_HEROIN_PRIMARY = Color.BLUE;

  /**
   * Color for the figure: Item: Heroin (needle).
   */
  public static final Color COLOR_ITEM_HEROIN_SECONDARY = Color.DARK_GRAY;

  /**
   * Color for the figure: Item: Jetpack.
   */
  public static final Color COLOR_ITEM_JETPACK = Color.GRAY;

  /**
   * Color for the figure: Item: LSD (itself).
   */
  public static final Color COLOR_ITEM_LSD_PRIMARY = Color.YELLOW;

  /**
   * Color for the figure: Item: LSD (outline).
   */
  public static final Color COLOR_ITEM_LSD_SECONDARY = Color.BLACK;

  /**
   * Color for the figure: Item: Shield.
   */
  public static final Color COLOR_ITEM_SHIELD = Color.BLUE;

  /**
   * Color for the figure: Item: Jetpack.
   */
  public static final Color COLOR_ITEM_WEED = new Color(0, 55, 0);

  /**
   * Color for the figure: Enemy (body).
   */
  public static Color getRandomEnemyColor() {
    return new Color(Random.getNextInt(0, 255),Random.getNextInt(0, 255), Random.getNextInt(0, 255));
    //return COLOR_ENEMY_BODY[Random.getNextInt(0, COLOR_ENEMY_BODY.length)];
  }

  /**
   * Color for the figure: Enemy (eyes).
   */
  public static final Color COLOR_ENEMY_EYES = Color.WHITE;

  /**
   * Color for the figure: Enemy (mouth).
   */
  public static final Color COLOR_ENEMY_MOUTH = COLOR_ENEMY_EYES;

  /**
   * Color for the figure: Projectile.
   */
  public static final Color COLOR_PROJECTILE = Color.DARK_GRAY;

  /**
   * Background color.
   */
  public static final Color COLOR_BACKGROUND = new Color(253, 235, 141);

  /**
   * Hit box color for all object types: Living Entity.
   */
  public static final Color COLOR_HITBOX_LIVING_ENTITY = Color.CYAN;

  /**
   * Hit box color for all object types: Item.
   */
  public static final Color COLOR_HITBOX_ITEM = Color.CYAN;

  /**
   * Hit box color for all object types: Object.
   */
  public static final Color COLOR_HITBOX_OBJECT = Color.CYAN;
}
