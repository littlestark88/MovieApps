package com.example.movieapps.presentasion.favoritemovie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapps.databinding.FragmentFavoriteMovieBinding
import com.example.movieapps.domain.model.MovieList
import com.example.movieapps.presentasion.detail.DetailMovieActivity
import com.example.movieapps.presentasion.viewmodel.MovieViewModel
import com.example.movieapps.utils.Const
import com.example.movieapps.utils.Const.DATA_MOVIE
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteMovieFragment : Fragment() {

    private var _binding: FragmentFavoriteMovieBinding? = null
    private val binding get() = _binding
    private val movieViewModel: MovieViewModel by inject()
    private val movieFavoriteAdapter: MovieFavoriteAdapter by lazy {
        MovieFavoriteAdapter(
            mutableListOf(),
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

        _binding = FragmentFavoriteMovieBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieViewModel.getMovieNowPlayingFavorite().observe(viewLifecycleOwner) {
            Log.e("favoriteMovie", "onViewCreated: ${it.size}")
            movieFavoriteAdapter.setFavoriteMovie(it)
            movieFavoriteAdapter.notifyDataSetChanged()
        }
        showMovieFavoriteList()
    }

    private fun showMovieFavoriteList() {
        binding?.rvMovieFavorite.apply {
            this?.layoutManager = LinearLayoutManager(requireActivity())
            this?.setHasFixedSize(true)
            this?.adapter = movieFavoriteAdapter
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