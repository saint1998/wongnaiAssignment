package com.wongnai.interview.movie;

import java.io.IOException;
import java.util.List;

public interface MovieSearchService {
	List<Movie> search(String queryText) throws IOException;
}
