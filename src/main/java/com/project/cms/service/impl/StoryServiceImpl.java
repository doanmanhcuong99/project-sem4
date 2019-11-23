package com.project.cms.service.impl;

import com.project.cms.entity.Status;
import com.project.cms.entity.Story;
import com.project.cms.entity.User;
import com.project.cms.pagination.PageModel;
import com.project.cms.repository.StoryRepository;
import com.project.cms.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StoryServiceImpl implements StoryService {
    @Autowired
    StoryRepository storyRepository;
    @Autowired
    PageModel pageModel;

    @Override
    public Page<Story> getAll() {
        pageModel.setSIZE(5);
        pageModel.initPageAndSize();
        return storyRepository.findAll(PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE()));
    }



    @Override
    public Page<Story> search(String term) {
        pageModel.setSIZE(5);
        pageModel.initPageAndSize();
        return storyRepository.findByTitleContaining(term, PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE()));
    }

    @Override
    public Story findById(Long storyId) {
        Optional<Story> storyOptional = storyRepository.findById(storyId);
        if (!storyOptional.isPresent()) {
            throw new RuntimeException("Story Not Found!");
        }
        return storyOptional.get();
    }

    @Override
    public Long create(Story storyDetails) {
        storyDetails.setCreatedAt(Calendar.getInstance().getTimeInMillis());
        storyDetails.setStatus(Status.CLOSED);
        storyRepository.save(storyDetails);
        return storyDetails.getId();
    }

    @Override
    public Story save(Story story) {
        return storyRepository.saveAndFlush(story);
    }

    @Override
    public Page<Story> findByUserOrderByCreatedAtDesc(User user) {
        pageModel.setSIZE(5);
        pageModel.initPageAndSize();
        return storyRepository.findByUserOrderByCreatedAtDesc(user, PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE()));
    }

    @Override
    public Page<Story> findAllByOrderByCreatedAtDesc() {
        pageModel.setSIZE(5);
        pageModel.initPageAndSize();
        return storyRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE()));
    }


    @Override
    public void update(Long storyId, Story storyDetails) {
        Story currentStory = findById(storyId);
        currentStory.setTitle(storyDetails.getTitle());
        currentStory.setDirector(storyDetails.getDirector());
        currentStory.setCategories(storyDetails.getCategories());
        currentStory.setDescription(storyDetails.getDescription());
        currentStory.setStatus(storyDetails.getStatus());
        storyRepository.save(currentStory);
    }

    @Override
    public void delete(Long storyId) {
        storyRepository.deleteById(storyId);
    }

    @Override
    public void openTask(Long storyId) {
        Story story = findById(storyId);
        story.openTask();
        storyRepository.save(story);
    }

    @Override
    public void closeTask(Long storyId) {
        Story story = findById(storyId);
        story.closeTask();
        storyRepository.save(story);
    }

    @Override
    public Optional<Story> findForId(Long storyId) {
        return storyRepository.findById(storyId);
    }
/*
    @Override
    public Page<Story> findByUserOrderByCreateDateDesc(User user) {
        pageModel.setSIZE(5);
        pageModel.initPageAndSize();
        return storyRepository.findByUserOrderByCreateDateDesc(user, PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE()));
    }

    @Override
    public Page<Story> findAllByOrderByCreateDateDesc() {
        pageModel.setSIZE(5);
        pageModel.initPageAndSize();
        return storyRepository.findAllByOrderByCreateDateDesc( PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE()));
    }*/

}
