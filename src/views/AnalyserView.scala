package views

import java.util.logging.Logger

import javafx.geometry.{Insets, Pos}
import javafx.scene.control.Button
import javafx.scene.layout.{GridPane, HBox, VBox}

/**
 * @param language
 */

class AnalyserView(language: String) extends GridPane
{
  val logger: Logger = Logger.getLogger(getClass.getName)
  val languageString: String = language.toLowerCase()
  val backButton: Button = new Button("<--")
  val analyserButtons: List[Button] = List(new Button("Starts with"), new Button("Ends With"), new Button("Letter Frequency"),
    new Button("Vowels/Consonants"), new Button("Starting Bigrams"), new Button("Ending Bigrams"),
    new Button("Popular Bigrams"), new Button("Popular Trigrams"), new Button("Popular Skipgrams")
    , new Button("SkipVsBigram"))
  val languageButtons: List[Button] = List(new Button("Dutch"), new Button("English"),
    new Button("Finnish"), new Button("German"), new Button("Italian"),
    new Button("Danish"), new Button("Portugese"), new Button("Spanish"))
  val sortButton = new Button("SORT")
  val leftBox: VBox = new VBox(10)
  val topBox: HBox = new HBox(10)
  val graphicBox: HBox = new HBox(10)

  def setButtons(): Unit =
  {
    backButton.setPrefSize(350, 75)
    analyserButtons.foreach(_.setPrefSize(350, 75))
    languageButtons.foreach(_.setId("languagebutton"))
    sortButton.setId("sortbutton")
  }

  def setPage(): Unit =
  {
    leftBox.getChildren.add(backButton)
    analyserButtons.foreach(leftBox.getChildren.add(_))
    languageButtons.foreach(topBox.getChildren.add(_))
    topBox.getChildren.add(sortButton)

    //add boxes
    add(leftBox, 0, 0, 1, 20)
    add(topBox, 1, 0, 1, 1)
    add(graphicBox, 1, 1, 1, 18)

    //for testing purpose
    topBox.setId("topbox")
    leftBox.setId("lefbox")
    graphicBox.setId("graphicbox")

    topBox.setAlignment(Pos.CENTER)
    leftBox.setAlignment(Pos.CENTER)
    graphicBox.setAlignment(Pos.CENTER)

    setVgap(10)
    setHgap(10)
    setPadding(new Insets(15))
  }

  //style
  setPrefSize(800, 600)
  setButtons()
  setPage()
}
