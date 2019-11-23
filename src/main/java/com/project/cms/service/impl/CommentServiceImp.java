package com.project.cms.service.impl;

import com.project.cms.entity.Comment;
import com.project.cms.pagination.PageModel;
import com.project.cms.repository.CommentRepository;
import com.project.cms.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImp implements CommentService
{
    private final CommentRepository commentRepository;
    @Autowired
    PageModel pageModel;

    @Autowired
    public CommentServiceImp(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.saveAndFlush(comment);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }



}
