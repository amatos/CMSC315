/*
 Author: Alberth Matos
 CMSC 315, Project 4
 Date: 3 March 2026
 Description: Track and perform calculations on an undirected graph.
 Contains variables to track vertices (vertices) and adjacency (adjacency)
 Contains methods to:
  - Add vertices (addVertex())
  - add edges (addEdge())
  - determine neighbors (neighborsOf())
  - determine if the graph contains cycles (hasCycles()),
    and it's helper method hasCycleFrom()
  - determine if the graph is connected (isConnected())
  - perform a depth-first search (depthFirstSearch())
  - perform a breadth-first search (breadthFirstSearch())
 */

package cc.matos.alberth.umgc.cmsc315;

import java.util.*;

public class Graph {

  // Map each vertex name to its vertex
  private final Map<String, Vertex> vertices = new LinkedHashMap<>();

  // Adjacency list: vertex name -> neighbors
  private final Map<String, Set<String>> adjacency = new HashMap<>();

  public boolean addVertex(Vertex v) {
    // Adds a vertex to the graph.
    if (vertices.containsKey(v.getName())) {
      return false;
    }

    vertices.put(v.getName(), v);
    adjacency.put(v.getName(), new LinkedHashSet<>());
    return true;
  }

  public boolean addEdge(String name1, String name2) {
    if (!vertices.containsKey(name1) || !vertices.containsKey(name2)) {
      return false;
    }

    if (name1.equals(name2)) {
      return false;
    }

    adjacency.get(name1).add(name2);
    adjacency.get(name2).add(name1);
    return true;
  }

  public Set<String> neighborsOf(String name) {
    return adjacency.getOrDefault(name, Collections.emptySet());
  }



  public boolean hasCycles() {
    // Check if the undirected graph has cycles.
    // A cycle is defined as a path that starts and ends at the same vertex
    // without repeating any edges.  If a path does not return to the origin
    // vertex, it is not a cycle.

    // Set to check if a vertex has been visited
    Set<String> visited = new HashSet<>();

    // Loop to check every vertex.
    for (String start : vertices.keySet()) {
      if (!visited.contains(start)) {
        // If visited does NOT contain 'start', call hasCycleFrom() to check
        // for a cycle
        if (hasCycleFrom(start, null, visited)) {
          // If there is a cycle, return 'true'
          return true;
        }
      }
    }
    // Otherwise, return 'false' as the graph does not contain a cycle
    return false;
  }

  private boolean hasCycleFrom(String current, String parent, Set<String> visited) {
    // Check if the graph has a cycle.
    // Passed-in variables:
    // - current: the current vertex
    // - parent: the parent vertex, if any
    // - visited: set of visited vertices

    // Add current vertex to visited set
    visited.add(current);

    // Iterate through neighborsOf the current vertex
    for (String neighbor : neighborsOf(current)) {
      // for each neighbor, check if it has been visited.
      if (!visited.contains(neighbor)) {
        // if it has NOT been visited, recursively call hasCycleFrom() using
        // this vertex's neighbors.
        if (hasCycleFrom(neighbor, current, visited)) {
          // Continue calling until there is a cycle, or we fall through to
          // checking all neighbors and not finding a cycle.
          return true;
        }
      } else if (!neighbor.equals(parent)) {
        // If we visited a neighbor that is NOT the vertex we came from,
        // then we have a cycle in an undirected graph.  Return 'true'
        return true;
      }
    }
    // If we reach here, we have checked all neighbors and have not found
    // a cycle.  Return 'false'.
    return false;
  }

  public boolean isConnected() {
    // Check if the graph is connected, that is, that there is a path between all vertices.

    // Check if there are any vertices.  By definition, if there are no vertices, then the (empty) graph is connected.
    if (vertices.isEmpty()) {
      return true;
    }

    // Start from any vertex and traverse all reachable vertices.
    // String start = vertices.keySet().iterator().next();
    String start = "A";
    Set<String> visited = new HashSet<>();
    Deque<String> stack = new ArrayDeque<>();
    stack.push(start);

    while (!stack.isEmpty()) {
      // if the stack is NOT empty, set current to the last item placed in the stack
      String current = stack.pop();
      if (!visited.add(current)) {
        // If visited does NOT contain current, add current to the visited list.
        continue;
      }

      for (String neighbor : neighborsOf(current)) {
        // Iterate through each neighbor of the current vertex
        if (!visited.contains(neighbor)) {
          // If it has not been visited, add it to the stack.
          stack.push(neighbor);
        }
      }
    }

    // The graph is connected if, and ONLY if, every vertex can be reached from vertex A.
    return visited.size() == vertices.size();
  }

  public List<Vertex> depthFirstSearch() {
    // Perform a depth-first search
    // Definition:  Using stacks. a DFS follows a path forward until it
    // reaches a leaf node, then backs up the stack, and follows a different
    // path to explore all nodes.

    // If "A" isn't in the graph, there is nothing to traverse.
    if (!vertices.containsKey("A")) {
      return new ArrayList<>();
    }

    // Variables to track order of vertices, whether a vertex has been
    // visited, and the stack of vertices to visit.
    List<Vertex> order = new ArrayList<>();
    Set<String> visited = new HashSet<>();
    Deque<String> stack = new ArrayDeque<>();

    // We always start at A, so push A into the stack.
    stack.push("A");

    while (!stack.isEmpty()) {
      // While the stack is NOT empty, pop the next vertex from the stack.
      String current = stack.pop();
      // If the vertex has NOT been visited, add it to the visited set.
      if (!visited.add(current)) {
        continue;
      }

      // Get the current vertex from the map.
      Vertex v = vertices.get(current);

      if (v != null) {
        // if the vertex is NOT null, add it to the order list.
        order.add(v);
      }

      // neighborsOf() returns a Set; to keep traversal stable, push in reverse
      // of its iteration order so the first neighbor is processed first.
      List<String> neighbors = new ArrayList<>(neighborsOf(current));
      for (int counter = neighbors.size() - 1; counter >= 0; counter--) {
        // Get the neighbors of the current vertex
        String n = neighbors.get(counter);

        if (!visited.contains(n)) {
          // If the neighbor has NOT been visited, push it onto the stack
          stack.push(n);
        }
      }
    }
    // Return the order of vertices visited in depth-first search
    return order;
  }

  public List<Vertex> breadthFirstSearch() {
    // Perform a breadth-first search
    // Definition:  Using queues. a BFS visits all vertices at an increasing
    // distance from the start.  That is, first it visits all vertices 1
    // step away from the start, then 2 steps, and so on.

    // If "A" isn't in the graph, there is nothing to traverse.
    if (!vertices.containsKey("A")) {
      return new ArrayList<>();
    }

    // Variables for the path order, whether a vertex is visited, and the
    // queue of vertices
    List<Vertex> order = new ArrayList<>();
    Set<String> visited = new HashSet<>();
    Deque<String> queue = new ArrayDeque<>();

    // Add A to the visited list
    visited.add("A");

    // Add A to the end of the queue.
    queue.addLast("A");

    while (!queue.isEmpty()) {
      // While the queue is NOT empty, dequeue the first element
      // in the queue, that is, the name of the first element.
      String current = queue.removeFirst();

      // Get the vertex corresponding to the first vertex name.
      Vertex v = vertices.get(current);

      if (v != null) {
        // If the vertex is not null, add it to the visited order list.
        order.add(v);
      }

      // For BFS, enqueue neighbors in the set's iteration order.
      for (String n : neighborsOf(current)) {
        if (visited.add(n)) {
          // If the neighbor has been visited, add it to the end of the queue.
          queue.addLast(n);
        }
      }
    }

    // Return the order in which the vertices were visited.
    return order;
  }
}
