package movieboard.controller;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletResponse;
import movieboard.dto.MovieDto;
import movieboard.dto.MovieFileDto;
import movieboard.service.MovieService;

@Controller
public class MovieController {
	@Autowired
    private MovieService movieService;
	
	@GetMapping("/movie/openMovieList.do")
    public ModelAndView openMovieList() throws Exception {
        ModelAndView mv = new ModelAndView("/movie/movieList");
        
        List<MovieDto> list = movieService.selectMovieList();
        mv.addObject("list", list);
        
        return mv;
    }
	
	// 글쓰기 화면 요청을 처리하는 메서드
    @GetMapping("/movie/openMovieWrite.do")
    public String openMovieWrite() throws Exception {
        return "/movie/movieWrite";
    }
    
    // 글 저장 요청을 처리하는 메서드
    @PostMapping("/movie/insertMovie.do")
    public String insertBoard(MovieDto movieDto, MultipartHttpServletRequest request) throws Exception {
        movieService.insertMovie(movieDto, request);
        return "redirect:/movie/openMovieList.do";
    }
    
    // 상세 조회 요청을 처리하는 메서드
    // /movie/openMovieDetail.do?boardIdx=1234
    @GetMapping("/movie/openMovieDetail.do")
    public ModelAndView openMovieDetail(@RequestParam("movieId") int movieId) throws Exception {
        MovieDto movieDto = movieService.selectMovieDetail(movieId);
        
        ModelAndView mv = new ModelAndView("/movie/movieDetail");
        mv.addObject("movie", movieDto);
        return mv;
    }
    
    // 수정 요청을 처리할 메서드 
    @PostMapping("/movie/updateMovie.do")
    public String updateMovie(MovieDto movieDto) throws Exception {
        movieService.updateMovie(movieDto);
        return "redirect:/movie/openMovieList.do";
    }
    
    // 삭제 요청을 처리할 메서드
    @PostMapping("/movie/deleteMovie.do")
    public String deleteMovie(@RequestParam("movieId") int movieId) throws Exception {
        movieService.deleteMovie(movieId);
        return "redirect:/movie/openMovieList.do";
    }    
    
    // 파일 다운로드 요청을 처리하는 메서드 
    @GetMapping("/movie/downloadMovieFile.do")
    public void downloadMovieFile(@RequestParam("idx") int idx, @RequestParam("movieId") int movieId, HttpServletResponse response) throws Exception {
        // idx와 movieId가 일치하는 파일 정보를 조회
        MovieFileDto movieFileDto = movieService.selectMovieFileInfo(idx, movieId);
        if (ObjectUtils.isEmpty(movieFileDto)) {
            return;
        }
        
        // 원본 파일 저장 위치에서 파일을 읽어서 호출(요청)한 곳으로 파일을 응답으로 전달
        Path path = Paths.get(movieFileDto.getStoredFilePath());
        byte[] file = Files.readAllBytes(path);
        
        response.setContentType("application/octet-stream");
        
        response.setContentLength(file.length);
        response.setHeader("Content-Disposition",
        		"attachment; fileName=\"" + URLEncoder.encode(movieFileDto.getOriginalFileName(), "UTF-8") + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        
        response.getOutputStream().write(file);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }








	

	

}
