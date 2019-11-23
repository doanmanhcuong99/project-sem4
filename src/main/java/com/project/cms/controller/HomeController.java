package com.project.cms.controller;

import com.project.cms.entity.Story;
import com.project.cms.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @Autowired
    private StoryService storyService;

    @GetMapping("/home")
    public String home() {
        Page<Story> stories = storyService.getAll();
        return "/home";
    }
}
