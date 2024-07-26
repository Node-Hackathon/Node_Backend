package com.example.nodebackend.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Center {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String center_name;

    @Column(nullable = false)
    private String center_type;

    @Column(nullable = false)
    private String center_address;

    @Column(nullable = false)
    private double latitude;

    @Column( nullable = false)
    private double longitude;

    @Column( nullable = false)
    private String center_phone_num;
}
