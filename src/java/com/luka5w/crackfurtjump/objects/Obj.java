package com.luka5w.crackfurtjump.objects;

import com.luka5w.crackfurtjump.CrackfurtJump;
import com.luka5w.crackfurtjump.data.Style;
import com.luka5w.crackfurtjump.math.Vertex;
import com.luka5w.swinggame.obj.GameObj;
import java.awt.Graphics2D;

public class Obj extends GameObj {

  public Obj(Vertex pos, double width, double height) {
    super(pos, width, height);
  }

  @Override
  public void paintTo(Graphics2D g) {
    if (CrackfurtJump.isDevEnabled()) {
      g.setColor(Style.COLOR_HITBOX_OBJECT);
      super.paintTo(g);
    }
  }

}
