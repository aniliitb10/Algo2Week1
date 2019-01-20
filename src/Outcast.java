import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public final class Outcast
{
  private final WordNet wordNet;

  // constructor takes a WordNet object
  public Outcast(WordNet wordnet)
  {
    this.wordNet = wordnet;
  }

  // given an array of WordNet nouns, return an outcast
  public String outcast(String[] nouns)
  {
    String outcastedNoun = null;
    int maxDistance = Integer.MIN_VALUE;

    for (String noun : nouns)
    {
      int distance = 0;
      for (String eachOtherNoun : nouns)
      {
        distance += wordNet.distance(noun, eachOtherNoun);
      }

      if (distance > maxDistance)
      {
        maxDistance = distance;
        outcastedNoun = noun;
      }
    }

    return outcastedNoun;
  }

  // see test client below
  public static void main(String[] args) {
    WordNet wordnet = new WordNet(args[0], args[1]);
    Outcast outcast = new Outcast(wordnet);
    for (int t = 2; t < args.length; t++) {
      In in = new In(args[t]);
      String[] nouns = in.readAllStrings();
      StdOut.println(args[t] + ": " + outcast.outcast(nouns));
    }
  }
}