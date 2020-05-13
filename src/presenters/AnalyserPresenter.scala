package presenters

import java.util.logging.Logger

import model.AnalyserModel
import utilities.{LanguageManager, NgramManager}
import views.charts.ChartView
import views.{AnalyserView, MainMenuView}

/**
 * TODO: vowels/consonants??? nog aan te werken
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
    val buttonChoice = b.getText.toLowerCase

    buttonChoice match
    {
      case "starts with" => setLetterList(analyserModel.getStartingLetterResult("resources/languagetxtfiles/test_dutch.txt", languageMgr.setAlphabet(analyserView.languageString)))
      case "ends with" => setLetterList(analyserModel.getEndingWithLetterResult("resources/languagetxtfiles/test_dutch.txt", languageMgr.setAlphabet(analyserView.languageString)))
      case "letter frequency" => setLetterList(analyserModel.getTotalFrequencyOfEveryLetter("resources/languagetxtfiles/test_dutch.txt", languageMgr.setAlphabet(analyserView.languageString)))
      case "vowels/consonants" => setVowelChart(analyserView.languageString)
      case "starting bigrams" => setNgramList(analyserModel.getPopularStartingBigrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toBigrams(languageMgr.setAlphabet(analyserView.languageString)).toList.flatten).sortWith(_._2 > _._2).take(25))
      case "ending bigrams" => setNgramList(analyserModel.getPopularEndingBigrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toBigrams(languageMgr.setAlphabet(analyserView.languageString)).toList.flatten).sortWith(_._2 > _._2).take(25))
      case "popular bigrams" => setNgramList(analyserModel.getMostPopularBigrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toBigrams(languageMgr.setAlphabet(analyserView.languageString)).toList.flatten).sortWith(_._2 > _._2).take(25))
      case "popular trigrams" => setNgramList(analyserModel.getMostPopularTrigrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toTrigrams(languageMgr.setAlphabet(analyserView.languageString)).flatten.toList.flatten).sortWith(_._2 > _._2).take(25))
      case "popular skipgrams" => setNgramList(analyserModel.getMostPopularSkipgrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toSkipgrams(languageMgr.setAlphabet(analyserView.languageString)).toList.flatten).sortWith(_._2 > _._2).take(25))
      case "skipvsbigram" => skipVsBigram("resources/languagetxtfiles/test_dutch.txt")
    }
  }))

  analyserView.languageButtons.foreach(b => b.setOnAction(_ =>
  {
    val anotherButtonChoice = b.getText.toLowerCase

    anotherButtonChoice match
    {
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
    new AnalyserPresenter(newView)
    analyserView.getScene.setRoot(newView)
  }

  def setLetterList(getFunction: IndexedSeq[(Char, Double)]): Unit =
  {
    analyserView.graphicBox.getChildren.clear()
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChart(getFunction.toList))
    analyserView.sortButton.setOnAction(_ =>
    {
      setLetterList(getFunction.sortWith(_._2 > _._2))
    })
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

  def setNgramList(getFunction: List[(String, Double)]): Unit =
  {
    analyserView.graphicBox.getChildren.clear()
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChartWithString(getFunction))
    analyserView.sortButton.setOnAction(_ =>
    {
      setNgramList(getFunction.sortWith(_._2 < _._2))
    })
  }

  def skipVsBigram(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.clear()
    analyserView
      .graphicBox.getChildren
      .add(new ChartView().setBarChartTwoDataSeries(analyserModel.compareBigramWithSkipGram(
        analyserModel.getMostPopularBigrams(languageString, ngramMgr.toBigrams("abcdefghijklmnopqrstuvwxyz").toList.flatten),
        analyserModel.getMostPopularSkipgrams(languageString, ngramMgr.toSkipgrams("abcdefghijklmnopqrstuvwxyz").toList.flatten))
        .flatten.filter(_ != ())))
    //TODO: for testing purposes, remove when fully integrated
    println(analyserModel.compareBigramWithSkipGram(
      analyserModel.getMostPopularBigrams(languageString, ngramMgr.toBigrams("abcdefghijklmnopqrstuvwxyz").toList.flatten),
      analyserModel.getMostPopularSkipgrams(languageString, ngramMgr.toSkipgrams("abcdefghijklmnopqrstuvwxyz").toList.flatten))
      .flatten.filter(_ != ()))
  }

  //backbutton
  analyserView.backButton.setOnAction(_ =>
  {
    val mainMenu = new MainMenuView
    new MainMenuPresenter(mainMenu)
    analyserView.getScene.setRoot(mainMenu)
  })
}
