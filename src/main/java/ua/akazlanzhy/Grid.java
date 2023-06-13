package ua.akazlanzhy;

public class Grid {

  // false - dead, true - live
  private boolean[][] cells;

  public Grid(int width, int height, int gliderStartX, int gliderStartY) {
    this.cells = new boolean[width][height];
    initGlider(gliderStartX, gliderStartY);
  }

  private void initGlider(int startX, int startY) {
    if (startX < 0 || startX + 2 >= cells.length || startY < 0 || startY + 2 >= cells[0].length) {
      throw new IllegalArgumentException("Invalid glider start coordinates");
    }
    cells[startX + 1][startY] = true;
    cells[startX + 2][startY + 1] = true;
    cells[startX + 2][startY + 2] = true;
    cells[startX + 1][startY + 2] = true;
    cells[startX][startY + 2] = true;
  }

  /**
   * @return true if the next generation differs from the current generation, false otherwise.
   */
  public boolean nextGeneration() {
    int rows = cells.length;
    int columns = cells[0].length;
    boolean[][] nextGeneration = new boolean[rows][columns];
    boolean hasChanged = false;

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        int liveNeighbors = countLiveNeighbors(cells, i, j);

        if (!cells[i][j]) {
          if (liveNeighbors == 3) {
            // A dead cell with exactly 3 live neighbors becomes alive
            nextGeneration[i][j] = true;
            hasChanged = true;
          } else {
            // Cell remains dead
            nextGeneration[i][j] = false;
          }
        } else {
          if (liveNeighbors < 2 || liveNeighbors > 3) {
            // Cell dies due to underpopulation or overcrowding of neighbors
            nextGeneration[i][j] = false;
            hasChanged = true;
          } else {
            // Cell continues to live
            nextGeneration[i][j] = true;
          }
        }
      }
    }
    cells = nextGeneration;
    return hasChanged;
  }

  private int countLiveNeighbors(boolean[][] grid, int row, int col) {
    int count = 0;
    int rows = grid.length;
    int columns = grid[0].length;

    int[][] neighbors = {
        {-1, -1}, {-1, 0}, {-1, 1},
        {0, -1}, {0, 1},
        {1, -1}, {1, 0}, {1, 1}
    };

    for (int[] neighbor : neighbors) {
      int newRow = row + neighbor[0];
      int newCol = col + neighbor[1];

      if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < columns && grid[newRow][newCol]) {
        count++;
      }
    }

    return count;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (boolean[] cell : cells) {
      for (boolean b : cell) {
        if (b) {
          sb.append("\uD83D\uDFE2");
        } else {
          sb.append("\uD83D\uDD34");
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
