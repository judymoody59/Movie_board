package movieboard.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import movieboard.common.FileUtils;
import movieboard.dto.MovieDto;
import movieboard.dto.MovieFileDto;
import movieboard.mapper.MovieMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MovieServiceImpl implements MovieService {
	@Autowired
    private MovieMapper movieMapper;
	
	@Autowired
    private FileUtils fileUtils;

	@Override
	public List<MovieDto> selectMovieList() {
		// TODO Auto-generated method stub
		return movieMapper.selectMovieList();
	}
	
	@Override
    public void insertMovie(MovieDto movieDto, MultipartHttpServletRequest request) {      
        movieMapper.insertMovie(movieDto);
        int movieId = movieDto.getMovieId();
        System.out.println("🔥 insertMovie에서 movieId 확인: " + movieId); 
        
        try {
            List<MovieFileDto> fileInfoList = fileUtils.parseFileInfo(movieId, request);
            
            // 첨부 파일 정보를 DB에 저장
            if (!CollectionUtils.isEmpty(fileInfoList)) {
                movieMapper.insertMovieFileList(fileInfoList);
            }


        } catch(Exception e) {
        	log.error(e.getMessage());
            
        }


    }
	
	@Override
    public MovieDto selectMovieDetail(int movieId) {
		
		MovieDto movieDto = movieMapper.selectMovieDetail(movieId);
        List<MovieFileDto> movieFileInfoList = movieMapper.selectMovieFileList(movieId);
        movieDto.setFileInfoList(movieFileInfoList);
        
        return movieDto;

    }
	
	@Override
    public void updateMovie(MovieDto movieDto) {
        movieMapper.updateMovie(movieDto);        
    }

    @Override
    public void deleteMovie(int movieId) {
        MovieDto movieDto = new MovieDto(); 
        movieDto.setMovieId(movieId);
        movieMapper.deleteMovie(movieDto);        
    }
    
    @Override
    public MovieFileDto selectMovieFileInfo(int idx, int movieId) {
        return movieMapper.selectMovieFileInfo(idx, movieId);
    }

    
    




}
