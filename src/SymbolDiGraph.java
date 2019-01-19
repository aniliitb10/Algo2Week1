import edu.princeton.cs.algs4.*;

public class SymbolDiGraph
{
  private ST<Integer, Integer> synIdToVertexIdMap; // id -> vertex-id
  private Integer[] vertexIdToSynIdArray; // vertex-id -> id
  private ST<String, Integer> nounToSynIdMap; // noun -> id
  private ST<Integer, String> synIdToSynsetStrMap; // id -> synset
  private SAP sap;

  public SymbolDiGraph(String synsets, String hypernyms, String delimiter)
  {
    synIdToVertexIdMap = new ST<>();
    nounToSynIdMap = new ST<>();
    synIdToSynsetStrMap = new ST<>();

    In in = new In(synsets);
    while (in.hasNextLine())
    {
      String[] a = in.readLine().split(delimiter);
      Integer synId = Integer.parseInt(a[0]);
      String synsetStr = a[1];

      synIdToVertexIdMap.put(synId, synIdToVertexIdMap.size());
      synIdToSynsetStrMap.put(synId, synsetStr);

      // for separate collection of nounToSynIdMap
      for (String eachNoun : synsetStr.split(" "))
      {
        nounToSynIdMap.put(eachNoun, synId);
      }
    }

    // inverted index to get string vertexIdToSynIdArray in an array
    // int to synset conversion
    vertexIdToSynIdArray = new Integer[synIdToVertexIdMap.size()];
    for (Integer synId : synIdToVertexIdMap.keys())
    {
      vertexIdToSynIdArray[synIdToVertexIdMap.get(synId)] = synId;
    }

    // second pass builds the digraph by connecting first vertex on each
    // line to all others
    Digraph diGraph = new Digraph(synIdToVertexIdMap.size());
    in = new In(hypernyms);
    while (in.hasNextLine())
    {
      String[] a = in.readLine().split(delimiter);

      int v = synIdToVertexIdMap.get(Integer.parseInt(a[0]));
      for (int i = 1; i < a.length; i++)
      {
        int w = synIdToVertexIdMap.get(Integer.parseInt(a[i]));
        diGraph.addEdge(v, w);
      }
    }

    sap = new SAP(diGraph);
  }

  public Iterable<String> nouns()
  {
    return nounToSynIdMap.keys();
  }

  public boolean isNoun(String word)
  {
    return nounToSynIdMap.get(word) != null;
  }

  public int distance(String nounA, String nounB)
  {
    int v = synIdToVertexIdMap.get(nounToSynIdMap.get(nounA));
    int w = synIdToVertexIdMap.get(nounToSynIdMap.get(nounB));
    return sap.length(v, w);
  }

  // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
  // in a shortest ancestral path (defined below)
  public String sap(String nounA, String nounB)
  {
    int v = synIdToVertexIdMap.get(nounToSynIdMap.get(nounA));
    int w = synIdToVertexIdMap.get(nounToSynIdMap.get(nounB));
    return synIdToSynsetStrMap.get(vertexIdToSynIdArray[sap.ancestor(v, w)]);
  }
}