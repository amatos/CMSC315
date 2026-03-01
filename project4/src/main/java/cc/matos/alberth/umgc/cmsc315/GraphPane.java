/*
 Author: Alberth Matos
 CMSC 315, Project 4
 Date: 3 March 2026
 Description:  Class to extend Pane to track vertex names (nextVertexName()),
   a map of vertex positions (vertexPositions(), add
   vertex (addVertexAtLocation()), draw the vertex on the graph (drawVertex()),
   and draw edges on the graph (drawEdge()).
 */

package cc.matos.alberth.umgc.cmsc315;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

public class GraphPane extends Pane {
  // Class to visually display the pane, with event handlers for clicks
  // to create vertices and a method to draw edges.

  // Variables for the graph as well as vertex name.
  // n.b., vertex names start with A and proceed through the alphabet,
  // limiting the number of vertices to 26.
  private final Graph graph;
  private char nextVertexName = 'A';

  // Map vertex name -> circle center, for drawing edges.
  private final Map<String, Point2D> vertexPositions = new HashMap<>();

  public GraphPane(Graph graph) {
    this.graph = graph;

    // Set graph style to light grey background with a light grey border.
    setStyle("-fx-background-color: lightgrey; -fx-border-color: lightgrey;");
    // Set a reasonable size for the graph pane, 400x600 pixels
    setPrefSize(600, 400);

    // Event handler for input device action.  On a setOnMouseClicked() event
    // with the primary button, add a vertex at the clicked location via a
    // call to addVertexAtLocation(x, y), where x and y are provided via
    // the MouseEvent.
    setOnMouseClicked(e -> {
      if (e.getButton() == MouseButton.PRIMARY) {
        addVertexAtLocation(e.getX(), e.getY());
      }
    });
  }

  private void addVertexAtLocation(double x, double y) {
    // Add a vertex at the indicated location.

    // While the instructions do not address the mumber of vertices that
    // should be allowed, it _does_, however, state that first vertex should
    // be labeled A and subsequent vertices should be labeled with the next
    // letters of the alphabet.  This implies that there should be a maximum
    // of 26 vertices, labeled A..Z.  If the next available vertex name goes
    // beyond Z, return an exception indicating the error.
    if (nextVertexName > 'Z') {
      throw new IllegalStateException("Maximum of 26 vertices reached (A..Z).");
    }

    // Set the name of the vertex to the next available letter.
    String name = String.valueOf(nextVertexName++);

    // Add the vertex to the graph.
    Vertex v = new Vertex(x, y, name);
    graph.addVertex(v);
    vertexPositions.put(name, new Point2D(x, y));

    // Call drawVertex to draw the vertex on the canvas.
    drawVertex(v);
  }

  private void drawVertex(Vertex v) {
    // Draw the vertex on the canvas.
    double x = v.getX();
    double y = v.getY();

    // Draw a black circle at the indicated position, with a radius of 5px.
    Circle circle = new Circle(x, y, 5);
    circle.setFill(Color.BLACK);

    // Add a label above the circle containing the vertex name.
    Text label = new Text(v.getName());
    label.setFont(Font.font(12));
    label.setX(x);
    label.setY(y - 10);

    // Add the circle and label to the graph.
    getChildren().addAll(circle, label);
  }

  public boolean drawEdge(String name1, String name2) {
    // Draw an edge from one vertex (name1) to another vertex (name2).
    // Call the map, vertexPosition.get(), to get the location of each
    // vertex.
    Point2D p1 = vertexPositions.get(name1);
    Point2D p2 = vertexPositions.get(name2);

    // If a vertex cannot be found, then this indicates that the vertex
    // does not yet exist.  Return 'false' to the caller to indicate the
    // problem.
    if (p1 == null || p2 == null) {
      return false;
    }

    // Create a line from the center of the first vertex (p1) to the center
    // of the second vertex (p2).
    Line line = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());

    // Add the line to the graph.
    getChildren().add(line);

    // return 'true' indicating that the edge was successfully added.
    return true;
  }
}
