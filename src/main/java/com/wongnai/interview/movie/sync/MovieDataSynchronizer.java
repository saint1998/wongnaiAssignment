package com.wongnai.interview.movie.sync;

import javax.transaction.Transactional;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.external.MovieData;
import com.wongnai.interview.movie.external.MoviesResponse;
import com.wongnai.interview.movie.search.InvertedIndexMovieSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.external.MovieDataService;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class MovieDataSynchronizer {
	@Autowired
	private MovieDataService movieDataService;

	@Autowired
	private MovieRepository movieRepository;

	@Transactional
	public void forceSync() throws IOException {
		//TODO: implement this to sync movie into repository
		MoviesResponse moviesResponse = movieDataService.fetchAll();
		Map<String, Set<Long>> invertedIndex = InvertedIndexMovieSearchService.getInvertedIndex();

		for(MovieData movie : moviesResponse){
			Movie m = new Movie(movie.getTitle(),movie.getCast());
			movieRepository.save(m);
			String[] title = movie.getTitle().toLowerCase().split(" ");
			for(String word :title){
				if(invertedIndex.containsKey(word)){
					invertedIndex.get(word).add(m.getId());
				}else {
					invertedIndex.put(word,new HashSet<>(Arrays.asList(m.getId())));
				}
			}
		}


	}
}
