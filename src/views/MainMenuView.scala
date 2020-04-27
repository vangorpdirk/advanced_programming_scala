package views

import java.util.logging.Logger

import javafx.geometry.{Insets, Pos}
import javafx.scene.control.{Button, Label}
import javafx.scene.layout.{GridPane, HBox, VBox}

class MainMenuView extends GridPane
{
  val logger: Logger = Logger.getLogger(getClass.getName)
  val buttonList = List(new Button("Dutch"), new Button("English"), new Button("Finnish"), new Button("German"), new Button("Italian"), new Button("Danish"), new Button("Portugese"), new Button("Spanish"))
  val helloVisitor: Label = new Label("Welcome to the language analyser")
  val topBox: HBox = new HBox(10)
  val centerBox: VBox = new VBox(10)
  val centerBox2: VBox = new VBox(10)

  def setButtons(): Unit =
  {
    buttonList.foreach(_.setPrefSize(800, 150))
  }

  def setPage(): Unit =
  {
    topBox.getChildren.add(helloVisitor)
    topBox.setAlignment(Pos.TOP_LEFT)
    add(topBox, 0, 0)

    for (number <- 1 to 4)
    {
      centerBox.getChildren.add(buttonList(number - 1))
    }
    for (number <- 5 to 8)
    {
      centerBox2.getChildren.add(buttonList(number - 1))
    }
    centerBox.setAlignment(Pos.BOTTOM_CENTER)
    centerBox2.setAlignment(Pos.BOTTOM_CENTER)
    add(centerBox, 0, 2)
    add(centerBox2, 1, 2)

    this.setAlignment(Pos.CENTER)
    setHgap(20)
    setVgap(20)
    setPadding(new Insets(10))
  }

  setPrefSize(800, 600)
  setButtons()
  setPage()
}
