package presenters

import java.util.logging.Logger

import model.AnalyserModel
import utilities.NgramManager
import views.charts.ChartView
import views.{AnalyserView, MainMenuView}

/**
 * TODO: number 7 and 8 --check assignment
 * TODO: when ready, replace test_dutch with setLanguage(languageString)
 */
class AnalyserPresenter(analyserView: AnalyserView)
{
  val logger: Logger = Logger.getLogger(getClass.getName)
  val analyserModel = new AnalyserModel()
  val ngramMgr = new NgramManager()

  analyserView.analyserButtons.foreach(b => b.setOnAction(_ =>
  {
    logger.info(b.getText)
    logger.info(analyserView.languageString)
    val buttonChoice = b.getText

    buttonChoice match
    {
      case "1" => setWordsStartingWith(analyserView.languageString); b.setDisable(true);
      case "2" => setWordsEndingWith(analyserView.languageString); b.setDisable(true);
      case "3" => setTotalFrequency(analyserView.languageString); b.setDisable(true);
      case "4" => setVowelChart(analyserView.languageString); b.setDisable(true);
      case "5" => setPopularStartingBigrams(analyserView.languageString); b.setDisable(true);
      case "6" => setPopularEndingBigrams(analyserView.languageString); b.setDisable(true);
    }
  }))

  def setWordsStartingWith(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChart(setAlphabet(languageString), analyserModel.getStartingLetterResult(setLanguage(languageString), setAlphabet(languageString))))
    analyserView.add(analyserView.graphicBox, 1, 0, 1, 10)
  }

  def setWordsEndingWith(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChart(setAlphabet(languageString), analyserModel.getEndingWithLetterResult(setLanguage(languageString), setAlphabet(languageString))))
    analyserView.add(analyserView.graphicBox, 1, 0, 1, 10)
  }

  def setTotalFrequency(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChartWithTuples(analyserModel.getTotalFrequencyOfEveryLetterInListOfTuples(setLanguage(languageString), setAlphabet(languageString))))
    analyserView.add(analyserView.graphicBox, 1, 0, 1, 10)
  }

  def setVowelChart(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.add(new ChartView().setPieChart(analyserModel.getFrequencyVowelsInDouble(setLanguage(languageString), setVowels(languageString))))
    analyserView.add(analyserView.graphicBox, 1, 0, 1, 10)
  }

  def setPopularStartingBigrams(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChartWithTuplesString(analyserModel.getPopularStartingBigrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toBigrams(setAlphabet(languageString)))))
    analyserView.add(analyserView.graphicBox, 1, 0, 1, 10)
  }

  def setPopularEndingBigrams(languageString: String): Unit =
  {
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChartWithTuplesString(analyserModel.getPopularEndingBigrams("resources/languagetxtfiles/test_dutch.txt", ngramMgr.toBigrams(setAlphabet(languageString)))))
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

  //move to utilities
  def setLanguage(languageString: String): String =
  {
    val language = languageString

    language match
    {
      case "dutch" => "resources/languagetxtfiles/Alldata Dutch.txt"
      case "danish" => "resources/languagetxtfiles/Alldata Danish.txt"
      case "finnish" => "resources/languagetxtfiles/Alldat Finnish.txt"
      case "english" => "resources/languagetxtfiles/Alldata English.txt"
      case "german" => "resources/languagetxtfiles/All data German.txt"
      case "portuguese" => "resources/languagetxtfiles/All data Portuguese.txt"
      case "italian" => "resources/languagetxtfiles/All data Italian.txt"
      case "spanish" => "resources/languagetxtfiles/All data Spanish.txt"
    }
  }

  def setAlphabet(languageString: String): String =
  {
    val alphabet = "alphabet" + languageString

    alphabet match
    {
      case "alphabetdutch" => "abcdefghijklmnopqrstuvwxyz"
      case "alphabetdanish" => "abcdefghijklmnopqrstuvwxyzæåø"
      case "alphabetfinnish" => "abcdefghijklmnopqrstuvwxyzäö"
      case "alphabetenglish" => "abcdefghijklmnopqrstuvwxyz"
      case "alphabetgerman" => "abcdefghijklmnopqrstuvwxyzäöüß"
      case "alphabetportuguese" => "abcdefghijklmnopqrstuvwxyz"
      case "alphabetitalian" => "abcdefghilmnopqrstuvz"
      case "alphabetspanish" => "abcdefghijklmnñopqrstuvwxyz"
    }
  }

  //vowels
  def setVowels(languageString: String): String =
  {
    val alphabet = "alphabet" + languageString

    alphabet match
    {
      case "alphabetdutch" => "aeuio"
      case "alphabetdanish" => "aeiouæåø"
      case "alphabetfinnish" => "aeiouäö"
      case "alphabetenglish" => "aeiou"
      case "alphabetgerman" => "aeiouäöü"
      case "alphabetportuguese" => "aeiou"
      case "alphabetitalian" => "aeiou"
      case "alphabetspanish" => "aeiou"
    }
  }
}
