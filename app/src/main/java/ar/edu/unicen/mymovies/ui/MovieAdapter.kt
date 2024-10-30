package ar.edu.unicen.mymovies.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.edu.unicen.mymovies.databinding.ListItemMovieBinding
import ar.edu.unicen.mymovies.ddl.models.MovieHome
import com.bumptech.glide.Glide

class MovieAdapter(
    private val movies: List<MovieHome>,
    private val onUserClick: (MovieHome) -> Unit
): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemMovieBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.show(movie)
    }

    inner class MovieViewHolder(
        private val binding: ListItemMovieBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun show(movie: MovieHome) {
            val baseUrl = "https://image.tmdb.org/t/p/w500"
            val posterUrl = baseUrl + movie.poster

            binding.movieTitle.text = movie.title

            Glide.with(itemView.context)
                .load(posterUrl)
                .into(binding.moviePoster)

            binding.root.setOnClickListener {
                onUserClick(movie)
            }
        }
    }
}