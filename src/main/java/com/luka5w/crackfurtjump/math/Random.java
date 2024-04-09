package com.luka5w.crackfurtjump.math;

/**
 * Utility class to get some random variables.
 */
public class Random {

  /**
   * The random object to retrieve the variables from.
   */
  private static final java.util.Random RANDOM = new java.util.Random();
  //java.util.Random.from(RandomGenerator.getDefault());

  /**
   * Returns a random boolean.
   *
   * @return A random boolean
   */
  public static boolean getNextBool() {
    return RANDOM.nextBoolean();
  }

  /**
   * Returns {@code true} if all generated booleans are {@code false}.
   * @param balance The amount of random booleans to generate.
   * @return {@code true} if all generated booleans are {@code false}
   */
  public static boolean getNextBool(int balance) {
    for (int i = 0; i < balance; i++) {
      if (RANDOM.nextBoolean()) {
        return false;
      }
    }
    return true;
  }

  /**
   * See {@link #getNextInt(int, int)}
   * @param origin
   * @param bound
   * @return
   */
  public static double getNextDouble(double origin, double bound) {
    return origin + (bound - origin) * RANDOM.nextDouble();
  }

  /**
   * See {@link java.util.Random#nextInt(int)}
   * @param origin
   * @param bound
   * @return
   */
  public static int getNextInt(int origin, int bound) {
    return origin + RANDOM.nextInt(bound - origin);
  }
}
