import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;

import java.util.ArrayList;

final class SymbolDiGraph
{
  private final ArrayList<String> synsetList;
  private final ST<String, ArrayList<Integer>> nounToSynIdsMap; // noun -> synIds
  private final SAP sap;

  SymbolDiGraph(String synsets, String hypernyms, String delimiter)
  {
    nounToSynIdsMap = new ST<>();
    synsetList = new ArrayList<>();

    In in = new In(synsets);
    while (in.hasNextLine())
    {
      String[] a = in.readLine().split(delimiter);
      String synset = a[1];
      int synId = Integer.parseInt(a[0]);

      synsetList.add(synset);

      // for separate collection of nounToSynIdsMap
      for (String noun : synset.split(" "))
      {
        ArrayList<Integer> nounIds = nounToSynIdsMap.get(noun);
        if (nounIds != null)
        {
          nounIds.add(synId);
        }
        else
        {
          nounIds = new ArrayList<>();
          nounIds.add(synId);
          nounToSynIdsMap.put(noun, nounIds);
        }
      }
    }

    MyDigraph myDigraph = new MyDigraph(hypernyms, synsetList.size());

    // Cycle detection
    MyCycleDetection dc = new MyCycleDetection(myDigraph);
    if (dc.hasCycle())
    {
      throw new IllegalArgumentException("dc.hasCycle()");
    }

    // Single root validation
    MyCycleDetection cd = new MyCycleDetection(myDigraph);
    if (cd.hasCycle())
    {
      throw new IllegalArgumentException();
    }

    sap = new SAP(myDigraph.getDigraph());

    MySingleRootedDigraphDetection msrdd = new MySingleRootedDigraphDetection(myDigraph);
    if (!msrdd.hasSingleRoot())
    {
      throw new IllegalArgumentException("Not single rooted diagraph");
    }
  }

  Iterable<String> nouns()
  {
    ArrayList<String> al = new ArrayList<>();
    Iterable<String> nounIts = nounToSynIdsMap.keys();
    nounIts.forEach(al::add);

    return al;
  }

  int distance(String nounA, String nounB)
  {
    return sap.length(nounToSynIdsMap.get(nounA), nounToSynIdsMap.get(nounB));
  }

  // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
  // in a shortest ancestral path (defined below)
  String sap(String nounA, String nounB)
  {
    return synsetList.get(sap.ancestor(nounToSynIdsMap.get(nounA), nounToSynIdsMap.get(nounB)));
  }

  public boolean isNoun(String word)
  {
    return nounToSynIdsMap.get(word) != null;
  }
}