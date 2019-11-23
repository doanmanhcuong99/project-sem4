package com.project.cms.service;

import com.project.cms.entity.Category;
import com.project.cms.entity.Chapter;
import com.project.cms.entity.Story;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

public interface ChapterService {
    Page<Chapter> getAllChapter();

    Page<Chapter> search(String hh);

    Chapter findById(Long chapId);

    Long create(Long storyId, Chapter chapterDetails);

    void update(Long chapId, Chapter chapterDetails);

    void delete(Long chapId);

    void openChap(Long chapId);

    void closeChap(Long chapId);

    Page<Chapter> getAllChapterByStory(Long storyId);

}
