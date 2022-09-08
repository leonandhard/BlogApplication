package com.leonhard.blog.utils.mapper;

import com.leonhard.blog.dtos.CommentDto;
import com.leonhard.blog.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "body", source = "body")
    @Mapping(target = "post", ignore = true)
    Comment commentDtoToComment(CommentDto dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "body", source = "body")
    CommentDto commentToCommentDto(Comment comment);
}
