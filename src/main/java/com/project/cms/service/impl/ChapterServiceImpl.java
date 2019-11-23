package com.project.cms.service.impl;

import com.project.cms.entity.Chapter;
import com.project.cms.entity.Status;
import com.project.cms.entity.Story;
import com.project.cms.pagination.PageModel;
import com.project.cms.repository.ChapterRepository;
import com.project.cms.repository.StoryRepository;
import com.project.cms.service.ChapterService;
import com.project.cms.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterRepository chapterRepository;
    @Autowired
    StoryService storyService;
    @Autowired
    PageModel pageModel;

    @Override
    public Page<Chapter> getAllChapter() {
        pageModel.setSIZE(8);
        pageModel.initPageAndSize();
        return chapterRepository.findAll(PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE()));
    }

    @Override
    public Page<Chapter> search(String hh) {
        pageModel.setSIZE(5);
        pageModel.initPageAndSize();
        return chapterRepository.findByTitleContaining(hh, PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE()));
    }

    @Override
    public Page<Chapter> getAllChapterByStory(Long storyId) {
        pageModel.setSIZE(8);
        pageModel.initPageAndSize();
        return chapterRepository.findAllByStoryId(storyId, PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE()));
    }


    @Override
    public Chapter findById(Long chapId) {
        Optional<Chapter> chapterOptional = chapterRepository.findById(chapId);
        if (!chapterOptional.isPresent()) {
            throw new RuntimeException("Chapter Not Found!");
        }
        return chapterOptional.get();
    }

    @Override
    public Long create(Long storyId, Chapter chapterDetails) {
        Story story = storyService.findById(storyId);
        chapterDetails.setStatusChap(Status.CLOSED);
        chapterDetails.setStory(story);
        chapterRepository.save(chapterDetails);
        return chapterDetails.getId();
    }

    @Override
    public void update(Long chapId, Chapter chapterDetails) {
        Chapter currentChapter = findById(chapId);
        currentChapter.setTitle(chapterDetails.getTitle());
        currentChapter.setContent(chapterDetails.getContent());
        chapterRepository.save(currentChapter);
    }

    @Override
    public void delete(Long chapId) {
        chapterRepository.deleteById(chapId);
    }

    @Override
    public void openChap(Long chapId) {
        Chapter chapter = findById(chapId);
        chapter.openChap();
        chapterRepository.save(chapter);
    }

    @Override
    public void closeChap(Long chapId) {
        Chapter chapter = findById(chapId);
        chapter.closeChap();
        chapterRepository.save(chapter);
    }


}
