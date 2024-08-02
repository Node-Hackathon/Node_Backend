package com.example.nodebackend.data.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Composition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPOSITION_ID")
    private Long id;

    @Column(name = "COMPOSITION_IMAGE_URL")
    private String composition_imageUrl;

    @Transient
    private MultipartFile composition_image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "CREATED_AT")
    private LocalDate createdAT;

}
