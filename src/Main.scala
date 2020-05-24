import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import presenters.MainMenuPresenter
import views.MainMenuView

/**
 * log 2405_update
 *
 * 1. AnalyserModel: changed method for vowels/consonants --> for-yield
 * 2. /resources/css/style.css: bigger font-size for ticks and different color for pie charts
 * 3. AnalyserView: added bottombox with flag + blanco chart
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
