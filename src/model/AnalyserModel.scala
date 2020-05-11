package model

import java.util.logging.Logger
import utilities.{IOManager, NgramManager}
import scala.util.matching.Regex

/**
 * TODO: Skipgrams: controle eventueel via regex "to match any char use ."
 */

class AnalyserModel
{
  val logger: Logger = Logger.getLogger(getClass.getName)
  val wordPattern: Regex = "[a-zA-Z]+".r
  val ioMgr: IOManager = new IOManager()
  val nGramMgr: NgramManager = new NgramManager()

  def getStartingLetterResult(language: String, char: String): IndexedSeq[(Char, Double)] =
  {
    for (c <- char) yield
      {
        (c, ioMgr.getWordsFromFile(language).count(_.startsWith(c.toString)).toDouble)
      }
  }

  def getEndingWithLetterResult(language: String, char: String): IndexedSeq[(Char, Double)] =
  {
    for (c <- char) yield
      {
        (c, ioMgr.getWordsFromFile(language).count(_.endsWith(c.toString)).toDouble)
      }
  }

  def getTotalFrequencyOfEveryLetter(language: String, char: String): IndexedSeq[(Char, Double)] =
  {
    for (c <- char) yield
      {
        (c, ioMgr.getLetters(language).count(_.equals(c)).toDouble)
      }
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
      {
        (bigram, (ioMgr.getWordsFromFile(language).count(_.startsWith(bigram)).toDouble / ioMgr.getWordsFromFile(language).length.toDouble) * 1000)
      }
  }

  def getPopularEndingBigrams(language: String, bigrams: List[String]): List[(String, Double)] =
  {
    for (bigram <- bigrams) yield
      {
        (bigram, (ioMgr.getWordsFromFile(language).count(_.endsWith(bigram)).toDouble / ioMgr.getWordsFromFile(language).length.toDouble) * 1000)
      }
  }

  def getMostPopularBigrams(language: String, bigrams: List[String]): List[(String, Int)] =
  {
    for (bigram <- bigrams) yield
      {
        (bigram, ioMgr.getWordsFromFile(language).count(_.contains(bigram)))
      }
  }

  def getMostPopularTrigrams(language: String, trigrams: List[String]): List[(String, Int)] =
  {
    for (trigram <- trigrams) yield
      {
        (trigram, ioMgr.getWordsFromFile(language).count(_.contains(trigram)))
      }
  }

  def getMostPopularSkipgrams(language: String, skipgrams: List[String]): List[(String, Int)] =
  {
    for (skipgram <- skipgrams) yield
      {
        (skipgram, ioMgr.getWordsFromFile(language).count(_.contains(skipgram)))
      }
  }
}
