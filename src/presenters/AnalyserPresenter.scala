package presenters

import java.util.logging.Logger
import model.AnalyserModel
import views.charts.ChartView
import views.{AnalyserView, MainMenuView}

/**
 * TODO: De methodes setLanguage en setAlphabet kunnen in principe verhuizen naar utilities.
 *
 * @param analyserView
 */
class AnalyserPresenter(analyserView: AnalyserView)
{
  val logger: Logger = Logger.getLogger(getClass().getName)
  val analyserModel = new AnalyserModel()

  //addChart
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
    }



    //    if (b.getText.equals("3"))
    //    {
    //      analyserView.add(setChartThree(analyserView.languageString), 4, 1)
    //      b.setDisable(true)
    //    }
    //    if (b.getText.equals("4"))
    //    {
    //      logger.info("Not implemented yet, needs work")
    //      //      analyserView.add(setPieChart(analyserView.languageString),4,1)
    //      b.setDisable(true)
    //    }

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
    //hier kan je dan de tweede functie met de tuple gebruiken. Voor elke
    analyserView.graphicBox.getChildren.add(new ChartView().setBarChart(setAlphabet(languageString), analyserModel.getTotalFrequencyOfEveryLetter(setLanguage(languageString), setAlphabet(languageString))))
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
    logger.info(alphabet)

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
  //  val vowelsDutch: String = "aeuio"
  //  val consonantsDutch: String = "bcdfghjklmnpqrstvwxyz"

}
