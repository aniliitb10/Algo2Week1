import java.util.ArrayList;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

class MyDigraph
{
  private final ArrayList<Integer>[] adjLists;
  private final int vertices;

  // This constructor is custom made for this question
  MyDigraph(String fileName, int vertices)
  {
    this.vertices = vertices;
    adjLists = (ArrayList<Integer>[]) new ArrayList[this.vertices];
    for (int index = 0; index < vertices; ++index)
    {
      adjLists[index] = new ArrayList<>();
    }

    In fileHandle = new In(fileName);
    while(fileHandle.hasNextLine())
    {
      String readLine = fileHandle.readLine();
      String[] idArr = readLine.split(",");

      int v = Integer.parseInt(idArr[0]);

      for (int index = 1; index < idArr.length; ++index)
      {
        int w = Integer.parseInt(idArr[index]);
        adjLists[v].add(w);
      }
    }

    validateSingleRootedDigraph();
  }

  MyDigraph(Digraph digraph)
  {
    vertices = digraph.V();
    adjLists = (ArrayList<Integer>[])new ArrayList[this.vertices];

    for (int index = 0; index < vertices; ++index)
    {
      adjLists[index] = new ArrayList<>();
    }

    for (int vertex = 0; vertex < digraph.V(); ++vertex)
    {
      for (int adj : digraph.adj(vertex))
      {
        adjLists[vertex].add(adj);
      }
    }

    validateSingleRootedDigraph();
  }

  private void validateSingleRootedDigraph()
  {
    int roots = 0;
    for (int vertex = 0; vertex < vertices; ++vertex)
    {
      if (getOutDegree(vertex) == 0)
      {
        roots++;
      }
    }

    if (roots != 1)
    {
      throw new IllegalArgumentException("Not single rooted");
    }
  }

  Digraph getDigraph()
  {
    Digraph digraph = new Digraph(vertices);
    for (int vertex = 0; vertex < vertices; ++vertex)
    {
      for (int adj : getAdjacents(vertex))
      {
        digraph.addEdge(vertex, adj);
      }
    }

    return digraph;
  }

  Iterable<Integer> getAdjacents(int vertex)
  {
    validateVertex(vertex);
    ArrayList<Integer> adj = adjLists[vertex];
    if (adj == null)
    {
      adj = new ArrayList<>();
    }

    return adj;
  }

  int getOutDegree(int vertex)
  {
    validateVertex(vertex);
    return adjLists[vertex].size();
  }

  int getVertices()
  {
    return vertices;
  }

  private void validateVertex(int vertex)
  {
    if (vertex < 0 || vertex >= vertices)
    {
      throw new IllegalArgumentException("Invalid vertex: " + vertex + ", it must be within [0, " + vertices + ")");
    }
  }

}
