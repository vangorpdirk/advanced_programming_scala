package utilities

/**
 * In deze klasse zet ik elk alfabet om naar de overeenkomende nGrammen.
 * bv: abc -> toBigram -> aa ab ac ba bb bc ca cb cc
 * Dit zijn nog vectoren in vectoren, dus nadien nog flatten en naar een lijst omzetten.
 */

class NgramManager
{
  def toBigrams(str: String): IndexedSeq[IndexedSeq[String]] =
  {
    for (c <- str) yield
      for (c2 <- str) yield
        c.toString + c2
  }

  def toTrigrams(str: String): IndexedSeq[IndexedSeq[IndexedSeq[String]]] =
  {
    for (c <- str) yield
      for (c2 <- str) yield
        for (c3 <- str) yield
          c.toString + c2 + c3
  }

  def toSkipgrams(str: String): IndexedSeq[IndexedSeq[String]] =
  {
    for (c <- str) yield
      for (c2 <- str) yield
        c.toString + "." + c2
  }
}
