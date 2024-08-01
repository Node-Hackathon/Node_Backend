package com.example.nodebackend.data.dto.SignDto;

import com.sun.istack.NotNull;
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


    private Integer height;


    private Integer weight;


    private String address;

}
