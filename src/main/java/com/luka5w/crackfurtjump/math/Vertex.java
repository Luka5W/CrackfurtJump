package com.luka5w.crackfurtjump.math;

import com.luka5w.util.Object;

/**
 * Simple data class for points in 2D space which stores the previous coordinates.
 */
public class Vertex extends Object {

  private double x;
  private double y;
  private double oldX;
  private double oldY;

  /**
   * Creates a new point with the passed coordinates.
   *
   * @param x
   * @param y
   */
  public Vertex(double x, double y) {
    this.x = x;
    this.y = y;
    this.oldX = x;
    this.oldY = y;
  }

  /**
   * Creates a velocity vector from the source to the destination.
   *
   * @param src The source
   * @param dst The destination
   * @return A new vertex representing the created velocity.
   */
  public static Vertex velocityForPath(Vertex src, Vertex dst) {
    double x = dst.getX() - src.getX();
    double y = dst.getY() - src.getY();
    double hyp = Math.sqrt(x * x + y * y);
    return new Vertex(x / hyp, y / hyp);
  }

  public void setX(double x) {
    this.update();
    this.x = x;
  }

  public void setY(double y) {
    this.update();
    this.y = y;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getOldX() {
    return oldX;
  }

  public double getOldY() {
    return oldY;
  }

  /**
   * Increases the coordinates of this point by the coordinates of the passed point.
   *
   * @param that The coordinates by which these coordinates are to be increased
   */
  public void add(Vertex that) {
    this.update();
    x += that.x;
    y += that.y;
  }

  /**
   * Sets the coordinates of this point to the coordinates of the passed point.
   *
   * @param that The new coordinates of these coordinates
   */
  public void moveTo(Vertex that) {
    this.update();
    x = that.x;
    y = that.y;
  }

  /**
   * Returns a new point whose coordinates correspond to the product of the coordinates of this
   * point and the passed point.
   *
   * @param d The factors
   * @return The new point
   */
  public Vertex mult(double d) {
    return new Vertex(d * x, d * y);
  }

  /**
   * Updates the old coordinates to the current coordinates of this point.
   */
  private void update() {
    this.oldX = this.x;
    this.oldY = this.y;
  }

  @Override
  public Vertex clone() {
    return new Vertex(this.x, this.y);
  }

  @Override
  public String toString() {
    return getClass().getName() + "[x=" + this.x + ",y=" + this.y + "]";
  }

  @Override
  public String toShortString() {
    return "(" + this.x + ";" + this.y + ")";
  }

  /**
   * Returns the distance between this and the passed point using Pythagorean theorem.
   *
   * @param that The other point
   * @return The distance
   */
  public double distance(Vertex that) {
    double x = that.getX() - this.getX();
    double y = that.getY() - this.getY();
    return Math.sqrt(x * x + y * y);
  }
}
