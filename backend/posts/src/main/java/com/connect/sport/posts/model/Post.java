package com.connect.sport.posts.model;

import com.connect.sport.posts.enums.SportType;
import com.connect.sport.posts.enums.Status;
import com.connect.sport.posts.enums.Visibility;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Post {

    @Id
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
