package com.example.movieapps.presentasion.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapps.databinding.FragmentMovieBinding
import com.example.movieapps.domain.model.MovieList
import com.example.movieapps.presentasion.adapter.LoadingStateAdapter
import com.example.movieapps.presentasion.detail.DetailMovieActivity
import com.example.movieapps.presentasion.viewmodel.MovieViewModel
import com.example.movieapps.utils.Const.DATA_MOVIE
import org.koin.android.ext.android.inject

class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding
    private val movieViewModel: MovieViewModel by inject()
    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter(
            onClickListener = {
                intentToDetail(it)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showMovieRecycleList()
        movieViewModel.getMovieNowPlaying().observe(viewLifecycleOwner) {
            movieAdapter.submitData(lifecycle, it)
        }
    }

    private fun showMovieRecycleList() {
        binding?.rvMovie.apply {
            this?.layoutManager = LinearLayoutManager(requireActivity())
            this?.setHasFixedSize(true)
            this?.adapter = movieAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    movieAdapter.refresh()
                }
            )
        }
    }

    private fun intentToDetail(data: MovieList) {
        val intent = Intent(requireActivity(), DetailMovieActivity::class.java)
        intent.putExtra(DATA_MOVIE, data)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}