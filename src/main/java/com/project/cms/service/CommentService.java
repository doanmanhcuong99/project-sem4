package com.project.cms.service;

import com.project.cms.entity.Comment;
import com.project.cms.entity.Story;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Comment save(Comment comment);

    Optional<Comment> findById(Long id);


}
