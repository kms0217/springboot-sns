package com.kms.mygram.dto.Page;

import com.kms.mygram.domain.Story;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExplorerPageDto extends BasePageDto{

    List<Story> storyList;
}
