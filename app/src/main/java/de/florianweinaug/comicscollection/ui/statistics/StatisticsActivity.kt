package de.florianweinaug.comicscollection.ui.statistics

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import de.florianweinaug.comicscollection.R
import de.florianweinaug.comicscollection.model.Statistics
import kotlinx.android.synthetic.main.activity_statistics.*
import kotlinx.android.synthetic.main.activity_statistics_content.*
import java.text.DecimalFormat

class StatisticsActivity : AppCompatActivity() {

    private lateinit var mViewModel: StatisticsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_statistics)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        createViewModel()
    }

    private fun createViewModel() {
        mViewModel = StatisticsViewModel.create(this)

        mViewModel.getStatistics()
                .observe(this, Observer { updateStatistics(it!!) })

        mViewModel.refresh()
    }

    private fun updateStatistics(statistics: Statistics) {
        comics_total.text = getString(R.string.number_format, statistics.comicsTotal)
        comics_concluded.text = getString(R.string.number_format, statistics.comicsConcluded)
        comics_read.text = getString(R.string.number_format, statistics.comicsRead)

        issues_total.text = getString(R.string.number_format, statistics.issuesTotal)
        issues_average.text = getString(R.string.decimal_format, statistics.issuesAverage)
        issues_read.text = getString(R.string.number_format, statistics.issuesRead)

        updateReadingProgressChart(statistics, reading_progress_chart)
        updatePublishersDistributionChart(statistics, publishers_distribution_chart)
    }

    private fun updateReadingProgressChart(statistics: Statistics, chart: HorizontalBarChart) {
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, statistics.comicsReadPercentage.toFloat()))
        entries.add(BarEntry(1f, statistics.issuesReadPercentage.toFloat()))

        val dataSet = BarDataSet(entries, null)
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

        val data = BarData(dataSet)
        data.setValueFormatter(PercentFormatter(DecimalFormat("0")))
        data.setValueTextSize(12f)

        val xValues = ArrayList<String>()
        xValues.add(getString(R.string.statistics_comics))
        xValues.add(getString(R.string.statistics_issues))

        val drawValueAboveBar = entries.any { it.y < 9f }
        chart.setDrawValueAboveBar(drawValueAboveBar)

        chart.axisLeft.isEnabled = false
        chart.axisLeft.axisMinimum = 0f
        chart.axisLeft.axisMaximum = 100f

        chart.axisRight.isEnabled = false

        chart.xAxis.valueFormatter = IndexAxisValueFormatter(xValues)
        chart.xAxis.textSize = 17f
        chart.xAxis.textColor = getColor(android.R.color.primary_text_light)
        chart.xAxis.granularity = 1f
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.setDrawAxisLine(false)
        chart.xAxis.setDrawGridLines(false)

        chart.data = data
        chart.description.isEnabled = false
        chart.legend.isEnabled = false

        chart.setNoDataText(getString(R.string.statistics_empty))
        chart.setPinchZoom(false)
        chart.isDoubleTapToZoomEnabled = false

        chart.invalidate()
    }

    private fun updatePublishersDistributionChart(statistics: Statistics, chart: PieChart) {
        val entries = ArrayList<PieEntry>()
        statistics.publisherCount.forEach({
            entries.add(PieEntry(it.count.toFloat(), it.name))
        })

        val dataSet = PieDataSet(entries, null)
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

        val data = PieData(dataSet)
        data.setValueFormatter(DefaultValueFormatter(0))
        data.setValueTextSize(12f)

        chart.data = data
        chart.description.isEnabled = false
        chart.legend.isEnabled = false

        chart.centerText = getString(R.string.statistics_publishers)
        chart.setCenterTextSize(16f)
        chart.setCenterTextColor(getColor(R.color.colorPrimary))

        chart.setNoDataText(getString(R.string.statistics_empty))

        chart.invalidate()
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    NavUtils.navigateUpFromSameTask(this)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}