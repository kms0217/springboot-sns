package com.kms.mygram.page.dto;

import com.kms.mygram.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BasePageDto {

    private String currentPage;
    private User currentUser;
}
