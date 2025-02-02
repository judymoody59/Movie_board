package movieboard.dto;

import java.util.List;

import lombok.Data;

@Data
public class MovieDto {
	private	int movieId;
	private String title;
	private String director;
	private String releaseDate;
	private String genre;
	private Double rating; 
	private String description;
	private String createdAt;
	private String updatedAt;
	
	// 첨부 파일 정보를 저장할 필드를 추가
    private List<MovieFileDto> fileInfoList;

	

}
