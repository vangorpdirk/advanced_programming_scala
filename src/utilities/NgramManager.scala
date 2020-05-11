package utilities

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
