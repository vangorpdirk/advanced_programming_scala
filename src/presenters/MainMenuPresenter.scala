package presenters

import java.util.logging.Logger

import views.{AnalyserView, MainMenuView}

class MainMenuPresenter(mainMenuView: MainMenuView)
{
  mainMenuView.buttonList.foreach(b => b.setOnAction(_ =>
  {
    val analyserView = new AnalyserView(b.getText)
    new AnalyserPresenter(analyserView)
    mainMenuView.getScene.setRoot(analyserView)
  }))
}
