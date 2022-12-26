package com.example.bonialbrochures

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bonialbrochures.data.local.model.BrochureEntity
import com.example.bonialbrochures.databinding.ActivityBrochureListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class BrochureListActivity : AppCompatActivity() {
    private val viewModel: BrochureListViewModel by viewModels()
    private lateinit var binding: ActivityBrochureListBinding
    private lateinit var brochureAdapter: BrochureAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrochureListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        brochureAdapter = BrochureAdapter()
        lifecycleScope.launchWhenResumed {
            viewModel.state.onEach(this@BrochureListActivity::renderState).launchIn(this)
        }

        setUpGridLayoutManager()

        binding.rvBrochures.apply {
            adapter = brochureAdapter
            layoutManager = gridLayoutManager
        }
    }

    private fun setUpGridLayoutManager() {
        gridLayoutManager =
            if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                GridLayoutManager(this@BrochureListActivity, 2)
            } else {
                GridLayoutManager(this@BrochureListActivity, 3)
            }
    }

    private fun renderState(state: BrochureListState) {
        binding.cpi.isVisible = state.isLoading

        if (state.error.isNotEmpty()) {
            Toast.makeText(this, state.error, Toast.LENGTH_LONG).show()
        } else if (state.brochureList.isNotEmpty()) {
            binding.rvBrochures.visibility = View.VISIBLE
            brochureAdapter.submitList(state.brochureList)
            setSpanSize(state.brochureList)
        }
    }

    private fun setSpanSize(brochureList: List<BrochureEntity>) {
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                //Use the current span count to set the span size for brochurePremium brochures
                var spanSize = gridLayoutManager.spanCount

                if (brochureList.size >= position) {
                    val currentItem = brochureList[position]
                    /**
                     * spanSize for brochurePremium type is the current span count
                     * if spanCount is (Landscape),
                     * brochurePremium will span 3 columns
                     * while brochure will always span 1 column
                     */
                    spanSize = if (currentItem.contentType == "brochurePremium") {
                        spanSize
                    } else {
                        1
                    }
                }

                return spanSize
            }
        }
    }
}
