package utilities

import scala.io.Source
import scala.util.matching.Regex

class IOManager
{
  val wordPattern: Regex = "[a-zA-Z]+".r

  def getWordsFromFile(string: String): List[String] =
  {
    Source.fromFile(string).getLines().flatMap(_.split("\\W")).filter(wordPattern.matches(_)).toList
  }

  //filter alleen de letters
  def getLetters(language: String): Iterator[Char] =
  {
    Source.fromFile(language).filter(!_.equals('?')).filter(!_.equals('!')).filter(!_.equals('.')).filter(!_.equals(' ')).filter(!_.equals(','))
  }

  //de volgende methodes zijn voor logging purpose
  //Number of capitals
  def getCapitals(string: String): Int =
  {
    Source.fromFile(string).getLines().map(_.count(_.isUpper)).sum
  }

  //Number of small letters
  def getSmallLetters(string: String): Int =
  {
    Source.fromFile(string).getLines().map(_.count(_.isLower)).sum
  }
}
