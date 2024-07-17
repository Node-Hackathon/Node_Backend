package com.example.nodebackend.data.dto.SignDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SignUpUserInfoDto {

    private String name;

    private String gender;

    private String birth;

    private int height;

    private int weight;

    private String address;

}
