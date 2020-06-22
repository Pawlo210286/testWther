package com.testwther.ui.common

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.testwther.ui.data.CityTab
import com.testwther.ui.dataList.WeekWeatherFragment

class SectionsPagerAdapter(
    private val context: Context,
    fm: FragmentManager
) : FragmentStatePagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> WeekWeatherFragment.newInstance(CityTab.PETER)
            else -> WeekWeatherFragment.newInstance(CityTab.MOSCOW)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(CityTab.values()[position].title)
    }

    override fun getCount(): Int = CITIES_COUNT

    companion object {
        private const val CITIES_COUNT = 2
    }
}