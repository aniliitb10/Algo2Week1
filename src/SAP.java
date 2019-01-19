import edu.princeton.cs.algs4.*;

public class SAP
{
  private Digraph digraph;

  // constructor takes a digraph (not necessarily a DAG)
  public SAP(Digraph G)
  {
    digraph = G;
  }

  // ancestor which participates in shortest path between BFDPs.
  // if no such ancestor, then returns -1
  private int sapAncestor(BreadthFirstDirectedPaths bfv, BreadthFirstDirectedPaths bfw)
  {
    int shortestPathLength = Integer.MAX_VALUE;
    int ancestor = -1;

    for (int vertex = 0; vertex < digraph.V(); vertex++)
    {
      if (bfv.hasPathTo(vertex) && bfw.hasPathTo(vertex))
      {
        int pathLength = bfv.distTo(vertex) + bfw.distTo(vertex);
        if (shortestPathLength > pathLength)
        {
          shortestPathLength = pathLength;
          ancestor = vertex;
        }
      }
    }

    return ancestor;
  }

  private int sapAncestorPathLength(BreadthFirstDirectedPaths bfv, BreadthFirstDirectedPaths bfw, int ancestor)
  {
    if (ancestor == -1) return -1;
    return bfv.distTo(ancestor) + bfw.distTo(ancestor);
  }

  // length of shortest ancestral path between v and w; -1 if no such path
  public int length(int v, int w)
  {
    BreadthFirstDirectedPaths bfV = new BreadthFirstDirectedPaths(digraph, v);
    BreadthFirstDirectedPaths bfW = new BreadthFirstDirectedPaths(digraph, w);

    return sapAncestorPathLength(bfV, bfW, sapAncestor(bfV, bfW));
  }

  // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
  public int ancestor(int v, int w)
  {
    BreadthFirstDirectedPaths bfV = new BreadthFirstDirectedPaths(digraph, v);
    BreadthFirstDirectedPaths bfW = new BreadthFirstDirectedPaths(digraph, w);

    return sapAncestor(bfV, bfW);
  }

  // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
  public int length(Iterable<Integer> v, Iterable<Integer> w)
  {
    BreadthFirstDirectedPaths bfV = new BreadthFirstDirectedPaths(digraph, v);
    BreadthFirstDirectedPaths bfW = new BreadthFirstDirectedPaths(digraph, w);

    return sapAncestorPathLength(bfV, bfW, sapAncestor(bfV, bfW));
  }

  // a common ancestor that participates in shortest ancestral path; -1 if no such path
  public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
  {
    BreadthFirstDirectedPaths bfV = new BreadthFirstDirectedPaths(digraph, v);
    BreadthFirstDirectedPaths bfW = new BreadthFirstDirectedPaths(digraph, w);

    return sapAncestor(bfV, bfW);
  }

  // do unit testing of this class
  // do unit testing of this class
  public static void main(String[] args) {
    In in = new In(args[0]);
    Digraph G = new Digraph(in);
    SAP sap = new SAP(G);

    while (!StdIn.isEmpty()) {
      int v = StdIn.readInt();
      int w = StdIn.readInt();
      int length   = sap.length(v, w);
      int ancestor = sap.ancestor(v, w);
      StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
  }
}