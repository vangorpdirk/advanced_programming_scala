package utilities

import java.util.logging.Logger

class AnalyserLog
{
  val logger: Logger = Logger.getLogger(getClass().getName)
  val ioMgr: IOManager = new IOManager()

  def printInfo(string: String): Unit =
  {
    logger.info(string.toString)
    logger.info("\nNumber of words: " + ioMgr.getWordsFromFile(string).count(!_.isEmpty) +
      "\nNumber of capitals: " + ioMgr.getCapitals(string) +
      "\nNumber of small letters: " + ioMgr.getSmallLetters(string) + "\n")
  }
}
