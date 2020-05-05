package presenters

import java.util.logging.Logger

import model.AnalyserModel
import utilities.{LanguageManager, NgramManager}
import views.charts.ChartView
import views.{AnalyserView, MainMenuView}

/**
 * TODO: skipgrams ???
 *
 * TODO: (GEEN VRAAG VOOR HERWIG) when ready, replace test_dutch with actual languageMgr.setLanguage(languageString)
 */
class AnalyserPresenter(analyserView: AnalyserView)
{
  val logger: Logger = Logger.getLogger(getClass.getName)
  val analyserModel = new AnalyserModel()
  val ngramMgr = new NgramManager()
  val languageMgr = new LanguageManager()

  analyserView.analyserButtons.foreach(b => b.setOnAction(_ =>
  {
    logger.info(b.getText.toLowerCase())
    logger.info(analyserView.languageString)
    val buttonChoice = b.getText.toLowerCase

    buttonChoice match
    {
      case "starts with" => setWordsStartingWith(analyserView.languageString);
      case "ends with" => setWordsEndingWith(analyserView.languageString);
      case "letter frequency" => setTotalFrequency(analyserView.languageString);
      case "vowels/consonants" => setVowelChart(analyserView.languageString);
      case "starting bigrams" => setPopularStartingBigrams(analyserView.languageString);
      case "ending bigrams" => setPopularEndingBigrams(analyserView.languageString);
      case "popular bigrams" => setMostPopularBigrams(analyserView.languageString);
      case "popular trigrams" => setMostPopularTrigrams(analyserView.languageString);
      //      case "popular skipgrams" => "whoopsie, not implemented yet"
    }
  }))

  //  analyserView.languageButtons.foreach(b => b.setOnAction(_ =>
  //  {
  //    val anotherButtonChoice = b.getText.toLowerCase
  //
  //    anotherButtonChoice match
  //    {
  //      case "sort" =>
  //    }
  //  }))

  def setWordsStartingWith(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.clear()
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChart(analyserModel.getStartingLetterResult("resources/languagetxtfiles/test_dutch.txt", languageMgr.setAlphabet(languageString))))
  }

  def setWordsEndingWith(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.clear()
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChart(analyserModel.getEndingWithLetterResult("resources/languagetxtfiles/test_dutch.txt", languageMgr.setAlphabet(languageString))))
  }

  def setTotalFrequency(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.clear()
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChart(analyserModel.getTotalFrequencyOfEveryLetter("resources/languagetxtfiles/test_dutch.txt", languageMgr.setAlphabet(languageString))))
  }

  def setVowelChart(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.clear()
    analyserView.graphicBox.getChildren.add(new ChartView().setPieChart(analyserModel.getFrequencyVowelsInDouble("resources/languagetxtfiles/test_dutch.txt", languageMgr.setVowels(languageString))))
  }

  def setPopularStartingBigrams(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.clear()
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChartWithString(analyserModel.getPopularStartingBigrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toBigrams(languageMgr.setAlphabet(languageString)).toList.flatten)))
  }

  def setPopularEndingBigrams(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.clear()
    analyserView
      .graphicBox
      .getChildren
      .add(new ChartView().setBarChartWithString(analyserModel.getPopularEndingBigrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toBigrams(languageMgr.setAlphabet(languageString)).toList.flatten)))
  }

  def setMostPopularBigrams(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.clear()
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChartWithInt(analyserModel.getMostPopularBigrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toBigrams(languageMgr.setAlphabet(languageString)).toList.flatten)))
  }

  def setMostPopularTrigrams(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.clear()
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChartWithInt(analyserModel.getMostPopularTrigrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toTrigrams(languageMgr.setAlphabet(languageString)))))
  }

  //    def setMostPopularSkipgrams(languageString: String): Unit =
  //    {
  //      analyserView.graphicBox.getChildren.add(new ChartView().setBarChartWithTuplesString(analyserModel.getMostPopularSkipgrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toSkipGrams(languageMgr.setAlphabet(languageString)))))
  //      analyserView.add(analyserView.graphicBox, 1, 0, 1, 10)
  //    }

  //backbutton
  analyserView.backButton.setOnAction(_ =>
  {
    logger.info(analyserView.backButton.getText)
    val mainMenu = new MainMenuView
    val mainPresenter = new MainMenuPresenter(mainMenu)
    analyserView.getScene.setRoot(mainMenu)
  })
}
