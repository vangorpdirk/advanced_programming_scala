package model

import java.util.logging.Logger

import utilities.{IOManager, NgramManager}

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

/**
 * TODO: Work with doubles and divide with list.length for percentage.
 * TODO: Skipgrams, zie ook NgramManager - nog niet opgelost
 */

class AnalyserModel
{
  val logger: Logger = Logger.getLogger(getClass.getName)
  val wordPattern: Regex = "[a-zA-Z]+".r
  val ioMgr: IOManager = new IOManager()
  val nGramMgr: NgramManager = new NgramManager()

  def getStartingLetterResult(language: String, char: String): List[(Char, Double)] =
  {
    var locallist: List[(Char, Double)] = List()
    for (i <- 0 until char.length)
    {
      val perc = (ioMgr.getWordsFromFile(language).count(_.startsWith(char(i).toString)).toDouble / ioMgr.getWordsFromFile(language).length.toDouble) * 100
      locallist = locallist :+ ((char(i), perc.ceil))
    }
    locallist
  }

  def getEndingWithLetterResult(language: String, char: String): List[(Char, Double)] =
  {
    var locallist: List[(Char, Double)] = List()
    for (i <- 0 until char.length)
    {
      val perc = (ioMgr.getWordsFromFile(language).count(_.endsWith(char(i).toString)).toDouble / ioMgr.getWordsFromFile(language).length.toDouble) * 100
      locallist = locallist :+ ((char(i), perc.ceil))
    }
    locallist
  }

  def getTotalFrequencyOfEveryLetter(language: String, char: String): List[(Char, Double)] =
  {
    var locallist: List[(Char, Double)] = List()
    char.foreach(letter =>
    {
      val perc = (ioMgr.getLetters(language).count(_.equals(letter)).toDouble / ioMgr.getLetters(language).length.toDouble) * 100
      locallist = locallist :+ ((letter, perc.ceil))
    })
    locallist
  }

  def getFrequencyVowelsInDouble(language: String, vowel: String): Double =
  {
    var perc: Double = 0.0
    vowel.foreach(vowel =>
    {
      perc += ioMgr.getLetters(language).count(_.equals(vowel)).toDouble
    })

    (perc / ioMgr.getLetters(language).length.toDouble) * 100
  }

  def getPopularStartingBigrams(language: String, bigrams: List[String]): List[(String, Double)] =
  {
    var locallist: List[(String, Double)] = List()
    bigrams.foreach(bigram =>
    {
      val perc = (ioMgr.getWordsFromFile(language).count(_.startsWith(bigram)).toDouble / ioMgr.getWordsFromFile(language).length.toDouble) * 100
      locallist = locallist :+ ((bigram, perc.ceil))
    })

    locallist.sortWith(_._2 > _._2).take(25)
  }

  def getPopularEndingBigrams(language: String, bigrams: List[String]): List[(String, Double)] =
  {
    var locallist: List[(String, Double)] = List()
    bigrams.foreach(bigram =>
    {
      val perc = (ioMgr.getWordsFromFile(language).count(_.endsWith(bigram)).toDouble / ioMgr.getWordsFromFile(language).length.toDouble) * 100
      locallist = locallist :+ ((bigram, perc.ceil))
    })

    locallist.sortWith(_._2 > _._2).take(25)
  }

  def getMostPopularBigrams(language: String, bigrams: List[String]): List[(String, Int)] =
  {
    var locallist: List[(String, Int)] = List()
    bigrams.foreach(bigram =>
    {
      locallist = locallist :+ ((bigram, nGramMgr.countBigrams(ioMgr.getLetters(language).toString(), bigram)))
    })

    locallist.sortWith(_._2 > _._2).take(25)
  }

  def getMostPopularTrigrams(language: String, trigrams: List[String]): List[(String, Int)] =
  {
    var locallist: List[(String, Int)] = List()
    trigrams.foreach(trigram =>
    {
      locallist = locallist :+ ((trigram, nGramMgr.countTrigrams(ioMgr.getLetters(language).toString(), trigram)))
    })

    locallist.sortWith(_._2 > _._2).take(25)
  }

  //  def getMostPopularSkipgrams(language: String, skipgrams: List[String]): List[(String, Int)] =
  //  {
  //    var locallist: List[(String, Int)] = List()
  //    skipgrams.foreach(skipgram =>
  //    {
  //      locallist = locallist :+ ((skipgram, nGramMgr.count(ioMgr.getLetters(language).toString(),skipgram)))
  //    })
  //
  //    locallist.sortWith(_._2 > _._2).take(25)
  //  }
}
