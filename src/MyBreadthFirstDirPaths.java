import edu.princeton.cs.algs4.Queue;

class MyBreadthFirstDirPaths
{
  private static final int INFINITY = Integer.MAX_VALUE;
  private final int vertices;
  private final boolean[] visited;
  private final int[] distTo;

  MyBreadthFirstDirPaths(MyDigraph myDigraph, int source)
  {
    vertices = myDigraph.getVertices();
    visited = new boolean[this.vertices];
    distTo = new int[this.vertices];

    for (int index = 0; index < vertices; ++index)
    {
      distTo[index] = INFINITY;
    }

    validateVertex(source);
    bfs(myDigraph, source);
  }

  MyBreadthFirstDirPaths(MyDigraph myDigraph, Iterable<Integer> sources)
  {
    vertices = myDigraph.getVertices();
    visited = new boolean[this.vertices];
    distTo = new int[this.vertices];

    for (int index = 0; index < distTo.length; ++index)
    {
      distTo[index] = INFINITY;
    }

    validateVertices(sources);
    bfs(myDigraph, sources);
  }

  private void bfs(MyDigraph myDigraph, int vertex)
  {
    Queue<Integer> queuedVertices = new Queue<>();

    queuedVertices.enqueue(vertex);
    visited[vertex] = true;
    distTo[vertex] = 0;

    while (!queuedVertices.isEmpty())
    {
      int targetVertex = queuedVertices.dequeue();

      for (int adj : myDigraph.getAdjacents(targetVertex))
      {
        if (!visited[adj])
        {
          queuedVertices.enqueue(adj);
          visited[adj] = true;
          distTo[adj] = distTo[targetVertex] + 1;
        }
      }
    }
  }

  private void bfs(MyDigraph myDigraph, Iterable<Integer> vertices)
  {
    Queue<Integer> queuedVertices = new Queue<>();

    for (Integer vertex : vertices)
    {
      queuedVertices.enqueue(vertex);
      visited[vertex] = true;
      distTo[vertex] = 0;
    }

    while (!queuedVertices.isEmpty())
    {
      int targetVertex = queuedVertices.dequeue();

      for (int adj : myDigraph.getAdjacents(targetVertex))
      {
        if (!visited[adj])
        {
          queuedVertices.enqueue(adj);
          visited[adj] = true;
          distTo[adj] = distTo[targetVertex] + 1;
        }
      }
    }
  }

  boolean hasPathTo(int vertex)
  {
    validateVertex(vertex);
    return visited[vertex];
  }

  private void validateVertices(Iterable<Integer> vertices)
  {
    for (Integer vertex : vertices)
    {
      if (vertex == null)
      {
        throw new IllegalArgumentException("From validateVertices");
      }
      validateVertex(vertex);
    }
  }

  private void validateVertex(int vertex)
  {
    if (vertex < 0 || vertex >= vertices)
    {
      throw new IllegalArgumentException("from MyBreadthFirstDirPaths");
    }
  }

  int distTo(int vertex)
  {
    validateVertex(vertex);
    return distTo[vertex];
  }
}
