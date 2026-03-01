/*
 Author: Alberth Matos
 CMSC 315, Project 4
 Date: 3 March 2026
 Description: The first class from the project requirements, an immutable
   class that defines a vertex of the graph, containing the x and y
   coördinates of the vertex as well as its name.
 */

package cc.matos.alberth.umgc.cmsc315;

public final class Vertex {
  // Class variables to hold the x and y coördinates of the vertex, along
  // with the name.
  private final double x;
  private final double y;
  private final String name;

  public Vertex(double x, double y, String name) {
    // Constructor
    this.x = x;
    this.y = y;
    this.name = name;
  }

  public double getX() {
    // Getter for X
    return x;
  }

  public double getY() {
    // Getter for Y
    return y;
  }

  public String getName() {
    // Getter for name
    return name;
  }
}
