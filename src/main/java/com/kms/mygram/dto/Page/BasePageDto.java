package com.kms.mygram.dto.Page;

import com.kms.mygram.domain.User;
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
