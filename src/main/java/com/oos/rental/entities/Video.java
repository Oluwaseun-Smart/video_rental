package com.oos.rental.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oos.rental.models.VideoGenre;
import com.oos.rental.models.VideoType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "video")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Video implements Serializable {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "video_type")
    @Enumerated(EnumType.STRING)
    private VideoType videoType;

    @Column(name = "video_genre")
    @Enumerated(EnumType.STRING)
    private VideoGenre videoGenre;

    @Column(name = "year")
    private String year;

    @Column(name = "maximum_age")
    private Integer maximumAge;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "update_at")
    private LocalDate updateAt;


}
