package com.kms.mygram.dto;

import com.kms.mygram.domain.Post;
import com.kms.mygram.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomePageDto {

    private List<Post> postList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
}
