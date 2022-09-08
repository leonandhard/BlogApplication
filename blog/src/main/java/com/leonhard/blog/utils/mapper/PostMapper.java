package com.leonhard.blog.utils.mapper;

import com.leonhard.blog.dtos.PostDto;
import com.leonhard.blog.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "comments", ignore = true)
    Post toPostEntity(PostDto dto);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "comments", source = "comments")
    PostDto toPostDto(Post post);

}
