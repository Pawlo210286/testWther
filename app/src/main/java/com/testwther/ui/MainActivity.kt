package com.testwther.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.testwther.R
import com.testwther.ui.common.SectionsPagerAdapter
import com.testwther.ui.data.CityTab
import com.testwther.ui.data.WeatherUiEntity
import com.testwther.ui.map.MapActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinTrigger
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MainActivity : AppCompatActivity(), KodeinAware {

    private val _parentKodein by closestKodein()
    override val kodein: Kodein by retainedKodein {
        extend(_parentKodein)

        bind<ViewModelProvider.Factory>(tag = MainActivity::class.java) with singleton {
            MainFactory(
                moscowDataSource = instance(tag = CityTab.MOSCOW),
                peterDataSource = instance(tag = CityTab.PETER)
            )
        }
        bind<MainViewModel>() with provider {
            ViewModelProviders.of(
                this@MainActivity,
                instance(tag = MainActivity::class.java)
            )[MainViewModel::class.java]
        }
    }
    override val kodeinTrigger = KodeinTrigger()

    private val viewModel: MainViewModel by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        kodeinTrigger.trigger()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(
            this,
            supportFragmentManager
        )
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {} //do nothing

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            } //do nothing

            override fun onPageSelected(position: Int) = viewModel.onPageSelected(position)
        })

        btnMap.setOnClickListener {
            Intent(this, MapActivity::class.java).let {
                startActivity(it)
            }
        }

        observe(viewModel.currentWeather, ::renderCurrentWeather)
    }

    private fun renderCurrentWeather(weather: WeatherUiEntity) {
        img_weather.setImageResource(weather.iconResId)
        tv_temperature.text = weather.temperature
        tv_weather_desc.text = weather.summary
    }

    private inline fun <reified T, LD : LiveData<T>> AppCompatActivity.observe(
        liveData: LD,
        crossinline block: (T) -> Unit
    ) {
        liveData.observe(this, Observer<T> { block(it) })
    }
}