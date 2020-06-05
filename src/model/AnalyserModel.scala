package model

import java.util.logging.Logger
import utilities.{IOManager, NgramManager}

class AnalyserModel
{
  val ioMgr: IOManager = new IOManager()
  val nGramMgr: NgramManager = new NgramManager()

  def getStartingLetterResult(language: String, char: List[Char]): List[(String, Double)] =
  {
    for (c <- char) yield
      (c.toString, ioMgr.getWordsFromFile(language).count(_.startsWith(c.toString)).toDouble)
  }

  def getEndingWithLetterResult(language: String, char: List[Char]): List[(String, Double)] =
  {
    for (c <- char) yield
      (c.toString, ioMgr.getWordsFromFile(language).count(_.endsWith(c.toString)).toDouble)
  }

  def getTotalFrequencyOfEveryLetter(language: String, char: List[Char]): List[(String, Double)] =
  {
    for (c <- char) yield
      (c.toString, ioMgr.getLetters(language).count(_.equals(c)).toDouble)
  }

  //  def getTotalFrequencyOfEveryLetter(language: String, char: String): IndexedSeq[(Char, Double)] =
  //  {
  //    for (c <- char) yield
  //      (c, ioMgr.getLetters(language).count(_.equals(c)).toDouble)
  //  }

  def getFrequencyVowelsInDouble(language: String, vowel: String): Double =
  {
    val seqOfVowels = for (v <- vowel) yield ioMgr.getLetters(language).count(_.equals(v)).toDouble

    seqOfVowels.sum / ioMgr.getLetters(language).length.toDouble * 100
  }

  def getPopularStartingBigrams(language: String, bigrams: List[String]): List[(String, Double)] =
  {
    for (bigram <- bigrams) yield
      (bigram, (ioMgr.getWordsFromFile(language).count(_.startsWith(bigram)).toDouble / ioMgr.getWordsFromFile(language).length.toDouble) * 100)
  }

  def getPopularEndingBigrams(language: String, bigrams: List[String]): List[(String, Double)] =
  {
    for (bigram <- bigrams) yield
      (bigram, (ioMgr.getWordsFromFile(language).count(_.endsWith(bigram)).toDouble / ioMgr.getWordsFromFile(language).length.toDouble) * 100)

  }

  def getMostPopularNgrams(language: String, ngrams: List[String]): List[(String, Double)] =
  {
    for (ngram <- ngrams) yield
      (ngram, ioMgr.getWordsFromFile(language).count(_.contains(ngram)) / ioMgr.getWordsFromFile(language).length.toDouble * 100)
  }

  //deze zou wegkunnen en dan de methode getMostPopularNgram maken ofzo in principe dubbel met bigram
  //  def getMostPopularTrigrams(language: String, trigrams: List[String]): List[(String, Double)] =
  //  {
  //    for (trigram <- trigrams) yield
  //      (trigram, ioMgr.getWordsFromFile(language).count(_.contains(trigram)) / ioMgr.getWordsFromFile(language).length.toDouble * 100)
  //  }

  def getMostPopularSkipgrams(language: String, skipgrams: List[String]): List[(String, Double)] =
  {
    for (skipgram <- skipgrams) yield
      (skipgram, ioMgr.getWordsFromFile(language).map(skipgram.r.findAllMatchIn(_).length).sum / ioMgr.getWordsFromFile(language).length.toDouble * 100)
  }

  def compareBigramWithSkipGram(bigrams: List[(String, Double)], skipgrams: List[(String, Double)]): List[List[Any]] =
  {
    for (skipgram <- skipgrams.sortWith(_._2 > _._2).take(25)) yield
      for (bigram <- bigrams) yield
        if (bigram._1.equals(skipgram._1.replace(".", "")))
          (bigram, skipgram)
  }
}
