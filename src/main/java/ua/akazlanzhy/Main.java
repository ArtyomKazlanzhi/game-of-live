package ua.akazlanzhy;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    Grid grid = new Grid(25,25, 0, 0);
    System.out.println(grid);
    Thread.sleep(1000);
    while (grid.nextGeneration()) {
      Thread.sleep(1000);
      System.out.println("\n" + grid);
    }
    System.out.println("Game over. The grid is not changed");
  }
}
