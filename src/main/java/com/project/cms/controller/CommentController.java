package com.project.cms.controller;

import com.project.cms.entity.Comment;
import com.project.cms.entity.Story;
import com.project.cms.entity.User;
import com.project.cms.repository.CommentRepository;
import com.project.cms.service.CommentService;
import com.project.cms.service.StoryService;
import com.project.cms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class CommentController {
    @Autowired
    private StoryService storyService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;



    @RequestMapping(value = "/createComment", method = RequestMethod.POST)
    public String createNewPost(@Valid Comment comment,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/commentForm";

        } else {
            commentService.save(comment);
            return "redirect:/story/show/" + comment.getStory().getId();
        }
    }

    @RequestMapping(value = "/commentPost/{id}", method = RequestMethod.GET)
    public String commentPostWithId(@PathVariable long id,
                                    Principal principal,
                                    Model model) {

        Optional<Story> story = storyService.findForId(id);

        if (story.isPresent()) {
            Optional<User> user = userService.findByUsername(principal.getName());
            if (user.isPresent()) {
                Comment comment = new Comment();
                comment.setUser(user.get());
                comment.setStory(story.get());
                model.addAttribute("comment", comment);
                return "/commentForm";

            } else {
                return "/error";
            }

        } else {
            return "/error";
        }
    }



}


