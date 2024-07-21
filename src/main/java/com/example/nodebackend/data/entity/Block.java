package com.example.nodebackend.data.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="BLOCK_COMBINATION_NAME")
    private String combination_name;

    @Column(name="BLOCK_COMBINATION_COUNT")
    private int count;

    @Column(name="BLOCK_ACCURACY")
    private String accuracy;

    @Column(name="BLOCK_IMAGE_URL")
    private String image_Url;

    @Transient // 데이터베이스에 저장하지 않음
    private MultipartFile blockImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="CREATED_AT")
    private LocalDateTime createdAt;


}
