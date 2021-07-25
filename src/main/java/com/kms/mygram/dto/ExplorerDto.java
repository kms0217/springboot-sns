package com.kms.mygram.dto;

import com.kms.mygram.domain.Story;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExplorerDto extends BasePageDto{

    List<Story> storyList;
}
