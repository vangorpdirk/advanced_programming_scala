package presenters

import java.util.logging.Logger

import model.AnalyserModel
import utilities.{LanguageManager, NgramManager}
import views.charts.ChartView
import views.{AnalyserView, MainMenuView}

/**
 * TODO: integrate function as parameter (problem with mismatch)
 * TODO: match popular skipgrams with matching bigrams???
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
      //      case "starts with" => setWordsStartingWith(analyserModel.getStartingLetterResult("resources/languagetxtfiles/test_dutch.txt", languageMgr.setAlphabet(analyserView.languageString)));

      case "starts with" => setWordsStartingWith(analyserView.languageString)
      case "ends with" => setWordsEndingWith(analyserView.languageString)
      case "letter frequency" => setTotalFrequency(analyserView.languageString)
      case "vowels/consonants" => setVowelChart(analyserView.languageString)
      case "starting bigrams" => setPopularStartingBigrams(analyserView.languageString)
      case "ending bigrams" => setPopularEndingBigrams(analyserView.languageString)
      case "popular bigrams" => setMostPopularBigrams(analyserView.languageString)
      case "popular trigrams" => setMostPopularTrigrams(analyserView.languageString)
      case "popular skipgrams" => setMostPopularSkipgrams(analyserView.languageString)
      case "skipvsbigram" => skipVsBigram(analyserView.languageString)
    }
  }))

  analyserView.languageButtons.foreach(b => b.setOnAction(_ =>
  {
    val anotherButtonChoice = b.getText.toLowerCase

    anotherButtonChoice match
    {
      //              case "sort" => setStartingLetterSort(analyserView.languageString)
      case "dutch" => changeLanguage("dutch")
      case "english" => changeLanguage("english")
      case "finnish" => changeLanguage("finnish")
      case "german" => changeLanguage("german")
      case "italian" => changeLanguage("italian")
      case "danish" => changeLanguage("danish")
      case "portugese" => changeLanguage("portugese")
      case "spanish" => changeLanguage("spanish")
    }
  }))

  def changeLanguage(changeLanguage: String): Unit =
  {
    val newView = new AnalyserView(changeLanguage)
    val analyserPresenter = new AnalyserPresenter(newView)
    analyserView.getScene.setRoot(newView)
  }

  def setWordsStartingWith(languageString: String): Unit =
  {
    logger.info("language = " + languageString)
    analyserView.graphicBox.getChildren.clear()
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChart(analyserModel.getStartingLetterResult("resources/languagetxtfiles/test_dutch.txt", languageMgr.setAlphabet(languageString)).toList))
  }

  //  def setWordsStartingWith(getStartingLetter: () => IndexedSeq[(Char, Double)]): Unit =
  //  {
  //    analyserView.graphicBox.getChildren.clear()
  //    analyserView.graphicBox.getChildren.add(new ChartView().setBarChart(getStartingLetter().toList))
  //  }

  //    def setStartingLetterSort(languageString: String): Unit =
  //    {
  //      logger.info("sortbutton pressed")
  //      logger.info(analyserView.languageString)
  //      analyserView.graphicBox.getChildren.clear()
  //      analyserView.graphicBox.getChildren.add(new ChartView().setBarChart(analyserModel.getStartingLetterResult("resources/languagetxtfiles/test_dutch.txt", languageMgr.setAlphabet(languageString)).sortWith(_._2 > _._2)))
  //    }

  def setWordsEndingWith(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.clear()
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChart(analyserModel.getEndingWithLetterResult("resources/languagetxtfiles/test_dutch.txt", languageMgr.setAlphabet(languageString)).toList))
  }

  def setTotalFrequency(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.clear()
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChart(analyserModel.getTotalFrequencyOfEveryLetter("resources/languagetxtfiles/test_dutch.txt", languageMgr.setAlphabet(languageString)).toList))
  }

  def setVowelChart(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.clear()
    analyserView
      .graphicBox
      .getChildren
      .add(new ChartView().setPieChart(
        analyserModel.getFrequencyVowelsInDouble("resources/languagetxtfiles/test_dutch.txt", languageMgr.setVowels(languageString))))
  }

  def setPopularStartingBigrams(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.clear()
    analyserView
      .graphicBox
      .getChildren
      .add(new ChartView().setBarChartWithString(
        analyserModel
          .getPopularStartingBigrams(
            "resources/languagetxtfiles/test_dutch.txt", ngramMgr.toBigrams(languageMgr.setAlphabet(languageString)).toList.flatten).sortWith(_._2 > _._2).take(25)))
  }

  def setPopularEndingBigrams(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.clear()
    analyserView
      .graphicBox
      .getChildren
      .add(new ChartView().setBarChartWithString(
        analyserModel
          .getPopularEndingBigrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toBigrams(languageMgr.setAlphabet(languageString)).toList.flatten).sortWith(_._2 > _._2).take(25)))
  }

  def setMostPopularBigrams(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.clear()
    analyserView
      .graphicBox
      .getChildren.add(new ChartView().setBarChartWithInt(
      analyserModel.getMostPopularBigrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toBigrams(languageMgr.setAlphabet(languageString)).toList.flatten).sortWith(_._2 > _._2).take(25)))
  }

  def setMostPopularTrigrams(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.clear()
    analyserView
      .graphicBox.getChildren
      .add(new ChartView().setBarChartWithInt(
        analyserModel.getMostPopularTrigrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toTrigrams(languageMgr.setAlphabet(languageString)).flatten.toList.flatten).sortWith(_._2 > _._2).take(25)))
  }

  def setMostPopularSkipgrams(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.clear()
    analyserView
      .graphicBox.getChildren
      .add(new ChartView().setBarChartWithInt(
        analyserModel.getMostPopularSkipgrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toSkipgrams(languageMgr.setAlphabet(languageString)).toList.flatten).sortWith(_._2 > _._2).take(25)))
  }

  def skipVsBigram(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.clear()
    analyserView
      .graphicBox.getChildren
      .add(new ChartView().setBarChartWithInt(
        analyserModel.getMostPopularSkipgrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toSkipgrams(languageMgr.setAlphabet(languageString)).toList.flatten)))
    analyserView
      .graphicBox.getChildren
      .add(new ChartView().setBarChartWithInt(
        analyserModel.getMostPopularBigrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toBigrams(languageMgr.setAlphabet(languageString)).toList.flatten)))
  }

  //backbutton
  analyserView.backButton.setOnAction(_ =>
  {
    val mainMenu = new MainMenuView
    val mainPresenter = new MainMenuPresenter(mainMenu)
    analyserView.getScene.setRoot(mainMenu)
  })
}
