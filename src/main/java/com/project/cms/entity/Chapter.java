package com.project.cms.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Chapter {
    @Id
    @Column(name = "chapter_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    private String title;
    private String content;
    private int episode;


    /*
    @Type(type = "org.hibernate.type.TextType") //heroku config
    */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "story_id", nullable = false)
    private Story story;
    @Column
    @ElementCollection(targetClass = String.class)
    private List<String> uploadFile;
    private long createdAt;
    private long updatedAt;
    private long deletedAt;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Status statusChap;


    public Chapter(@NotEmpty String title, String content, Story story, List<String> uploadFile, long createdAt, long updatedAt, long deletedAt, Status statusChap, int episode) {
        this.title = title;
        this.content = content;
        this.story = story;
        this.uploadFile = uploadFile;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.statusChap = statusChap;
        this.episode = episode;
    }

    public void closeChap() {
        this.statusChap = Status.CLOSED;
    }

    public void openChap() {
        this.statusChap = Status.OPEN;
    }

    public Chapter() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public Status getStatusChap() {
        return statusChap;
    }

    public void setStatusChap(Status statusChap) {
        this.statusChap = statusChap;
    }

    public List<String> getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(List<String> uploadFile) {
        this.uploadFile = uploadFile;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(long deletedAt) {
        this.deletedAt = deletedAt;
    }


}
