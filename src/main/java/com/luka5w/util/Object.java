package com.luka5w.util;

/**
 * A utility class for objects which has to have more detailed toString methods.
 */
public abstract class Object {

  @Override
  public String toString() {
    throw new IllegalStateException();
  }

  /**
   * Returns a short string representation of the object. Useful for Objects representing
   * coordinates.
   *
   * @return a string representation of the object.
   * @see #toString()
   */
  public abstract String toShortString();

}
