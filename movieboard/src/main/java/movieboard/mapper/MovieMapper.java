package movieboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import movieboard.dto.MovieDto;
import movieboard.dto.MovieFileDto;

@Mapper
public interface MovieMapper {
	List<MovieDto> selectMovieList();

	void insertMovie(MovieDto movieDto);
	MovieDto selectMovieDetail(int movieId);

	void updateMovie(MovieDto movieDto);

	void deleteMovie(MovieDto movieDto);

	void insertMovieFileList(List<MovieFileDto> fileInfoList);
	
	List<MovieFileDto> selectMovieFileList(int movieId);
	MovieFileDto selectMovieFileInfo(@Param("idx") int idx, @Param("movieId") int boardIdx);

}
