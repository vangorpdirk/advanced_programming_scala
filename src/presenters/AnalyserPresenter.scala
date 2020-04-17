package presenters

import java.util.logging.Logger

import model.AnalyserModel
import utilities.{LanguageManager, NgramManager}
import views.charts.ChartView
import views.{AnalyserView, MainMenuView}

/**
 * TODO: skipgrams
 * TODO: when ready, replace test_dutch with languageMgr.setLanguage(languageString)
 *
 * TODO: @param analyserView = ????
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
      case "starts with" => setWordsStartingWith(analyserView.languageString); b.setDisable(true);
      case "ends with" => setWordsEndingWith(analyserView.languageString); b.setDisable(true);
      case "letter frequency" => setTotalFrequency(analyserView.languageString); b.setDisable(true);
      case "vowels/consonants" => setVowelChart(analyserView.languageString); b.setDisable(true);
      case "starting bigrams" => setPopularStartingBigrams(analyserView.languageString); b.setDisable(true);
      case "ending bigrams" => setPopularEndingBigrams(analyserView.languageString); b.setDisable(true);
      case "popular bigrams" => setMostPopularBigrams(analyserView.languageString); b.setDisable(true);
      case "popular trigrams" => setMostPopularTrigrams(analyserView.languageString); b.setDisable(true);
    }
  }))

  def setWordsStartingWith(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChart(languageMgr.setAlphabet(languageString), analyserModel.getStartingLetterResult(languageMgr.setLanguage(languageString), languageMgr.setAlphabet(languageString))))
    analyserView.add(analyserView.graphicBox, 1, 0, 1, 10)
  }

  def setWordsEndingWith(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChart(languageMgr.setAlphabet(languageString), analyserModel.getEndingWithLetterResult(languageMgr.setLanguage(languageString), languageMgr.setAlphabet(languageString))))
    analyserView.add(analyserView.graphicBox, 1, 0, 1, 10)
  }

  def setTotalFrequency(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChartWithTuples(analyserModel.getTotalFrequencyOfEveryLetterInListOfTuples(languageMgr.setLanguage(languageString), languageMgr.setAlphabet(languageString))))
    analyserView.add(analyserView.graphicBox, 1, 0, 1, 10)
  }

  def setVowelChart(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.add(new ChartView().setPieChart(analyserModel.getFrequencyVowelsInDouble(languageMgr.setLanguage(languageString), languageMgr.setVowels(languageString))))
    analyserView.add(analyserView.graphicBox, 1, 0, 1, 10)
  }

  def setPopularStartingBigrams(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChartWithTuplesString(analyserModel.getPopularStartingBigrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toBigrams(languageMgr.setAlphabet(languageString)))))
    analyserView.add(analyserView.graphicBox, 1, 0, 1, 10)
  }

  def setPopularEndingBigrams(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChartWithTuplesString(analyserModel.getPopularEndingBigrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toBigrams(languageMgr.setAlphabet(languageString)))))
    analyserView.add(analyserView.graphicBox, 1, 0, 1, 10)
  }

  def setMostPopularBigrams(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChartWithTuplesString(analyserModel.getMostPopularBigrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toBigrams(languageMgr.setAlphabet(languageString)))))
    analyserView.add(analyserView.graphicBox, 1, 0, 1, 10)
  }

  def setMostPopularTrigrams(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChartWithTuplesString(analyserModel.getMostPopularTrigrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toTrigrams(languageMgr.setAlphabet(languageString)))))
    analyserView.add(analyserView.graphicBox, 1, 0, 1, 10)
  }

  //backbutton
  analyserView.backButton.setOnAction(_ =>
  {
    logger.info(analyserView.backButton.getText)
    val mainMenu = new MainMenuView
    val mainPresenter = new MainMenuPresenter(mainMenu)
    analyserView.getScene.setRoot(mainMenu)
  })
}
