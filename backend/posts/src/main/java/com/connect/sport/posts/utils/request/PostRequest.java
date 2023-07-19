package com.connect.sport.posts.utils.request;

import com.connect.sport.posts.enums.SportType;
import com.connect.sport.posts.enums.Status;
import com.connect.sport.posts.enums.Visibility;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostRequest {

    private String id;
    private String authorId;
    private String title;
    private String description;
    private String location;

    private LocalDateTime timestamp;

    private SportType sportType;
    private Status status;
    private Visibility visibility;

    private List<String> tags;
}
