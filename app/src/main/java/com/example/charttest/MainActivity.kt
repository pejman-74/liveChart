package com.example.charttest

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var lineChart: LineChart
    private lateinit var dataSet: LineDataSet
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lineChart = findViewById(R.id.line_chart)
        dataSet = LineDataSet(mutableListOf(Entry(0f, 0f)), "")

        with(lineChart) {
            legend.isEnabled = false
            description.isEnabled = false
            setTouchEnabled(true)
            setScaleEnabled(true)
            isDragXEnabled = true
            isDragYEnabled = false
            lineChart.data = LineData(dataSet)
        }

        with(dataSet) {
            setDrawValues(true)
            mode = LineDataSet.Mode.CUBIC_BEZIER
            color = Color.RED
        }


    }


    private fun sendFakeValue() {
        var fakeY: Float
        lifecycleScope.launch {
            repeat((1..50).count()) {
                fakeY = (-10..10).random().toFloat()
                delay(200)
                val data = lineChart.lineData

                data.addEntry(Entry(it.toFloat(), fakeY), 0)
                data.notifyDataChanged()
                with(lineChart) {
                    notifyDataSetChanged()
                    setVisibleXRangeMaximum(5f)
                    invalidate()
                    moveViewToX(it.toFloat())
                }
            }
        }
    }


    fun fakeDataButton(v: View) {
        sendFakeValue()
    }
}