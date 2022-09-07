package com.leonhard.blog.utils.mapper;

import com.leonhard.blog.dtos.PostDto;
import com.leonhard.blog.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
@Component
@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    Post toPostEntity(PostDto dto);

    PostDto toPostDto(Post post);
}
