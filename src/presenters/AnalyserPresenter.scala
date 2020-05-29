package presenters

import java.util.logging.Logger

import model.AnalyserModel
import utilities.{LanguageManager, NgramManager}
import views.charts.ChartView
import views.{AnalyserView, MainMenuView}

/**
 * TODO: (GEEN VRAAG VOOR HERWIG) when ready, replace test_dutch with actual languageMgr.setLanguage(languageString)
 */
class AnalyserPresenter(analyserView: AnalyserView)
{
  val logger: Logger = Logger.getLogger(getClass.getName)
  val analyserModel = new AnalyserModel()
  val ngramMgr = new NgramManager()
  val languageMgr = new LanguageManager()

  var buttonCounter = 0
  var lastAnalysis = new String

  analyserView.analyserButtons.foreach(b => b.setOnAction(_ =>
  {
    lastAnalysis = b.getText.toLowerCase()
    matchAnalysis(lastAnalysis, analyserView.languageString)
  }))

  def matchAnalysis(buttonText: String, languageString: String): Unit =
  {
    buttonText match
    {
      case "starts with" => setLetterList(analyserModel.getStartingLetterResult("resources/languagetxtfiles/test_dutch.txt", languageMgr.setAlphabet(languageString)))
      case "ends with" => setLetterList(analyserModel.getEndingWithLetterResult("resources/languagetxtfiles/test_dutch.txt", languageMgr.setAlphabet(languageString)))
      case "letter frequency" => setLetterList(analyserModel.getTotalFrequencyOfEveryLetter("resources/languagetxtfiles/test_dutch.txt", languageMgr.setAlphabet(languageString)))
      case "vowels/consonants" => setVowelChart(analyserView.languageString)
      case "starting bigrams" => setNgramList(analyserModel.getPopularStartingBigrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toBigrams(languageMgr.setAlphabet(languageString)).toList.flatten).sortWith(_._2 > _._2).take(25))
      case "ending bigrams" => setNgramList(analyserModel.getPopularEndingBigrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toBigrams(languageMgr.setAlphabet(languageString)).toList.flatten).sortWith(_._2 > _._2).take(25))
      case "popular bigrams" => setNgramList(analyserModel.getMostPopularBigrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toBigrams(languageMgr.setAlphabet(languageString)).toList.flatten).sortWith(_._2 > _._2).take(25))
      case "popular trigrams" => setNgramList(analyserModel.getMostPopularTrigrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toTrigrams(languageMgr.setAlphabet(languageString)).flatten.toList.flatten).sortWith(_._2 > _._2).take(25))
      case "popular skipgrams" => setNgramList(analyserModel.getMostPopularSkipgrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toSkipgrams(languageMgr.setAlphabet(languageString)).toList.flatten).sortWith(_._2 > _._2).take(25))
      case "skipvsbigram" => skipVsBigram("resources/languagetxtfiles/test_dutch.txt")
    }
  }

  analyserView.languageButtons.foreach(b => b.setOnAction(_ =>
  {
    if (lastAnalysis == "")
    {
      val newView = new AnalyserView(b.getText.toLowerCase())
      new AnalyserPresenter(newView)
      analyserView.getScene.setRoot(newView)
    }
    else
    {
      matchAnalysis(lastAnalysis, b.getText.toLowerCase())
      analyserView.setFlag(b.getText.toLowerCase())
    }
  }))

  def setLetterList(getFunction: IndexedSeq[(Char, Double)]): Unit =
  {
    analyserView.graphicBox.getChildren.clear()
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChart(getFunction.toList))
    analyserView.sortButton.setOnAction(_ =>
    {
      buttonCounter += 1
      if (buttonCounter % 2 == 0)
      {
        setLetterList(getFunction.sortWith(_._2 > _._2))
      } else
      {
        setLetterList(getFunction.sortWith(_._2 < _._2))
      }
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
      buttonCounter += 1
      if (buttonCounter % 2 == 0)
      {
        setNgramList(getFunction.sortWith(_._2 > _._2))
      } else
      {
        setNgramList(getFunction.sortWith(_._2 < _._2))
      }
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
  }

  //backbutton
  analyserView.backButton.setOnAction(_ =>
  {
    val mainMenu = new MainMenuView
    new MainMenuPresenter(mainMenu)
    analyserView.getScene.setRoot(mainMenu)
  })

  //maybe for immutable buttoncounter
  //  def counter(originalList: List[Int]): List[Int] =
  //  {
  //    originalList :+ (originalList.last + 1)
  //  }
}
