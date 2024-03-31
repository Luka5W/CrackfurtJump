package com.luka5w.crackfurtjump.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Handler class for the file where the high scores are stored.
 */
public class HighscoreHandler {

  /**
   * The file where the high scores are stored.<br/> The file will be located in the working
   * directory and holds high scores separated by {@link #SCORE_SEPARATOR}.
   */
  private static final File FILE_HIGH_SCORE = new File("highscores.dat");

  /**
   * The character to separate the high scores in the file.
   */
  private static final char SCORE_SEPARATOR = '\n';

  /**
   * Whether the high scores are loaded and can be stored.
   */
  private static boolean enabled = false;
  private static List<Long> highScores = null;

  /**
   * Ensures that the high scores file exists and writes the passed high scores to the file
   *
   * @param highScores The high scores to be stored in the file
   * @throws IOException When the file couldn't be created or written to
   */
  private static void write(List<Long> highScores) throws IOException {
    checkFile();
    try (BufferedWriter writer =
        new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_HIGH_SCORE),
            StandardCharsets.US_ASCII))) {
      for (long highScore : highScores) {
        String s = String.valueOf(highScore) + SCORE_SEPARATOR;
        writer.write(s, 0, s.length());
      }
    }
  }

  /**
   * Ensures that the high scores file exists and reads it.
   *
   * @return The high scores stored in the file
   * @throws IOException When the file couldn't be created or read fromL
   */
  private static List<Long> read() throws IOException {
    checkFile();
    List<Long> highScores = new ArrayList<>();
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(new FileInputStream(FILE_HIGH_SCORE),
            StandardCharsets.US_ASCII))) {
      StringBuilder sb = new StringBuilder();
      int c;
      while ((c = reader.read()) != -1) {
        if (c == SCORE_SEPARATOR && !sb.isEmpty()) {
          highScores.add(Long.valueOf(sb.toString()));
          sb = new StringBuilder();
        } else {
          sb.append((char) c);
        }
      }
    }
    return highScores;
  }

  /**
   * Creates the file where the high scores are stored.
   *
   * @return Whether the file was created
   * @throws IOException when the file couldn't be created
   */
  private static boolean checkFile() throws IOException {
    boolean created = false;
    if (!FILE_HIGH_SCORE.exists()) {
      created = FILE_HIGH_SCORE.createNewFile();
    }
    if (!(FILE_HIGH_SCORE.canRead() && FILE_HIGH_SCORE.canWrite())) {
      throw new FileNotFoundException(
          "Cant read and/ or write file '" + FILE_HIGH_SCORE.getAbsolutePath() + "'");
    }
    return created;
  }

  /**
   * Loads the high scores and sets {@link #enabled} to {@code true} when the scores could be
   * loaded, {@code false} otherwise.
   */
  public static void load() {
    try {
      highScores = HighscoreHandler.read();
      enabled = true;
    } catch (IOException e) {
      enabled = false;
      System.err.println("Unable to load high scores: " + e.getMessage());
      e.printStackTrace(System.err);
      System.out.println("High scores are NOT saved after exiting the game.");
      highScores = new ArrayList<>();
    }
  }

  /**
   * Saves the loaded high scores when it is possible to store them ({@link #enabled} is
   * {@code true}).
   */
  public static void save() {
    if (enabled) {
      try {
        write(highScores);
      } catch (IOException e) {
        System.err.println("Unable to save high scores: " + e.getMessage());
        e.printStackTrace(System.err);
        System.out.println("High scores are NOT saved after exiting the game.");
      }
    }
  }

  /**
   * Ensures that the high scores are loaded and returns them.
   *
   * @return The loaded high scores
   */
  public static List<Long> get() {
    if (highScores == null) {
      load();
    }
    return highScores;
  }
}
