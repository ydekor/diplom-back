package com.ydekor.diplomback.web.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class AddTagDto {
    private Long noteId;
    private Long tagId;
}
