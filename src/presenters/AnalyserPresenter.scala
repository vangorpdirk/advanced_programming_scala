package presenters

import model.AnalyserModel
import utilities.{LanguageManager, NgramManager}
import views.charts.ChartView
import views.{AnalyserView, MainMenuView}

/**
 * TODO: (GEEN VRAAG VOOR HERWIG) when ready, replace test_dutch with actual languageMgr.setLanguage(languageString)
 */
class AnalyserPresenter(analyserView: AnalyserView)
{
  val analyserModel = new AnalyserModel()
  val ngramMgr = new NgramManager()
  val languageMgr = new LanguageManager()

  //vars
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
      case "starts with" => setNgramList(analyserModel.getStartingLetterResult("resources/languagetxtfiles/test_dutch.txt", languageMgr.setAlphabet(languageString).toList))
      case "ends with" => setNgramList(analyserModel.getEndingWithLetterResult("resources/languagetxtfiles/test_dutch.txt", languageMgr.setAlphabet(languageString).toList))
      case "letter frequency" => setNgramList(analyserModel.getTotalFrequencyOfEveryLetter("resources/languagetxtfiles/test_dutch.txt", languageMgr.setAlphabet(languageString).toList))
      case "vowels/consonants" => setVowelChart(analyserView.languageString)
      case "starting bigrams" => setNgramList(analyserModel.getPopularStartingBigrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toBigrams(languageMgr.setAlphabet(languageString)).toList.flatten).sortWith(_._2 > _._2).take(25))
      case "ending bigrams" => setNgramList(analyserModel.getPopularEndingBigrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toBigrams(languageMgr.setAlphabet(languageString)).toList.flatten).sortWith(_._2 > _._2).take(25))
      case "popular bigrams" => setNgramList(analyserModel.getMostPopularNgrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toBigrams(languageMgr.setAlphabet(languageString)).toList.flatten).sortWith(_._2 > _._2).take(25))
      case "popular trigrams" => setNgramList(analyserModel.getMostPopularNgrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toTrigrams(languageMgr.setAlphabet(languageString)).flatten.toList.flatten).sortWith(_._2 > _._2).take(25))
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

//  def setLetterList(getFunction: List[(String, Double)]): Unit =
//  {
//    analyserView.graphicBox.getChildren.clear()
//    analyserView.graphicBox.getChildren.add(new ChartView().setBarChart(getFunction))
//    analyserView.sortButton.setOnAction(_ =>
//    {
//      if (analyserView.sortButton.getText.equals("SORT >"))
//      {
//        setLetterList(getFunction.sortWith(_._2 > _._2))
//        analyserView.sortButton.setText("SORT <")
//      }
//      else
//      {
//        setLetterList(getFunction.sortWith(_._2 < _._2))
//        analyserView.sortButton.setText("SORT >")
//      }
//    })
//  }

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
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChart(getFunction))
    analyserView.sortButton.setOnAction(_ =>
    {
      if (analyserView.sortButton.getText.equals("SORT >"))
      {
        setNgramList(getFunction.sortWith(_._2 > _._2))
        analyserView.sortButton.setText("SORT <")
      }
      else
      {
        setNgramList(getFunction.sortWith(_._2 < _._2))
        analyserView.sortButton.setText("SORT >")
      }
    })
  }

  def skipVsBigram(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.clear()
    analyserView
      .graphicBox.getChildren
      .add(new ChartView().setBarChartTwoDataSeries(analyserModel.compareBigramWithSkipGram(
        analyserModel.getMostPopularNgrams(languageString, ngramMgr.toBigrams("abcdefghijklmnopqrstuvwxyz").toList.flatten),
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
}
