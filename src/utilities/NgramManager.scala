package utilities

import scala.collection.immutable.HashSet
import scala.collection.mutable

/**
 * TODO: setSkipgrams???
 */

class NgramManager
{

  def toBigrams(str: String):HashSet[String] =
  {
    var bigramSet: HashSet[String] = HashSet()
    for (c <- str) yield
      {
        for (c2 <- str) yield
          {
            bigramSet = bigramSet + (c.toString + c2)
          }
      }
    bigramSet
  }

//  def toBigrams(str: String): List[String] =
//  {
//    var bigramList: List[String] = List()
//    for (i <- 0 until str.length)
//    {
//      for (j <- 0 until str.length)
//      {
//        val x1 = str.charAt(i)
//        val x2 = str.charAt(j)
//        val bigram = "" + x1 + x2
//        bigramList = bigramList :+ bigram
//      }
//    }
//    bigramList
//  }

//  def countBigrams(string: String, bigram: String): Int =
//  {
//    //is empty uitsluiten
//    var count = 0
//    for (i <- 0 until string.length - 1)
//    {
//      val currentBigram = "" + string(i) + string(i + 1)
//      if (currentBigram.contains(bigram))
//      {
//        count = count + 1
//      }
//    }
//    count
//  }

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
          val trigram = "" + x1 + x2 + x3
          trigramList = trigramList :+ trigram
        }
      }
    }
    trigramList
  }

//  def countTrigrams(string: String, trigram: String): Int =
//  {
//    //is empty uitsluiten
//    var count = 0
//    for (i <- 0 until string.length - 2)
//    {
//      val currentBigram = "" + string(i) + string(i + 1) + string(i + 2)
//      if (currentBigram.contains(trigram))
//      {
//        count = count + 1
//      }
//    }
//    count
//  }


//  def toSkipGrams(str: String): List[String] =
//  {
//    var skipGramList: List[String] = List()
//    for (i <- 0 until str.length)
//    {
//      for (k <- 0 until str.length)
//      {
//        val x1 = str.charAt(i)
//        val x2 = str.matches(".")
//        val x3 = str.charAt(k)
//        val skipgram = "" + x1 + x2 + x3
//        skipGramList = skipGramList :+ skipgram
//      }
//    }
//    skipGramList
//  }
}
