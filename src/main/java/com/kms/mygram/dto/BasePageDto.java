package com.kms.mygram.dto;

import com.kms.mygram.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BasePageDto {

    private String current;
    private User currentUser;
}
