package com.kisnahc.usedmarket.usedmarket.web.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SignUpForm {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 5, max = 20)
    private String nickname;

    @Length(min = 8, max = 40)
    private String password;

}
