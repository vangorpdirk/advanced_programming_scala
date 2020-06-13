package model

import utilities.{IOManager, NgramManager}

/**
 * In deze klasse vindt u alle methodes die gebruikt zijn voor de analyses. Ik realiseerde me aan het einde dat ik
 * best met dezelfde returnvalues werk, dus strings en doubles. Dat maakt het in de presenter makkelijker. Aanvankelijk
 * gebruikte ik voor de letters chars en doubles.
 *
 * voorbeeld van een methode: ik geef een taal mee en een letter(combinatie). De rest is vrij zelfverklarend. Voordeel
 * van die for-yield is dat hij een vector teruggeeft. Dat werkt uiteraard makkelijk.
 */

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
