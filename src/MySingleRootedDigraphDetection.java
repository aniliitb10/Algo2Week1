class MySingleRootedDigraphDetection
{
  private boolean hasSingleRoot = false;

  public MySingleRootedDigraphDetection(MyDigraph myDigraph)
  {
    int rootCounter = 0;

    for (int vertex = 0;vertex < myDigraph.getVertices(); ++vertex)
    {
      if (myDigraph.getOutDegree(vertex) == 0)
      {
        rootCounter += 1;
      }
    }

    if (rootCounter == 1)
    {
      hasSingleRoot = true;
    }
  }

  boolean hasSingleRoot()
  {
    return hasSingleRoot;
  }
}
