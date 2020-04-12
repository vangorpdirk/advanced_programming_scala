import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import presenters.MainMenuPresenter
import views.MainMenuView

/**
 * log 1204_update_1:
 *  -> how implement bigrams? --> sorting???
 *  -> check other implementations
 *  -> maybe lookup testtxt
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
    val mainPresenter = new MainMenuPresenter(mainMenu)
    val scene: Scene = new Scene(mainMenu)

    scene.getStylesheets.add(getClass.getResource("/css/style.css").toString)

    primaryStage.setTitle("NGram Analyser")
    primaryStage.setMaximized(true)
    primaryStage.setScene(scene)
    primaryStage.show
  }
}
