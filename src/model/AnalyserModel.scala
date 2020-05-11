package model

import java.util.logging.Logger
import utilities.{IOManager, NgramManager}

class AnalyserModel
{
  val logger: Logger = Logger.getLogger(getClass.getName)
//  val wordPattern: Regex = "[a-zA-Z]+".r
  val ioMgr: IOManager = new IOManager()
  val nGramMgr: NgramManager = new NgramManager()

  def getStartingLetterResult(language: String, char: String): IndexedSeq[(Char, Double)] =
  {
    for (c <- char) yield
      (c, ioMgr.getWordsFromFile(language).count(_.startsWith(c.toString)).toDouble)
  }

  def getEndingWithLetterResult(language: String, char: String): IndexedSeq[(Char, Double)] =
  {
    for (c <- char) yield
      (c, ioMgr.getWordsFromFile(language).count(_.endsWith(c.toString)).toDouble)
  }

  def getTotalFrequencyOfEveryLetter(language: String, char: String): IndexedSeq[(Char, Double)] =
  {
    for (c <- char) yield
      (c, ioMgr.getLetters(language).count(_.equals(c)).toDouble)
  }

  def getFrequencyVowelsInDouble(language: String, vowel: String): Double =
  {
    var perc: Double = 0.0
    vowel.foreach(vowel =>
    {
      perc += ioMgr.getLetters(language).count(_.equals(vowel)).toDouble
    })

    (perc / ioMgr.getLetters(language).length.toDouble) * 1000
  }

  def getPopularStartingBigrams(language: String, bigrams: List[String]): List[(String, Double)] =
  {
    for (bigram <- bigrams) yield
      (bigram, (ioMgr.getWordsFromFile(language).count(_.startsWith(bigram)).toDouble / ioMgr.getWordsFromFile(language).length.toDouble) * 1000)
  }

  def getPopularEndingBigrams(language: String, bigrams: List[String]): List[(String, Double)] =
  {
    for (bigram <- bigrams) yield
      (bigram, (ioMgr.getWordsFromFile(language).count(_.endsWith(bigram)).toDouble / ioMgr.getWordsFromFile(language).length.toDouble) * 1000)

  }

  def getMostPopularBigrams(language: String, bigrams: List[String]): List[(String, Double)] =
  {
    for (bigram <- bigrams) yield
      (bigram, ioMgr.getWordsFromFile(language).count(_.contains(bigram)) / ioMgr.getWordsFromFile(language).length.toDouble * 1000)

  }

  def getMostPopularTrigrams(language: String, trigrams: List[String]): List[(String, Double)] =
  {
    for (trigram <- trigrams) yield
      (trigram, ioMgr.getWordsFromFile(language).count(_.contains(trigram)) / ioMgr.getWordsFromFile(language).length.toDouble * 1000)

  }

  def getMostPopularSkipgrams(language: String, skipgrams: List[String]): List[(String, Double)] =
  {
    for (skipgram <- skipgrams) yield
      (skipgram, ioMgr.getWordsFromFile(language).map(skipgram.r.findAllMatchIn(_).length).sum / ioMgr.getWordsFromFile(language).length.toDouble * 1000)
  }
}
