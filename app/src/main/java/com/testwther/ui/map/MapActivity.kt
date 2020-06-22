package com.testwther.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.MapFragment
import com.testwther.R
import com.testwther.ui.map.common.MarkerAdapter
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinTrigger
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MapActivity : AppCompatActivity(), KodeinAware {

    private val _parentKodein by closestKodein()
    override val kodein: Kodein by retainedKodein {
        extend(_parentKodein)

        bind() from provider {
            MarkerAdapter(
                layoutInflater = LayoutInflater.from(this@MapActivity),
                resourceReader = instance()
            )
        }

        bind<ViewModelProvider.Factory>(tag = MapViewModel::class.java) with singleton {
            MapFactory(
                permissionChecker = instance(),
                weatherUseCase = instance(),
                markerAdapter = instance()
            )
        }
        bind<MapViewModel>() with provider {
            ViewModelProviders.of(
                this@MapActivity,
                instance(tag = MapViewModel::class.java)
            )[MapViewModel::class.java]
        }
    }
    override val kodeinTrigger = KodeinTrigger()

    private val viewModel: MapViewModel by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        kodeinTrigger.trigger()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        (fragmentManager.findFragmentById(R.id.map) as? MapFragment)?.getMapAsync(
            viewModel
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            false
        }
    }
}