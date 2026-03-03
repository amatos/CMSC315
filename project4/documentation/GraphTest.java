/*
 Author: Alberth Matos
 CMSC 315, Project 4
 Date: 3 March 2026
 Description: JUnit 6 tests for Graph class.
 */

package cc.matos.alberth.umgc.cmsc315;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

  @Test
  void addVertexRejectsDuplicateNames() {
    Graph graph = new Graph();

    assertTrue(graph.addVertex(new Vertex(10, 10, "A")));
    assertFalse(graph.addVertex(new Vertex(20, 20, "A")));
  }

  @Test
  void addEdgeRequiresExistingDistinctVertices() {
    // Adding an edge requires that both vertices already exist.

    // Create a graph containing vertices A and B
    Graph graph = new Graph();
    graph.addVertex(new Vertex(0, 0, "A"));
    graph.addVertex(new Vertex(1, 1, "B"));

    // Creating an edge from A to C should fail, since there is no vertex C
    assertFalse(graph.addEdge("A", "C"));
    // Creating an edge from A to A should fail, since you cannot create an
    // edge that starts and ends on the same vertex.
    assertFalse(graph.addEdge("A", "A"));
    // Creating an edge from A to B should succeed.
    assertTrue(graph.addEdge("A", "B"));
  }

  @Test
  void neighborsAreUndirected() {
    // Verify that all edges created by addEdge() are undirected, that is,
    // are a two-way connection.  (A, B) should mean:
    //   - A is connected to B
    //   - B is connected to A

    // Define a new graph, containing vertices A, B, and C.
    Graph graph = new Graph();
    graph.addVertex(new Vertex(0, 0, "A"));
    graph.addVertex(new Vertex(1, 1, "B"));
    graph.addVertex(new Vertex(2, 2, "C"));
    // Add edges between (A, B) and (A, C)
    graph.addEdge("A", "B");
    graph.addEdge("A", "C");

    // Verify that both B and C are neighbors of A
    assertEquals(Set.of("B", "C"), graph.neighborsOf("A"));
    // Verify that A is a neighbor of B
    assertEquals(Set.of("A"), graph.neighborsOf("B"));
    // Verify that there are no neighbors of Z (which does not exist)
    assertTrue(graph.neighborsOf("Z").isEmpty());
  }

  @Test
  void hasCyclesDetectsTreeVsCycle() {
    // hasCycles should detect graphs that contain a cycle, and not a tree-style
    // acyclic graph.  I.e.: Graph A-B-C does not contain a cycle, but
    // graph A-B-C-A does.

    // Define a new graph, and add vertices A, B, and C.
    Graph acyclic = new Graph();
    acyclic.addVertex(new Vertex(0, 0, "A"));
    acyclic.addVertex(new Vertex(1, 1, "B"));
    acyclic.addVertex(new Vertex(2, 2, "C"));
    // Add an edge between A and B
    acyclic.addEdge("A", "B");
    // Add an edge between B and C
    acyclic.addEdge("B", "C");
    // Check if this graph has a cycle.  It should return false, as it is an
    // acyclic graph.
    assertFalse(acyclic.hasCycles());

    // Generate a new graph containing vertices A, B, and C.
    Graph cyclic = new Graph();
    cyclic.addVertex(new Vertex(0, 0, "A"));
    cyclic.addVertex(new Vertex(1, 1, "B"));
    cyclic.addVertex(new Vertex(2, 2, "C"));
    // Add an edge between A and B.
    cyclic.addEdge("A", "B");
    // Add an edge between B and C.
    cyclic.addEdge("B", "C");
    // Add an edge between C and A, forming a cyclic loop.
    cyclic.addEdge("C", "A");
    // Verify that the graph has a cycle.  It should return true, as this
    // is a cyclic graph.
    assertTrue(cyclic.hasCycles());
  }

  @Test
  void isConnectedHandlesEmptyMissingAConnectedAndDisconnectedCases() {
    /*
     isConnected() should return true for empty graphs, as empty graphs are,
     by definition, connected. It should also return true for graphs missing
     vertex A, as A is the starting point for all graphs. While a graph that
     does not contain A is perfectly valid, the project directions state that
     all graphs should start with vertex A. Therefore, a graph that
     contains vertices that are NOT A should always return unconnected, even
     if the graph is otherwise a connected graph.
     Define an empty graph
    */

    Graph empty = new Graph();
    // The graph should return true, since it is a connected graph.
    assertTrue(empty.isConnected());

    // Define a graph that does NOT contain A
    Graph noA = new Graph();
    // Add a vertex B to the graph.
    noA.addVertex(new Vertex(1, 1, "B"));
    // Even though a single vertex graph is, by definition, connected, this
    // graph should return that it is NOT connected,
    assertFalse(noA.isConnected());

    // Define a new graph, and add vertices A, B, and C.
    Graph connected = new Graph();
    connected.addVertex(new Vertex(0, 0, "A"));
    connected.addVertex(new Vertex(1, 1, "B"));
    connected.addVertex(new Vertex(2, 2, "C"));
    // Add an edge between A - B, and B - C.
    connected.addEdge("A", "B");
    connected.addEdge("B", "C");
    // The graph should return true, since it is a connected graph.
    assertTrue(connected.isConnected());

    // Create a new, disconnected graph, containing vertices A, B, and C
    Graph disconnected = new Graph();
    disconnected.addVertex(new Vertex(0, 0, "A"));
    disconnected.addVertex(new Vertex(1, 1, "B"));
    disconnected.addVertex(new Vertex(2, 2, "C"));
    // Create an edge between A - B.
    disconnected.addEdge("A", "B");

    // The graph should return false, since C is not connected via an edge.
    assertFalse(disconnected.isConnected());
  }

  @Test
  void depthFirstSearchUsesAAsStartAndVisitsExpectedOrder() {
    // All graphs start with A, and proceed from there down the alphabet.

    // Initialize variables, create vertices A, B, C, D, and E, and add
    // edges between:
    //   (A, B)
    //   (A, C)
    //   (B, D)
    //   (C, E)
    Graph graph = new Graph();
    graph.addVertex(new Vertex(0, 0, "A"));
    graph.addVertex(new Vertex(1, 1, "B"));
    graph.addVertex(new Vertex(2, 2, "C"));
    graph.addVertex(new Vertex(3, 3, "D"));
    graph.addVertex(new Vertex(4, 4, "E"));
    graph.addEdge("A", "B");
    graph.addEdge("A", "C");
    graph.addEdge("B", "D");
    graph.addEdge("C", "E");

    // Verify that a DFS returns a list containing, in order, [A, B, D, C, E].
    assertEquals(List.of("A", "B", "D", "C", "E"), names(graph.depthFirstSearch()));
  }

  @Test
  void breadthFirstSearchUsesAAsStartAndVisitsExpectedOrder() {
    // All graphs start with A, and proceed from there down the alphabet.

    // Initialize variables, create vertices A, B, C, D, and E, and add
    // edges between:
    //   (A, B)
    //   (A, C)
    //   (B, D)
    //   (C, E)
    Graph graph = new Graph();
    graph.addVertex(new Vertex(0, 0, "A"));
    graph.addVertex(new Vertex(1, 1, "B"));
    graph.addVertex(new Vertex(2, 2, "C"));
    graph.addVertex(new Vertex(3, 3, "D"));
    graph.addVertex(new Vertex(4, 4, "E"));
    graph.addEdge("A", "B");
    graph.addEdge("A", "C");
    graph.addEdge("B", "D");
    graph.addEdge("C", "E");

    // Verify that a BFS returns a list containing, in order, [A, B, C, D, E].
    assertEquals(List.of("A", "B", "C", "D", "E"), names(graph.breadthFirstSearch()));
  }

  @Test
  void searchesReturnEmptyWhenAIsMissing() {
    // All graphs start with vertex A. If there is no vertex A, every search
    // should return an empty list.

    // Initialize variables, create vertices B and C, and add an edge
    // between them.
    Graph graph = new Graph();
    graph.addVertex(new Vertex(1, 1, "B"));
    graph.addVertex(new Vertex(2, 2, "C"));
    graph.addEdge("B", "C");

    // Verify that both BFS and DFS return empty sets for their searches.
    assertTrue(graph.breadthFirstSearch().isEmpty());
    assertTrue(graph.depthFirstSearch().isEmpty());
  }

  private List<String> names(List<Vertex> vertices) {
    return vertices.stream().map(Vertex::getName).collect(Collectors.toList());
  }
}
