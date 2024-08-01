package com.example.nodebackend.data.dto.SignDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;


@Getter
@Setter
public class SignUpGuardianInfoDto {

    private String guardian_name;

    private String guardian_phone_num;

    private String guardian_address;

}
