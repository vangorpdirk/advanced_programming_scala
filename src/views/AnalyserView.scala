package views

import java.util.logging.Logger

import javafx.geometry.Insets
import javafx.scene.control.Button
import javafx.scene.layout.{GridPane, HBox, VBox}

class AnalyserView(language: String) extends GridPane
{
  val logger: Logger = Logger.getLogger(getClass.getName)
  val languageString: String = language.toLowerCase()
  val backButton: Button = new Button("<--")
  val analyserButtons: List[Button] = List(new Button("1"), new Button("2"), new Button("3"),
    new Button("4"), new Button("5"), new Button("6"),
    new Button("7"), new Button("8"), new Button("9"))
  val leftBox: VBox = new VBox(10)
  val graphicBox: HBox = new HBox(10)

  def setButtons(): Unit =
  {
    backButton.setPrefSize(350, 75)
    analyserButtons.foreach(_.setPrefSize(350, 75))
  }

  def setPage(/*chart: ChartView*/): Unit =
  {
    leftBox.getChildren.add(backButton)
    analyserButtons.foreach(leftBox.getChildren.add(_))

    //    graphicBox.getChildren.add(chart.setBarChart())

    add(leftBox, 0, 0, 1, 10)
    //    add(graphicBox, 1, 0, 1, 10)

    setVgap(10)
    setHgap(10)
    setPadding(new Insets(10))
  }

  //style
  setPrefSize(800, 600)
  setButtons()
  setPage()
}
