package views.charts

import javafx.collections.FXCollections
import javafx.scene.chart.{BarChart, CategoryAxis, NumberAxis, PieChart, XYChart}

/**
 * TODO: Pie Chart implementeren.
 */

class ChartView
{
  def setBarChart(charArray: String, numberList: List[Number]): BarChart[String, Number] =
  {
    val xAxis = new CategoryAxis
    val yAxis = new NumberAxis()
    val barchart: BarChart[String, Number] = new BarChart[String, Number](xAxis, yAxis)
    xAxis.setLabel("Letter from alfabet")
    yAxis.setLabel("Frequency")
    barchart.setPrefSize(1480, 1000)
    barchart.setBarGap(0)
    barchart.setCategoryGap(1.0)

    val dataseries: XYChart.Series[String, Number] = new XYChart.Series()

    for (i <- 0 until charArray.length)
    {
      dataseries.getData().add(new XYChart.Data(charArray(i).toString, numberList(i)))
    }

    barchart.getData().add(dataseries)
    barchart.setLegendVisible(false)

    barchart
  }

  def setPieChart(): PieChart =
  {
    val pieData = FXCollections.observableArrayList(
      new PieChart.Data("Dirk", 40),
      new PieChart.Data("Jos", 30),
      new PieChart.Data("Lou", 30)
    )
    val pie: PieChart = new PieChart(pieData)

    pie.setLegendVisible(false)
    pie.setLabelLineLength(10)
    pie.setPrefSize(800, 600)

    pie
  }
}
