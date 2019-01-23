import java.util.ArrayList;

public final class WordNet
{
  private final SymbolDiGraph symbolDiGraph;

  // constructor takes the name of the two input files
  public WordNet(String synsets, String hypernyms)
  {
    if (synsets == null || hypernyms == null)
    {
      throw new IllegalArgumentException("synsets == null || hypernyms == null");
    }

    symbolDiGraph = new SymbolDiGraph(synsets, hypernyms, ",");
  }

  // returns all WordNet nouns
  public Iterable<String> nouns()
  {
    ArrayList<String> al = new ArrayList<>();
    Iterable<String> nounItr = symbolDiGraph.nouns();
    nounItr.forEach(al::add);

    return al;
  }

  // is the word a WordNet noun?
  public boolean isNoun(String word)
  {
    return symbolDiGraph.isNoun(word);
  }

  // distance between nounA and nounB (defined below)
  public int distance(String nounA, String nounB)
  {
    if (nounA == null || nounB == null)
    {
      throw new IllegalArgumentException("nounA == null || nounB == null");
    }

    if (!isNoun(nounA) || !isNoun(nounB))
    {
      throw new IllegalArgumentException("nounA || nounB are not nouns");
    }

    if (nounA.equals(nounB)) return 0;

    return symbolDiGraph.distance(nounA, nounB);
  }

  // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
  // in a shortest ancestral path (defined below)
  public String sap(String nounA, String nounB)
  {
    if (nounA == null || nounB == null)
    {
      throw new IllegalArgumentException("nounA == null || nounB == null");
    }

    if (!isNoun(nounA) || !isNoun(nounB))
    {
      throw new IllegalArgumentException("nounA || nounB are not nouns");
    }

    return symbolDiGraph.sap(nounA, nounB);
  }
}
