public class WordNet
{
  private SymbolDiGraph symbolDiGraph;

  // constructor takes the name of the two input files
  public WordNet(String synsets, String hypernyms)
  {
    symbolDiGraph = new SymbolDiGraph(synsets, hypernyms, ",");
  }

  // returns all WordNet nouns
  public Iterable<String> nouns()
  {
    return symbolDiGraph.nouns();
  }

  // is the word a WordNet noun?
  public boolean isNoun(String word)
  {
    return symbolDiGraph.isNoun(word);
  }

  // distance between nounA and nounB (defined below)
  public int distance(String nounA, String nounB)
  {
    if (nounA.equals(nounB)) return 0;

    return symbolDiGraph.distance(nounA, nounB);
  }

  // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
  // in a shortest ancestral path (defined below)
  public String sap(String nounA, String nounB)
  {
    return symbolDiGraph.sap(nounA, nounB);
  }
}
