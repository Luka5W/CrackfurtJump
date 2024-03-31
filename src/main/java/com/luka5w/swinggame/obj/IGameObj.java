package com.luka5w.swinggame.obj;

import com.luka5w.crackfurtjump.math.Vertex;
import java.awt.Graphics2D;

/**
 * Alle gültigen Spielobjekte müssen die hier definierten Eigenschaften und Verhalten haben.
 */
public interface IGameObj {

  /**
   * Gibt die aktuelle Position des Spielobjekts zurück.
   *
   * @return Die aktuelle Position des Spielobjekts
   */
  Vertex getPos();

  /**
   * Gibt die Weite des Spielobjekts (HitBox) zurück.
   *
   * @return Die Weite des Spielobjekts (HitBox)
   */
  double getWidth();

  /**
   * Gibt die Höhe des Spielobjekts (HitBox) zurück.
   *
   * @return Die Höhe des Spielobjekts (HitBox)
   */

  double getHeight();

  /**
   * Mit dieser Methode wird das Spielobjekt in dem Fenster (in dem übergebenen grafischen Kontext)
   * visualisiert (angezeigt).
   *
   * @param g Der grafische Kontext
   */
  default void paintTo(Graphics2D g) {
    g.drawRect((int) this.getPos().getX(), (int) this.getPos().getY(), (int) this.getWidth(),
        (int) this.getHeight());
  }

  /**
   * Prüft, ob die übergebene Koordinate über diesem Spielobjekt liegt.
   *
   * @param y Die zu prüfende Koordinate
   * @return {@code true}, wenn die übergebene Koordinate über diesem Spielobjekt liegt,
   * {@code false}, wenn die übergebene Koordinate in oder unter diesem Spielobjekt liegt
   */
  default boolean isAbove(double y) {
    return getPos().getY() + getHeight() < y;
  }

  /**
   * Prüft, ob das übergebene Spielobjekt über diesem Spielobjekt ist.
   *
   * @param that Das zu prüfende Spielobjekt
   * @return {@code true}, wenn das übergebene Spielobjekt über diesem Spielobjekt ist,
   * {@code false}, wenn das übergebene Spielobjekt in oder unter diesem Spielobjekt ist
   */
  default boolean isAbove(IGameObj that) {
    return isAbove(that.getPos().getY());
  }

  /**
   * Prüft, ob das übergebene Spielobjekt unter diesem Spielobjekt ist.
   *
   * @param that Das zu prüfende Spielobjekt
   * @return {@code true}, wenn das übergebene Spielobjekt unter diesem Spielobjekt ist,
   * {@code false}, wenn das übergebene Spielobjekt in oder über diesem Spielobjekt ist
   */
  default boolean isUnderneath(IGameObj that) {
    return that.isAbove(this);
  }

  /**
   * Prüft, ob die übergebene Koordinate links von diesem Spielobjekt liegt.
   *
   * @param x Die zu prüfende Koordinate
   * @return {@code true}, wenn die übergebene Koordinate links von diesem Spielobjekt liegt,
   * {@code false}, wenn die übergebene Koordinate in oder rechts von diesem Spielobjekt liegt
   */
  default boolean isLeftOf(double x) {
    return getPos().getX() + getWidth() < x;
  }

  /**
   * Prüft, ob das übergebene Spielobjekt links von diesem Spielobjekt ist.
   *
   * @param that Das zu prüfende Spielobjekt
   * @return {@code true}, wenn das übergebene Spielobjekt links von diesem Spielobjekt ist,
   * {@code false}, wenn das übergebene Spielobjekt in oder rechts von diesem Spielobjekt ist
   */
  default boolean isLeftOf(IGameObj that) {
    return isLeftOf(that.getPos().getX());
  }

  /**
   * Prüft, ob das übergebene Spielobjekt rechts von diesem Spielobjekt ist.
   *
   * @param that Das zu prüfende Spielobjekt
   * @return {@code true}, wenn das übergebene Spielobjekt rechts von diesem Spielobjekt ist,
   * {@code false}, wenn das übergebene Spielobjekt in oder links von diesem Spielobjekt ist
   */
  default boolean isRightOf(IGameObj that) {
    return that.isLeftOf(this);
  }

  /**
   * Prüft, ob sich dieses und das übergebene Spielobjekt berühren. Dabei muss sich das übergebene
   * Spielobjekt in mindestens einer Dimension mit diesem Spielobjekt überschneiden (bzw.
   * überlappen).
   *
   * @param that Das zu prüfende Spielobjekt
   * @return {@code true}, wenn das übergebene Spielobjekt sich mit diesem in mindestens einer
   * Dimension schneidet, {@code false}, wenn das übergebene Spielobjekt in allen Dimensionen einen
   * Abstand von mindestens 0 px hat.
   */
  default boolean touches(IGameObj that) {
    return
        !(isAbove(that) || isUnderneath(that)
            || isLeftOf(that) || isRightOf(that));
  }

  /**
   * Prüft, ob sich das übergebene Spielobjekt genau über diesem Spielobjekt befindet.
   *
   * @param that Das zu prüfende Spielobjekt
   * @return {@code true}, wenn das übergebene Spielobjekt über diesem ist und mit höchstens einem
   * Schritt in die aktuelle Bewegungsrichtung zzgl. Toleranzfaktor erreichbar ist <i>(also, die
   * Koordinaten dieses Spielobjekts nach einem Schritt + Toleranzfaktor höher als die des
   * übergebenen Objekts sind)</i>
   */
  default boolean isStandingOnTopOf(IGameObj that) {
    return !(isLeftOf(that) || isRightOf(that))
        && isAbove(that.getPos().getY() + 3)
        && !isAbove(that.getPos().getY() - 3);
  }
}
