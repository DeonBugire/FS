package ru.gb.android.homework3.presentation.product

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.gb.android.homework3.MarketSampleApp
import ru.gb.android.homework3.di.DaggerAppComponent
import ru.gb.android.homework3.marketsample.databinding.FragmentProductListBinding
import ru.gb.android.homework3.presentation.product.adapter.ProductsAdapter
import javax.inject.Inject

class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private val adapter = ProductsAdapter()
    @Inject
    lateinit var productListViewModelFactory : ProductListViewModelFactory

    private val viewModel: ProductListViewModel by viewModels {
        productListViewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MarketSampleApp.getInstance().appComponent
            .productComponentFactory()
            .create()
            .inject(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MarketSampleApp).appComponent
            .productComponentFactory()
            .create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }

        subscribeUI()
    }

    private fun subscribeUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        when {
                            state.isLoading -> showLoading()
                            state.hasError -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Error while loading data",
                                    Toast.LENGTH_SHORT
                                ).show()
                                viewModel.errorHasShown()
                            }
                            else -> showProductList(productListState = state.productListState)
                        }
                    }
                }
            }
        }
    }

    private fun showProductList(productListState: List<ProductState>) {
        binding.progress.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        adapter.submitList(productListState)
    }

    private fun showLoading() {
        binding.progress.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

