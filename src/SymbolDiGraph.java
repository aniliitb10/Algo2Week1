import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;

import java.util.ArrayList;

final class SymbolDiGraph
{
  private final ArrayList<String> synsetList;
  private final ST<String, ArrayList<Integer>> nounToSynIdsMap; // noun -> synIds
  private final SAP sap;
  private final Digraph digraph;

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

    digraph = new Digraph(synsetList.size());
    In hypernymsHandle = new In(hypernyms);

    while(hypernymsHandle.hasNextLine())
    {
      String[] a = hypernymsHandle.readLine().split(delimiter);
      int v = Integer.parseInt(a[0]);

      for (int index = 1; index < a.length; ++index)
      {
        int w = Integer.parseInt(a[index]);
        digraph.addEdge(v, w);
      }
    }

    // Cycle detection
    DirectedCycle dc = new DirectedCycle(digraph);
    if (dc.hasCycle())
    {
      throw new IllegalArgumentException("dc.hasCycle()");
    }

    // Single root validation
    validateSingleRooted(digraph);

    sap = new SAP(digraph);
  }

  Iterable<String> nouns()
  {
    ArrayList<String> al = new ArrayList<>();
    Iterable<String> nounIts = nounToSynIdsMap.keys();
    nounIts.forEach(al::add);

    return al;
  }

  private void validateSingleRooted(Digraph dg)
  {
    // Single root detection
    int roots = 0;
    for (int index = 0; index < dg.V(); ++index)
    {
      if (dg.outdegree(index) == 0)
      {
        roots++;
      }
    }

    if (roots != 1)
    {
      throw new IllegalArgumentException("Not single rooted!");
    }
  }

  private void validateNouns(String nounA, String nounB)
  {
    // null check is done in ST.get()
    if (!isNoun(nounA) || !isNoun(nounB))
    {
      throw new IllegalArgumentException("Not nouns");
    }
  }

  boolean isNoun(String word)
  {
    return nounToSynIdsMap.get(word) != null;
  }

  int distance(String nounA, String nounB)
  {
    validateNouns(nounA, nounB);
    return sap.length(nounToSynIdsMap.get(nounA), nounToSynIdsMap.get(nounB));
  }

  // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
  // in a shortest ancestral path (defined below)
  String sap(String nounA, String nounB)
  {
    validateNouns(nounA, nounB);
    return synsetList.get(sap.ancestor(nounToSynIdsMap.get(nounA), nounToSynIdsMap.get(nounB)));
  }
}