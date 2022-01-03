package com.kms.mygram.page.dto;

import com.kms.mygram.story.entity.Story;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ExplorerPageDto extends BasePageDto {

    List<Story> storyList;
}
