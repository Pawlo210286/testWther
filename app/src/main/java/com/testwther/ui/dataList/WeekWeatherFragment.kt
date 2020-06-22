package com.testwther.ui.dataList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.testwther.R
import com.testwther.ui.data.CityTab
import com.testwther.ui.data.WeatherUiEntity
import com.testwther.ui.dataList.common.DaysWeatherListAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinTrigger
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class WeekWeatherFragment : Fragment(), KodeinAware {

    override val kodeinTrigger = KodeinTrigger()
    private lateinit var cityTab: CityTab

    private val _parentKodein: Kodein by closestKodein()
    override val kodein: Kodein = Kodein.lazy {
        extend(_parentKodein, true)

        bind<ViewModelProvider.Factory>(tag = WeekWeatherFragment::class.java) with singleton {
            ViewModelFactory(dataSource = instance(tag = cityTab), cityTab = cityTab)
        }
        bind<WeekWeatherViewModel>() with provider {
            ViewModelProviders.of(
                this@WeekWeatherFragment,
                instance(tag = WeekWeatherFragment::class.java)
            )[WeekWeatherViewModel::class.java]
        }
    }

    private val viewModel: WeekWeatherViewModel by instance()
    private val dailyWeatherAdapter by lazy { DaysWeatherListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cityTab = CityTab.values()[arguments?.getInt(ARG_TAB_ID) ?: 0]

        kodeinTrigger.trigger()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        observe(viewModel.dailyWeather, ::renderDailyWeather)
    }

    private fun initViews() {
        recyclerWeather.apply {
            adapter = dailyWeatherAdapter
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        }
    }

    private fun renderDailyWeather(dailyWeathers: List<WeatherUiEntity>) {
        dailyWeatherAdapter.submitList(dailyWeathers)
    }

    private inline fun <reified T, LD : LiveData<T>> Fragment.observe(
        liveData: LD,
        crossinline block: (T) -> Unit
    ) {
        liveData.observe(viewLifecycleOwner, Observer<T> { block(it) })
    }

    companion object {
        private const val ARG_TAB_ID = "arg_tab_id"

        fun newInstance(cityTab: CityTab): WeekWeatherFragment {
            return WeekWeatherFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_TAB_ID, cityTab.ordinal)
                }
            }
        }
    }
}
