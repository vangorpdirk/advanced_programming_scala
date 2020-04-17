package model

import java.util.logging.Logger

import utilities.{IOManager, NgramManager}

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

/**
 * TODO: Work with doubles and divide with list.length for percentage.
 */

class AnalyserModel
{
  val logger: Logger = Logger.getLogger(getClass.getName)
  val wordPattern: Regex = "[a-zA-Z]+".r
  val ioMgr: IOManager = new IOManager()
  val nGramMgr: NgramManager = new NgramManager()

  //voor elke letter van het alfabet van de taal in kwestie het aantal woorden dat er mee begint
  def getStartingLetterResult(language: String, char: String): List[Number] =
  {
    val localList = new ListBuffer[Number]
    for (i <- 0 until char.length)
    {
      localList += ioMgr.getWordsFromFile(language).count(_.startsWith(char(i).toString))
    }
    localList.toList
  }

  //voor elke letter van het alfabet van de taal in kwestie het aantal woorden dat er mee eindigt
  def getEndingWithLetterResult(language: String, char: String): List[Number] =
  {
    val localList = new ListBuffer[Number]
    for (i <- 0 until char.length)
    {
      localList += ioMgr.getWordsFromFile(language).count(_.endsWith(char(i).toString))
    }
    localList.toList
  }

  //voor elke letter van het alfabet van de taal in kwestie de frequentie over de hele taal
  def getTotalFrequencyOfEveryLetter(language: String, char: String): List[Number] =
  {
    //    ioMgr.getLetters(language).foreach(println(_))
    val localList = new ListBuffer[Number]
    for (i <- 0 until char.length)
    {
      localList += ioMgr.getLetters(language).count(_.equals(char(i)))
    }
    localList.toList
  }

  def getTotalFrequencyOfEveryLetterInListOfTuples(language: String, char: String): List[(Char, Double)] =
  {
    var locallist: List[(Char, Double)] = List()
    char.foreach(letter =>
    {
      val perc = (ioMgr.getLetters(language).count(_.equals(letter)).toDouble / ioMgr.getLetters(language).length.toDouble) * 100
      locallist = locallist :+ ((letter, perc.ceil))
    })
    locallist
  }

  //Voor de vowels en de consonants de frequentie over de hele taal, als groep dan. Er komen bvb 30 % vowels en 70 % consonants voor in een taal
  def getFrequencyVowelsInDouble(language: String, vowel: String): Double =
  {
    var perc: Double = 0.0
    vowel.foreach(vowel =>
    {
      perc += ioMgr.getLetters(language).count(_.equals(vowel)).toDouble
    })

    (perc / ioMgr.getLetters(language).length.toDouble) * 100
  }

  def getPopularStartingBigrams(language: String, bigrams: List[String]): List[(String, Int)] =
  {
    var locallist: List[(String, Int)] = List()
    bigrams.foreach(bigram =>
    {
      locallist = locallist :+ ((bigram, ioMgr.getWordsFromFile(language).count(_.startsWith(bigram))))
    })

    locallist.sortWith(_._2 > _._2).take(25)
  }

  def getPopularEndingBigrams(language: String, bigrams: List[String]): List[(String, Int)] =
  {
    var locallist: List[(String, Int)] = List()
    bigrams.foreach(bigram =>
    {
      locallist = locallist :+ ((bigram, ioMgr.getWordsFromFile(language).count(_.endsWith(bigram))))
    })

    locallist.sortWith(_._2 > _._2).take(25)
  }

  def getMostPopularBigrams(language: String, bigrams: List[String]): List[(String, Int)] =
  {
    var locallist: List[(String, Int)] = List()
    bigrams.foreach(bigram =>
    {
      locallist = locallist :+ ((bigram, nGramMgr.countBigrams(ioMgr.getLetters(language).toString(),bigram)))
    })

    locallist.sortWith(_._2 > _._2).take(25)
  }

  def getMostPopularTrigrams(language: String, trigrams: List[String]): List[(String, Int)] =
  {
    var locallist: List[(String, Int)] = List()
    trigrams.foreach(trigram =>
    {
      locallist = locallist :+ ((trigram, nGramMgr.countTrigrams(ioMgr.getLetters(language).toString(),trigram)))
    })

    locallist.sortWith(_._2 > _._2).take(25)
  }
}
