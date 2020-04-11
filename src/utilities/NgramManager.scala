package utilities

class NgramManager
{
  def toBigrams(str: String): List[String] =
  {
    var bigramList: List[String] = List()
    for (i <- 0 until str.length)
    {
      for (j <- 0 until str.length)
      {
        val x1 = str.charAt(i)
        val x2 = str.charAt(j)
        val bigram = "" + x1 + x2
        bigramList = bigramList :+ bigram
      }
    }
    bigramList
  }

  def toTrigrams(str: String): List[String] =
  {
    var trigramList: List[String] = List()
    for (i <- 0 until str.length)
    {
      for (j <- 0 until str.length)
      {
        for (k <- 0 until str.length)
        {
          val x1 = str.charAt(i)
          val x2 = str.charAt(j)
          val x3 = str.charAt(k)
          val bigram = "" + x1 + x2 + x3
          trigramList = trigramList :+ bigram
        }
      }
    }
    trigramList
  }
}
