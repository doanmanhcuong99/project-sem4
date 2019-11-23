package com.project.cms.service;

import com.project.cms.entity.Status;
import com.project.cms.entity.Story;
import com.project.cms.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StoryService {
    Page<Story> getAll();

    Page<Story> search(String term);

    Story findById(Long storyId);

    Long create(Story storyDetails);

    void update(Long storyId, Story storyDetails);

    void delete(Long storyId);

    void openTask(Long storyId);

    void closeTask(Long storyId);

    Optional<Story> findForId(Long storyId);

    Story save(Story story);

    Page<Story> findByUserOrderByCreatedAtDesc(User user);

    Page<Story> findAllByOrderByCreatedAtDesc();

/*    Set<Story> getTasksByStatus(Status status);*/
}
