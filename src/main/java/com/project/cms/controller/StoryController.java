package com.project.cms.controller;

import com.project.cms.entity.*;
import com.project.cms.repository.StoryRepository;
import com.project.cms.service.*;
import com.project.cms.service.impl.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class StoryController {
    @Autowired
    private StoryService storyService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private StoryRepository storyRepository;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    /*@GetMapping("/home")
    public String home(){
        return "home";
    }*/

    @RequestMapping(value = "/stories/{username}", method = RequestMethod.GET)
    public String StoryByUsername(@PathVariable String username,
                                  Model model) {
        Optional<User> optionalUser = userService.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Page<Story> stories = storyService.findByUserOrderByCreatedAtDesc(user);
            model.addAttribute("stories", stories);
            model.addAttribute("user", user);
            model.addAttribute("username", user.getName());
            return "stories/showByUsername";
        } else {
            return "/error";
        }
    }

    @RequestMapping(value = "/story/show/{id}")
    public String showSingleStory(@PathVariable long id, Principal principal, Model model) {
        Optional<Story> optionalStory = storyService.findForId(id);
        Optional<User> user = userService.findByUsername(principal.getName());
        Optional<Story> optionalPost = storyService.findForId(id);
        Comment comment = new Comment();
        comment.setUser(user.get());
        comment.setStory(optionalPost.get());
        model.addAttribute("comment", comment);
        if (optionalStory.isPresent()) {
            Story story = optionalStory.get();
            model.addAttribute("story", story);
            if (isPrincipalOwnerOfStory(principal, story)) {
                model.addAttribute("username", principal.getName());
            }
            return "stories/showById";
        } else {
            return "/error";
        }

    }

    @RequestMapping(value = "/createChild/{commentId}", method = RequestMethod.POST)
    public String createNewChild(@Valid Comment comment, @PathVariable("commentId") long commentId, Model m, Principal principal) {
        Optional<Comment> optionalComment = commentService.findById(commentId);
        Optional<User> user = userService.findByUsername(principal.getName());
        comment.setParent(optionalComment.get());
        comment.setUser(user.get());
        m.addAttribute("comment1", comment);
        commentService.save(comment);
        return "redirect:/story/show/" + comment.getStory().getId();
    }

    @RequestMapping(value = "/createComment/{id}", method = RequestMethod.POST)
    public String createNewComment( @Valid Comment comment) {
        commentService.save(comment);
        return "redirect:/story/show/" + comment.getStory().getId();
    }


    @RequestMapping(value = "/story/search", method = RequestMethod.GET)
    public String search(@RequestParam("term") String term, Model model) {
        if (StringUtils.isEmpty(term)) {
            return "redirect:/home";
        }
        Set<Status> statusList = new HashSet<>();
        Status.stream().forEach(statusList::add);
        model.addAttribute("statusList", statusList);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("stories", storyService.search(term));
        return "stories/list";
    }

    @RequestMapping(value = "/story/create")
    public String newStoryForm(Model model) {
        model.addAttribute("story", new Story());
        Page<Category> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        Set<Status> statusList = new HashSet<>();
        Status.stream().forEach(statusList::add);
        model.addAttribute("statusList", statusList);
        return "stories/new";
    }

    @RequestMapping(value = "/story", method = RequestMethod.POST)
    public String saveNewStory(Principal principal, Story story, @RequestParam("my-file") MultipartFile file, @RequestParam("folder") String folder) {
        Optional<User> user = userService.findByUsername(principal.getName());
        story.setUser(user.get());
        String filename = fileUploadService.upload(file, folder);
        story.setThumbnail(filename);
        storyService.create(story);
        return "redirect:/blog/" + story.getUser().getUsername();

    }

    @RequestMapping(value = "/story/{id}", method = RequestMethod.GET)
    public String editStoryForm(@PathVariable("id") long id, Model model) {
        Story story = storyService.findById(id);
        Page<Category> categories = categoryService.getAll();
        Set<Status> statusList = new HashSet<>();
        Status.stream().forEach(statusList::add);
        model.addAttribute("statusList", statusList);
        model.addAttribute("allCategories", categories);
        model.addAttribute("story", story);
        return "stories/edit";
    }


    @GetMapping(value = "/stories")
    public String showAllStories(Model model) {
        model.addAttribute("stories", storyService.findAllByOrderByCreatedAtDesc());
        model.addAttribute("categories", categoryService.getAll());
        Set<Status> statusList = new HashSet<>();
        Status.stream().forEach(statusList::add);
        model.addAttribute("statusList", statusList);
        return "stories/list";
    }


    @RequestMapping(value = "/story/{id}", method = RequestMethod.POST)
    public String updateStory(@PathVariable("id") long storyId, Story story) {
        storyService.update(storyId, story);
        return "redirect:/stories";
    }


    @GetMapping("/story/chapters/{id}")
    public String getChaptersByStory(@PathVariable("id") Long storyId, Model model) {
        Optional<Story> stories = storyRepository.findById(storyId);
        model.addAttribute("story", stories.get());
        model.addAttribute("chapters", chapterService.getAllChapterByStory(storyId));
        return "stories/showByIdStories";
    }

    @RequestMapping(value = "/story/delete/{id}", method = RequestMethod.GET)
    public String deleteStory(@PathVariable("id") long storyId) {
        storyService.delete(storyId);
        return "redirect:/stories";
    }


    @RequestMapping(value = "/story/{id}/{action}", method = RequestMethod.GET)
    public String handleStatus(@PathVariable("id") Long storyId,
                               @PathVariable("action") String action,
                               HttpServletRequest request) {
        Story story = storyService.findById(storyId);

        if (action.equals("close")) {
            if (story.getStatus() == Status.OPEN) {
                storyService.closeTask(storyId);
            }
            if (story.getStatus() == Status.OPEN) {
                storyService.closeTask(storyId);
            }
        }
        if (action.equals("open") && story.getStatus() == Status.CLOSED) {
            storyService.openTask(storyId);
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    private boolean isPrincipalOwnerOfStory(Principal principal, Story story) {
        return principal != null && principal.getName().equals(story.getUser().getUsername());
    }


}
