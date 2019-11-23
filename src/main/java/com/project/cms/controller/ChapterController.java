package com.project.cms.controller;

import com.project.cms.entity.Chapter;
import com.project.cms.entity.Status;
import com.project.cms.service.ChapterService;
import com.project.cms.service.impl.FileUploadService;
import com.project.cms.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class ChapterController {
    @Autowired
    private StoryService storyService;
    @Autowired
    ChapterService chapterService;
    @Autowired
    FileUploadService fileUploadService;

    @GetMapping("/chapters")
    public String getAllChapter(Model model) {
        model.addAttribute("chapters", chapterService.getAllChapter());
        Set<Status> statusList = new HashSet<>();
        Status.stream().forEach(statusList::add);
        model.addAttribute("statusListChapter", statusList);
        return "chapters/list";
    }



    @RequestMapping(value = "/chapter/search", method = RequestMethod.GET)
    public String search(@RequestParam("hh") String hh, Model model) {
        if (StringUtils.isEmpty(hh)) {
            return "redirect:/home";
        }
        model.addAttribute("chapters", chapterService.search(hh));
        return "chapters/list";
    }

    @GetMapping("/chapters/create/{storyId}")
    public String createChapter(Model model, @PathVariable("storyId") Long storyId) {
        Chapter chapter = new Chapter();
        model.addAttribute("chapter", chapter);
        model.addAttribute("storyIds", storyId);
        Set<Status> statusList = new HashSet<>();
        Status.stream().forEach(statusList::add);
        model.addAttribute("statusListChapter", statusList);
        return "chapters/new";
    }

    @PostMapping("/chapters/create/{storyId}")
    public String saveChapter(@PathVariable("storyId") Long storyId, Chapter chapter, @RequestParam("my-file") List<MultipartFile> files) {
        List<Chapter> chapters = new ArrayList<>();
        chapter.setUploadFile(fileUploadService.upload(files));
        chapters.add(chapter);
        chapterService.create(storyId, chapter);
        return "redirect:/story/chapters/{storyId}";
    }

    @RequestMapping(value = "/chapter/show/{id}")
    public String showSingleStory(@PathVariable("id") long id, Model model) {
        Chapter chapter = chapterService.findById(id);
        model.addAttribute("chapter", chapter);
        return "chapters/showById";
    }


    @RequestMapping(value = "/chapter/{id}/{action}", method = RequestMethod.GET)
    public String handleStatusChapter(@PathVariable("id") Long chapId,
                               @PathVariable("action") String action,
                               HttpServletRequest request) {
        Chapter chapter = chapterService.findById(chapId);

        if (action.equals("closeChap")) {
            if (chapter.getStatusChap() == Status.OPEN) {
                chapterService.closeChap(chapId);
            }
        }
        if (action.equals("openChap") && chapter.getStatusChap() == Status.CLOSED) {
            chapterService.openChap(chapId);
        }

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

}
