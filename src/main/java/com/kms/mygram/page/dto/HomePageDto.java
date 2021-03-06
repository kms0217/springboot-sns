package com.kms.mygram.page.dto;

import com.kms.mygram.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class HomePageDto extends BasePageDto {

    private List<User> userList = new ArrayList<>();
}
