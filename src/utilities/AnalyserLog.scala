package utilities

import java.util.logging.Logger

/**
 * Bij het opbouwen is deze klasse regelmatig gebruikt, maar naar het einde toe werd ze niet meer gebruikt.
 * Uiteraard blijft het interessant om alles te loggen in een bepaald formaat. Dus heb ik ze laten staan.
 */

class AnalyserLog
{
  val logger: Logger = Logger.getLogger(getClass.getName)
  val ioMgr: IOManager = new IOManager()

  def printInfo(string: String): Unit =
  {
    logger.info(string.toString)
    logger.info("\nNumber of words: " + ioMgr.getWordsFromFile(string).count(!_.isEmpty) +
      "\nNumber of capitals: " + ioMgr.getCapitals(string) +
      "\nNumber of small letters: " + ioMgr.getSmallLetters(string) + "\n")
  }
}
