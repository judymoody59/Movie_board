package movieboard.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import movieboard.dto.MovieDto;
import movieboard.dto.MovieFileDto;


public interface MovieService {
	List<MovieDto> selectMovieList();

	void insertMovie(MovieDto movieDto, MultipartHttpServletRequest request);

	MovieDto selectMovieDetail(int movieId);

	void updateMovie(MovieDto movieDto);

	void deleteMovie(int movieId);
	MovieFileDto selectMovieFileInfo(int idx, int movieId);


}
