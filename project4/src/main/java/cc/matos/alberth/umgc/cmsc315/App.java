/*
 Author: Alberth Matos
 CMSC 315, Project 4
 Date: 3 March 2026
 Description: A JavaFX program that allows a user to draw vertices with an
   input device, automatically naming each vertex A..Z, thus limiting the
   program to only 26 vertices.  The program allows the user to add edge
   lines in-between vertices by entering vertices in a pair of top-line
   textfields, and using a button to add the edge.  The user can then analyze
   the generated graph via four buttons that appear near the bottom, above
   a status line.  The buttons allow the user to see if the vertices are
   connected, whether the graph has cycles, as well as perform depth-first
   and breadth-first searches starting from vertex A.
 */

package cc.matos.alberth.umgc.cmsc315;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class App extends Application {

  private Graph graph;
  private GraphPane graphPane;
  private TextField statusField;
  private TextField v1Field;
  private TextField v2Field;

  @Override
  public void start(Stage primaryStage) {
    graph = new Graph();
    graphPane = new GraphPane(graph);

    BorderPane root = new BorderPane();

    /*
     Top of window controls:
     Add Edge: button
     Vertex 1: label
     Vertex 1: textfield
     Vertex 2: Label
     Vertex 2: textfield
    */
    HBox topControls = buildTopControls();
    root.setTop(topControls);

    // Graph pane
    root.setCenter(graphPane);

    /*
     Bottom of window controls and status field:
     Is Connected?: button
     Has Cycles?: button
     Depth First Search: button
     Breadth First Search: button
     Status field: textfield
    */
    VBox bottomControls = buildBottomControls();
    root.setBottom(bottomControls);

    // Set a reasonable size for the scene window: 500x700 pixels
    Scene scene = new Scene(root, 700, 500);
    // Window title
    primaryStage.setTitle("Project 4");
    primaryStage.setScene(scene);
    // Display the window
    primaryStage.show();
  }

  private HBox buildTopControls() {
    // hbox to contain the top row with 10px padding on all sides, centered.
    HBox hbox = new HBox(10);
    hbox.setPadding(new Insets(10));
    hbox.setAlignment(Pos.CENTER);

    // Create button, addEdgeButton, and a label for vertex1 (v1Label) and
    // a label for vertex2 (v2Label)
    Button addEdgeButton = new Button("Add Edge");
    Label v1Label = new Label("Vertex 1");
    Label v2Label = new Label("Vertex 2");

    // Create textfield for vertex 1, v1Field, set 4 text columns wide
    v1Field = new TextField();
    v1Field.setPrefColumnCount(4);

    // Create textfield for vertex 2, v2Field, set 4 text columns wide
    v2Field = new TextField();
    v2Field.setPrefColumnCount(4);

    /*
     Define the action for addEdgeButton: lambda expression to call
     onAddEdge() when the ActionEvent (e) occurs.
     n.b.: the lambda expression is shorthand for the longer:
        new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            onAddEdge();
        }}
      that would otherwise occur here.
    */
    addEdgeButton.setOnAction(e -> onAddEdge());

    // Add the above-created entities to hbox
    hbox.getChildren().addAll(addEdgeButton, v1Label, v1Field, v2Label, v2Field);
    return hbox;
  }

  private VBox buildBottomControls() {
    // vbox to contain the buttons and status bar that make up the bottow "row"
    VBox vbox = new VBox(10);
    vbox.setPadding(new Insets(10));

    /*
     centered, 10px padded hbox to contain the buttons:
     Is Connected?: button
     Has Cycles?: button
     Depth First Search: button
     Breadth First Search: button
    */
    HBox buttonRow = new HBox(10);
    buttonRow.setAlignment(Pos.CENTER);

    /*
     The four required buttons:
     n.b.:
     Abbreviating Depth First Search to dfs in button name.
     Abbreviating Breadth First Search to bfs in button name.
    */
    Button isConnectedButton = new Button("Is Connected?");
    Button hasCyclesButton = new Button("Has Cycles?");
    Button dfsButton = new Button("Depth First Search");
    Button bfsButton = new Button("Breadth First Search");

    /*
     ActionEvent for isConnectedButton:
     Call graph.isConnected() to see if graph is connected.
     graph.isConnected() returns a boolean value, which is then used to
     print a message indicating that either the graph is connected or
     is not connected.
    */
    isConnectedButton.setOnAction(e -> {
      boolean connected = graph.isConnected();
      statusField.setText(connected ? "The graph is connected"
        : "The graph is not connected");
    });

    /*
     ActionEvent for hasCyclesButton:
     Call graph.hasCycles() to see if graph has cycles.
     graph.hasCycles() returns a boolean value, which is then used to
     print either the graph either has or does not have cycles.
    */
    hasCyclesButton.setOnAction(e -> {
      boolean cycles = graph.hasCycles();
      statusField.setText(cycles ? "The graph has cycles"
        : "The graph doesn't have cycles");
    });

    /*
     Action event for dfsButton:
     Call graph.depthFirstSearch() to perform a depth first search, and
     return a list of Vertex objects that match, if any.
    */
    dfsButton.setOnAction(e -> {
      // Perform a depth first search, set "order" to the results.
      List<Vertex> order = graph.depthFirstSearch();
      // Populate "text" with the map of vertex names, joined by a comma and space.
      String text = order.stream()
        .map(Vertex::getName)
        .collect(Collectors.joining(", "));
      // Set statusField to the result of the above.
      statusField.setText("DFS: " + text);
    });

    // Action event for bfsButton:
    // call graph.breadthFirstSearch() to perform a breadth first search and
    // return a list of Vertex objects that match, if any.
    bfsButton.setOnAction(e -> {
      List<Vertex> order = graph.breadthFirstSearch();
      String text = order.stream()
        .map(Vertex::getName)
        .collect(Collectors.joining(", "));
      statusField.setText("BFS: " + text);
    });

    // Add isConnectedButton, hasCyclesButton, dfsButton, and bfsButton to
    // the button row.
    buttonRow.getChildren().addAll(isConnectedButton, hasCyclesButton, dfsButton, bfsButton);

    // Create a TextField to contain the status output, and set its width
    // to the width of the buttonRow immediately above.
    statusField = new TextField();
    statusField.setEditable(false);
    statusField.prefWidthProperty().bind(buttonRow.widthProperty());

    // Add both the buttonRow and statusField to the vbox container.
    vbox.getChildren().addAll(buttonRow, statusField);
    // Return the vbox.
    return vbox;
  }

  private void onAddEdge() {
    /*
     Add an edge line between Vertex 1 and Vertex 2.
     Edges are created from the clicking addEdgeButton, once values are
     populated in v1Field and v2Field.
     */

    // Get strings in v1Field and v2Field, trim them of whitespace, and set
    // them to upper case only.
    String v1 = v1Field.getText().trim().toUpperCase();
    String v2 = v2Field.getText().trim().toUpperCase();

    // If either v1 or v2 is empty, display a status message in the bottom
    // text field indicating so, and return control.
    if (v1.isEmpty() || v2.isEmpty()) {
      statusField.setText("Please enter both vertex names.");
      return;
    }

    /*
     Adding an edge requires valid values for both  v1 and v2.
     graph.addEdge() will validate that both vertices exist, returning
     a boolean indicating success or failure.
    */
    boolean isValidEdge = graph.addEdge(v1, v2);

    if (isValidEdge) {
      /*
       if isValidEdge is true, call graphPane.drawEdge() to draw the edge.
       as with graph.addEdge, graphPane.drawEdge() returns a boolean
       value indicating success or failure.
      */
      boolean drawn = graphPane.drawEdge(v1, v2);
      if (drawn) {
        statusField.setText("Edge added between " + v1 + " and " + v2);
      } else {
        statusField.setText("Could not draw edge (positions missing).");
      }
    } else {
      // if isValidEdge is false, display an error message indicating such.
      statusField.setText("One or both vertices do not exist.");
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
