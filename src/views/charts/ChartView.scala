package views.charts

import java.util.logging.Logger
import javafx.collections.FXCollections
import javafx.scene.chart.{BarChart, CategoryAxis, NumberAxis, PieChart, XYChart}

class ChartView
{
  val logger: Logger = Logger.getLogger(getClass.getName)
  val xAxis = new CategoryAxis
  val yAxis = new NumberAxis()
  val barchart: BarChart[String, Number] = new BarChart[String, Number](xAxis, yAxis)

  def setBarChart(alphabetList: List[(Char, Double)]): BarChart[String, Number] =
  {
    xAxis.setLabel("nGram")
    yAxis.setLabel("Frequency")
    barchart.setPrefSize(1500, 2500)
    barchart.setBarGap(0)
    barchart.setCategoryGap(1.0)

    val dataseries: XYChart.Series[String, Number] = new XYChart.Series()

    for ((a, b) <- alphabetList)
    {
      dataseries.getData.add(new XYChart.Data(a.toString, b))
    }

    barchart.getData.add(dataseries)
    barchart.setLegendVisible(false)

    barchart
  }

  def setBarChartWithString(alphabetList: List[(String, Double)]): BarChart[String, Number] =
  {
    xAxis.setLabel("nGram")
    yAxis.setLabel("Frequency")
    barchart.setPrefSize(1500, 2500)
    barchart.setBarGap(0)
    barchart.setCategoryGap(1.0)

    val dataseries: XYChart.Series[String, Number] = new XYChart.Series()

    for ((a, b) <- alphabetList)
    {
      dataseries.getData.add(new XYChart.Data(a.toString, b))
    }

    barchart.getData.add(dataseries)
    barchart.setLegendVisible(false)

    barchart
  }

  def setBarChartWithInt(alphabetList: List[(String, Int)]): BarChart[String, Number] =
  {
    xAxis.setLabel("nGram")
    yAxis.setLabel("Frequency")
    barchart.setPrefSize(1500, 2500)
    barchart.setBarGap(0)
    barchart.setCategoryGap(1.0)

    val dataseries: XYChart.Series[String, Number] = new XYChart.Series()

    for ((a, b) <- alphabetList)
    {
      dataseries.getData.add(new XYChart.Data(a.toString, b))
    }

    barchart.getData.add(dataseries)
    barchart.setLegendVisible(false)

    barchart
  }

  def setPieChart(input: Double): PieChart =
  {
    val pieData = FXCollections.observableArrayList(
      new PieChart.Data("Vowels", input),
      new PieChart.Data("Consonants", 100 - input),
    )
    val pie: PieChart = new PieChart(pieData)

    pie.setLegendVisible(false)
    pie.setLabelLineLength(10)
    pie.setPrefSize(800, 600)

    pie
  }
}
