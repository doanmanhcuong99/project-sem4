package com.project.cms.repository;

import com.project.cms.entity.Story;
import com.project.cms.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoryRepository extends JpaRepository<Story, Long> {
    Optional<Story> findById(Long storyId);

    Page<Story> findByTitleContaining(String term, Pageable pageable);

    Page<Story> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);

    Page<Story> findAllByOrderByCreatedAtDesc(Pageable pageable);


}
