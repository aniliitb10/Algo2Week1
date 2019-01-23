class MyCycleDetection
{
  private final boolean[] visited;
  private final boolean[] onStack;
  private boolean hasCycle = false;

  MyCycleDetection(MyDigraph myDigraph)
  {
    visited = new boolean[myDigraph.getVertices()];
    onStack = new boolean[myDigraph.getVertices()];

    for (int vertex = 0; vertex < myDigraph.getVertices(); ++vertex)
    {
      if (hasCycle)
      {
        break;
      }

      if (!visited[vertex])
      {
        dfs(myDigraph, vertex);
      }
    }
  }

  private void dfs(MyDigraph myDigraph, int vertex)
  {
    if (hasCycle) return;

    onStack[vertex] = true;
    visited[vertex] = true;

    for (int adjacent : myDigraph.getAdjacents(vertex))
    {
      if (hasCycle) return;

      if (onStack[adjacent])
      {
        hasCycle = true;
        return;
      }

      if (!visited[adjacent])
      {
        dfs(myDigraph, adjacent);
      }
    }

    onStack[vertex] = false;
  }

  boolean hasCycle()
  {
    return hasCycle;
  }
}
