package com.kms.mygram.dto.Page;

import com.kms.mygram.domain.Story;
import com.kms.mygram.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class HomePageDto extends BasePageDto{

    private List<Story> storyList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
}
