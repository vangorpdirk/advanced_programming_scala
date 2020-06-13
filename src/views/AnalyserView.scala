package views

import javafx.geometry.{Insets, Pos}
import javafx.scene.control.Button
import javafx.scene.image.{Image, ImageView}
import javafx.scene.layout.{GridPane, HBox, VBox}
import views.charts.ChartView

/**
 * @param language
 * --> to know which language is selected, you have to inject languagestring
 */

class AnalyserView(language: String) extends GridPane
{
  val languageString: String = language.toLowerCase()

  //buttons
  val backButton: Button = new Button("<--")
  val analyserButtons: List[Button] = List(new Button("starts with"), new Button("ends with"), new Button("letter frequency"),
    new Button("vowelsvsconsonants"), new Button("starting bigrams"), new Button("ending bigrams"),
    new Button("popular bigrams"), new Button("popular trigrams"), new Button("popular skipgrams")
    , new Button("skipvsbigram"))
  val languageButtons: List[Button] = List(new Button("dutch"), new Button("english"),
    new Button("finnish"), new Button("german"), new Button("italian"),
    new Button("danish"), new Button("portugese"), new Button("spanish"))
  val sortButton = new Button("SORT >")

  //layout
  val leftBox: VBox = new VBox(10)
  val topBox: HBox = new HBox(10)
  val graphicBox: HBox = new HBox(10)
  val bottomBox: HBox = new HBox(10)

  //blanco chart
  val chartView = new ChartView

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
    graphicBox.getChildren.add(chartView.setBlancoBarChart())

    //add boxes
    add(leftBox, 0, 0, 1, 10)
    add(topBox, 1, 0, 1, 1)
    add(graphicBox, 1, 1, 1, 1)
    add(bottomBox, 1, 2, 1, 1)

    //for testing purpose
    topBox.setId("topbox")
    leftBox.setId("lefbox")
    graphicBox.setId("graphicbox")

    topBox.setAlignment(Pos.CENTER)
    leftBox.setAlignment(Pos.CENTER)
    graphicBox.setAlignment(Pos.CENTER)
    bottomBox.setAlignment(Pos.BASELINE_RIGHT)

    setVgap(10)
    setHgap(10)
    setPadding(new Insets(15))
  }

  //style
  setPrefSize(800, 600)
  setButtons()
  setPage()
  setFlag(languageString)

  def setFlag(countryFlag: String): Unit =
  {
    bottomBox.getChildren.clear()
    //flag of country where language is spoken
    val flagImage = new ImageView(new Image("/images/" + countryFlag + "_flag.png"))
    flagImage.setFitHeight(75)
    flagImage.setFitWidth(100)
    bottomBox.getChildren.add(flagImage)
  }
}
