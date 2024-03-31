package com.luka5w.crackfurtjump.gui;

import com.luka5w.crackfurtjump.CrackfurtJump;
import com.luka5w.crackfurtjump.data.Style;
import com.luka5w.crackfurtjump.entities.Item;
import com.luka5w.crackfurtjump.entities.Item.DurabilityUnit;
import com.luka5w.crackfurtjump.entities.Item.Type;
import com.luka5w.crackfurtjump.math.Vertex;
import com.luka5w.swinggame.gui.Padding;
import com.luka5w.swinggame.gui.SwingUtils;
import com.luka5w.swinggame.obj.GUIObj;
import java.awt.Graphics2D;

/**
 * The GUI which displays the players' items.
 */
public class InventoryGUI extends GUIObj {

  /**
   * The padding used for the slots.
   */
  private static final Padding PADDING_ICON = new Padding(2, 5, 2, 40);

  /**
   * The padding used for the background.
   */
  private static final Padding PADDING_TEXT = new Padding(9, PADDING_ICON.right(), 9,
      PADDING_ICON.left());

  /**
   * The height of an inventory slot (in px).
   */
  private static final int SLOT_HEIGHT = (int) (PADDING_ICON.top() + Item.HEIGHT
      + PADDING_ICON.bottom());

  /**
   * The players' items.
   */
  private final Item[] items;

  /**
   * Creates a new GUI for displaying the players' items.
   *
   * @param pos   The lower left corner of this object
   * @param items The players' items
   */
  public InventoryGUI(Vertex pos, Item[] items) {
    super(pos, 0, 0);
    this.items = items;
  }

  @Override
  public void paintTo(Graphics2D g) {
    super.paintTo(g);
    g.setFont(Style.TEXT_FONT);
    // metrics
    String[] itemAmounts = new String[this.items.length];
    int longestStringsWidth = 0;
    int itemCount = 0;
    for (int i = 0; i < this.items.length; i++) {
      if (this.items[i].areUsagesLeft()) {
        itemCount++;
        itemAmounts[i] = switch (Type.values()[i].unit) {
          case USES -> this.items[i].getDurability() + DurabilityUnit.USES.asString();
          case TICKS -> ((int) (this.items[i].getDurability() / CrackfurtJump.TICK_TIME))
              + DurabilityUnit.TICKS.asString();
        };
        int width = SwingUtils.getStringBounds(g, itemAmounts[i]).width;
        if (width > longestStringsWidth) {
          longestStringsWidth = width;
        }
      }
    }
    if (itemCount != 0) {
      // draw background
      g.setColor(Style.BG_COLOR);
      SwingUtils.drawFilledRect(g, this.pos.getX(), this.pos.getY() - SLOT_HEIGHT * itemCount,
          PADDING_TEXT.left() + longestStringsWidth + PADDING_TEXT.right(),
          SLOT_HEIGHT * itemCount);
      // draw inventory
      int x = (int) this.pos.getX() + PADDING_ICON.left() + longestStringsWidth;
      double y = this.pos.getY() - SLOT_HEIGHT / 2D;
      for (int i = 0; i < this.items.length; i++) {
        if (itemAmounts[i] != null) {
          new Item(new Vertex(this.pos.getX() + PADDING_ICON.right(),
              this.pos.getY() - Item.HEIGHT - PADDING_ICON.bottom() - SLOT_HEIGHT * --itemCount),
              this.items[i]).paintTo(g);
          SwingUtils.drawString(g, itemAmounts[i], x, (int) (y - SLOT_HEIGHT * itemCount),
              SwingUtils.ALIGN_R | SwingUtils.ALIGN_M, Style.TEXT_COLOR);
        }
      }
    }
  }
}
