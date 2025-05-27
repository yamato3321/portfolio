// EditProfileForm.java
package com.example.portfolio.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class EditProfileForm {

    @NotBlank(message = "名前は必須です")
    private String name;

    private String email;
    
    @NotBlank(message ="現在のパスワードを入力してください")
    private String currentPassword;
    
    @NotBlank(message = "新しいパスワードを入力してください")
    private String newPassword;
}
