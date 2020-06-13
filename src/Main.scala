import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import presenters.MainMenuPresenter
import views.MainMenuView

/**
 * Dit is mijn taalanalyse-app. De opdracht was zeer interessant en Scala is absoluut de moeite om te kennen. Ik heb hier
 * en daar wat commentaar boven de klassen geschreven zodat u het wat kan volgen. Verder heb ik geprobeerd om zoveel mogelijk
 * functional te programmeren. Ook voor de structuur van mijn klassen heb ik geprobeerd kort en bondig te zijn.
 *
 * Veel verbeterplezier!
 *
 * @author Dirk Van Gorp
 *         dirk.vangorp@student.kdg.be
 * @version 1.0
 */

object Main
{
  def main(args: Array[String]): Unit =
  {
    Application.launch(classOf[Main], args: _*)
  }
}

class Main extends Application
{
  override def start(primaryStage: Stage): Unit =
  {
    val mainMenu = new MainMenuView()
    new MainMenuPresenter(mainMenu)
    val scene: Scene = new Scene(mainMenu)

    scene.getStylesheets.add(getClass.getResource("/css/style.css").toString)

    primaryStage.setTitle("NGram Analyser")
    primaryStage.setMaximized(true)
    primaryStage.setScene(scene)
    primaryStage.show()
  }
}
