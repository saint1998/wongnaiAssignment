package com.wongnai.interview.movie.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wongnai.interview.movie.external.MovieData;
import com.wongnai.interview.movie.external.MovieDataServiceImpl;
import com.wongnai.interview.movie.external.MoviesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieSearchService;
import com.wongnai.interview.movie.external.MovieDataService;

@Component("simpleMovieSearchService")
public class SimpleMovieSearchService implements MovieSearchService {
	@Autowired
	private MovieDataService movieDataService;

	@Override
	public List<Movie> search(String queryText) throws IOException {
		//TODO: Step 2 => Implement this method by using data from MovieDataService
		// All test in SimpleMovieSearchServiceIntegrationTest must pass.
		// Please do not change @Component annotation on this class
		List<Movie> result = new ArrayList<Movie>();
		MoviesResponse moviesResponse = movieDataService.fetchAll();

		if(queryText.contains(" ")){
			return result;
		}
		String regex = "\\b" + queryText + "\\b";

		Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);

		for (MovieData movie: moviesResponse)
		{
			String title = movie.getTitle();
			Matcher matcher = pattern.matcher(title);
			if(matcher.find()){
				Movie m = new Movie(title,movie.getCast());
				result.add(m);
			}
		}

		return result;
	}
}
