package com.project.cms.repository;

import com.project.cms.entity.Chapter;
import com.project.cms.entity.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    Optional<Chapter> findById(Long chapId);

    Page<Chapter> findAllByStoryId(Long storyId, Pageable pageable);

    Page<Chapter> findByTitleContaining(String hh, Pageable pageable);

}
