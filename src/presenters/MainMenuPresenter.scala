package presenters

import java.util.logging.Logger

import views.{AnalyserView, MainMenuView}

class MainMenuPresenter(mainMenuView: MainMenuView)
{
  val logger: Logger = Logger.getLogger(getClass().getName)

  mainMenuView.buttonList.foreach(b => b.setOnAction(_ =>
  {
    logger.info(b.getText)
    val analyserView = new AnalyserView(b.getText)
    val analyserPresenter = new AnalyserPresenter(analyserView)
    mainMenuView.getScene.setRoot(analyserView)
  }))
}
