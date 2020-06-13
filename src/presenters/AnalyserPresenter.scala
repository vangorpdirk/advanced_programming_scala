package presenters

import model.AnalyserModel
import utilities.{LanguageManager, NgramManager}
import views.charts.ChartView
import views.{AnalyserView, MainMenuView}

/**
 * Hier komen UI en Model samen. Ik heb het aantal methodes van deze klasse in de loop van mijn studie zo efficiënt mogelijk
 * gemaakt. Eerst de twee buttonrijen, behalve back -en sortbutton. Maar dus de taal en analyseknoppen sturen de functie
 * matchanalysis aan waar ik dan de juiste methode aanspreek. Die methode gaat de analyse linken aan een chart.
 */
class AnalyserPresenter(analyserView: AnalyserView)
{
  val analyserModel = new AnalyserModel()
  val ngramMgr = new NgramManager()
  val languageMgr = new LanguageManager()

  //languagebuttons
  analyserView.languageButtons.foreach(but => but.setOnAction(_ =>
  {
    analyserView.languageButtons.foreach(w => w.setText(w.getText.toLowerCase))
    if (!analyserView.analyserButtons.map(_.getText.filter(_.isUpper)).filter(_.nonEmpty).mkString("").equals(""))
    {
      matchAnalysis(analyserView.analyserButtons.map(_.getText.filter(_.isUpper)).filter(_.nonEmpty).mkString("").toLowerCase(), but.getText.toLowerCase())
    }
    analyserView.setFlag(but.getText.toLowerCase)
    but.setText(but.getText.toUpperCase())
  }))

  //analyserbuttons
  analyserView.analyserButtons.foreach(b => b.setOnAction(_ =>
  {
    analyserView.analyserButtons.foreach(w => w.setText(w.getText.toLowerCase))
    if (analyserView.languageButtons.map(_.getText.filter(_.isUpper)).filter(_.nonEmpty).mkString("").equals(""))
    {
      matchAnalysis(b.getText.replace(" ", "").toLowerCase, analyserView.languageString)
    } else
    {
      matchAnalysis(b.getText.replace(" ", "").toLowerCase, analyserView.languageButtons.map(_.getText.filter(_.isUpper)).filter(_.nonEmpty).mkString("").toLowerCase())
    }
    b.setText(b.getText.toUpperCase)
  }))

  def matchAnalysis(buttonText: String, languageString: String): Unit =
  {
    buttonText match
    {
      case "startswith" => setNgramList(analyserModel.getStartingLetterResult(languageMgr.setLanguage(languageString), languageMgr.setAlphabet(languageString).toList))
      case "endswith" => setNgramList(analyserModel.getEndingWithLetterResult(languageMgr.setLanguage(languageString), languageMgr.setAlphabet(languageString).toList))
      case "letterfrequency" => setNgramList(analyserModel.getTotalFrequencyOfEveryLetter(languageMgr.setLanguage(languageString), languageMgr.setAlphabet(languageString).toList))
      case "vowelsvsconsonants" => setVowelChart(languageMgr.setLanguage(languageString), analyserView.languageString)
      case "startingbigrams" => setNgramList(analyserModel.getPopularStartingBigrams(languageMgr.setLanguage(languageString), ngramMgr.toBigrams(languageMgr.setAlphabet(languageString)).toList.flatten).sortWith(_._2 > _._2).take(25))
      case "endingbigrams" => setNgramList(analyserModel.getPopularEndingBigrams(languageMgr.setLanguage(languageString), ngramMgr.toBigrams(languageMgr.setAlphabet(languageString)).toList.flatten).sortWith(_._2 > _._2).take(25))
      case "popularbigrams" => setNgramList(analyserModel.getMostPopularNgrams(languageMgr.setLanguage(languageString), ngramMgr.toBigrams(languageMgr.setAlphabet(languageString)).toList.flatten).sortWith(_._2 > _._2).take(25))
      case "populartrigrams" => setNgramList(analyserModel.getMostPopularNgrams(languageMgr.setLanguage(languageString), ngramMgr.toTrigrams(languageMgr.setAlphabet(languageString)).flatten.toList.flatten).sortWith(_._2 > _._2).take(25))
      case "popularskipgrams" => setNgramList(analyserModel.getMostPopularSkipgrams(languageMgr.setLanguage(languageString), ngramMgr.toSkipgrams(languageMgr.setAlphabet(languageString)).toList.flatten).sortWith(_._2 > _._2).take(25))
      case "skipvsbigram" => skipVsBigram(languageMgr.setLanguage(languageString))
    }
  }

  def setVowelChart(languageFile: String, languageString: String): Unit =
  {
    analyserView.sortButton.setDisable(true)
    analyserView.graphicBox.getChildren.clear()
    analyserView
      .graphicBox
      .getChildren
      .add(new ChartView().setPieChart(
        analyserModel.getFrequencyVowelsInDouble(languageFile, languageMgr.setVowels(languageString))))
  }

  def setNgramList(getFunction: List[(String, Double)]): Unit =
  {
    analyserView.sortButton.setDisable(false)
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
    analyserView.sortButton.setDisable(true)
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
