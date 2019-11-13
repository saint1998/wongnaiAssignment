package com.wongnai.interview.movie.external;

import com.fasterxml.jackson.core.JsonParseException;

import java.io.IOException;

public interface MovieDataService {
	MoviesResponse fetchAll() throws JsonParseException, IOException;
}
