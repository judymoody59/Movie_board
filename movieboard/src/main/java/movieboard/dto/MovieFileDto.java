package movieboard.dto;

import lombok.Data;

@Data
public class MovieFileDto {
	private int idx;
    private int movieId;
    private String originalFileName;
    private String storedFilePath;
    private String fileSize;

}
