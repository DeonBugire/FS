package ru.gb.android.workshop2.presentation.list.promo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.gb.android.workshop2.ServiceLocator
import ru.gb.android.workshop2.marketsample.R
import ru.gb.android.workshop2.marketsample.databinding.FragmentPromoListBinding
import ru.gb.android.workshop2.presentation.list.promo.adapter.PromoAdapter

class PromoListFragment : Fragment() {

    private var _binding: FragmentPromoListBinding? = null
    private val binding get() = _binding!!

    private val adapter = PromoAdapter()

    private val viewModel: PromoListViewModel by viewModels {
        PromoListViewModelFactory(
            consumePromosUseCase = ServiceLocator.provideConsumePromosUseCase(),
            promoVOMapper = FeatureServiceLocator.providePromoVOMapper()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPromoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshPromos()
        }

        observeViewModel()
        viewModel.loadPromos()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is PromoListState.Loading -> {
                        binding.progress.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    }
                    is PromoListState.Loaded -> {
                        binding.progress.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        adapter.submitList(state.promoList)
                    }
                    is PromoListState.Error -> {
                        binding.progress.visibility = View.GONE
                        binding.recyclerView.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.error_wile_loading_data),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
