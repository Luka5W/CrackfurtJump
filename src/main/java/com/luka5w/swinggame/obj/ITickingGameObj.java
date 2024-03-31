package com.luka5w.swinggame.obj;

import com.luka5w.crackfurtjump.util.GameEndedException;
import java.util.List;

public interface ITickingGameObj extends IGameObj {

  /**
   * Called for a single game tick (each 13ms).
   *
   * @param pressedKeys A list containing all currently pressed keys
   * @return {@code true} when the display has changed, {@code false} otherwise
   */
  boolean tick(List<Integer> pressedKeys) throws GameEndedException;
}
