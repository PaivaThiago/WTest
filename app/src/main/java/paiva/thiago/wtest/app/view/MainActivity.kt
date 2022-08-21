package paiva.thiago.wtest.app.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import paiva.thiago.wtest.app.view.adapter.PostalCodeAdapter
import paiva.thiago.wtest.app.view.adapter.PostalCodeComparator
import paiva.thiago.wtest.app.viewModel.PostalCodeViewModel
import paiva.thiago.wtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val vm: PostalCodeViewModel by viewModel()
    private val pagingDataAdapter = PostalCodeAdapter(PostalCodeComparator)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initStates()
        initViews()
    }

    private fun initStates() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.loading.collectLatest { isLoading ->
                    if (isLoading) {
                        showLoading()
                    } else {
                        hideLoading()
                        binding.searchBar.visibility = VISIBLE
                    }
                }
            }
        }
    }

    private fun initViews() {
        binding.postalCodes.adapter = pagingDataAdapter

        binding.searchQuery.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun onTextChanged(query: CharSequence, p1: Int, p2: Int, p3: Int) {
                lifecycleScope.launch { search(query.toString()) }
            }

            override fun afterTextChanged(p0: Editable) {
                // Do nothing
            }
        })

        binding.clearBtn.setOnClickListener {
            clearText()
        }
    }

    private fun showLoading() {
        binding.loadingBar.visibility = VISIBLE
    }

    private fun hideLoading() {
        binding.loadingBar.visibility = GONE
    }

    private fun showResults() {
        hideLoading()
        if (pagingDataAdapter.itemCount > 0) {
            binding.postalCodes.visibility = VISIBLE
            binding.noResults.visibility = GONE
        } else {
            binding.postalCodes.visibility = GONE
            binding.noResults.visibility = VISIBLE
        }
    }

    private suspend fun search(query: String) {
        showLoading()
        binding.clearBtn.visibility = VISIBLE
        vm.search(query).collectLatest { pagingData ->
            pagingDataAdapter.submitData(pagingData)
            showResults()
        }
    }

    private fun clearText() {
        binding.searchQuery.text = null
        binding.clearBtn.visibility = GONE
    }
}