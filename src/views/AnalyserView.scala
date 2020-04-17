package views

import java.util.logging.Logger

import javafx.geometry.{Insets, Pos}
import javafx.scene.control.Button
import javafx.scene.layout.{GridPane, HBox, VBox}

class AnalyserView(language: String) extends GridPane
{
  val logger: Logger = Logger.getLogger(getClass.getName)
  val languageString: String = language.toLowerCase()
  val backButton: Button = new Button("<--")
  val analyserButtons: List[Button] = List(new Button("Starts with"), new Button("Ends With"), new Button("Letter Frequency"),
    new Button("Vowels/Consonants"), new Button("Starting Bigrams"), new Button("Ending Bigrams"),
    new Button("Popular Bigrams"), new Button("Popular Trigrams"), new Button("Popular Skipgrams")
  , new Button("10"))
  val leftBox: VBox = new VBox(10)
  val graphicBox: HBox = new HBox(10)

  def setButtons(): Unit =
  {
    backButton.setPrefSize(350, 75)
    analyserButtons.foreach(_.setPrefSize(350, 75))
  }

  def setPage(): Unit =
  {
    leftBox.getChildren.add(backButton)
    analyserButtons.foreach(leftBox.getChildren.add(_))
    leftBox.setAlignment(Pos.CENTER)
    add(leftBox, 0, 0, 1, 10)

    setVgap(10)
    setHgap(10)
    setPadding(new Insets(10))
  }

  //style
  setPrefSize(800, 600)
  setButtons()
  setPage()
}
