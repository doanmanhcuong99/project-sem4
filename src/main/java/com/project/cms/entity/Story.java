package com.project.cms.entity;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "stories")
public class Story {
    @Id
    @Column(name = "story_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String anotherName;


    @NotEmpty
    private String director;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "story_category",
            joinColumns = {@JoinColumn(name = "story_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private Set<Category> categories = new HashSet<>();
    @Column(name = "CreatedAt")
    private long createdAt;

    private long updatedAt;

    private long deletedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(mappedBy = "story", cascade = CascadeType.REMOVE)
    private Collection<Comment> comments;



    @Lob
    @NotEmpty
    /*@Type(type = "org.hibernate.type.TextType") //heroku config*/
    private String description;
    private String thumbnail;
    private String code;
    private String tag;
    private String actor;
    private int format;


    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Status status;

    public Story() {
    }

    public Story(@NotEmpty String title, @NotEmpty String anotherName, @NotEmpty String director, Set<Category> categories, long createdAt, long updatedAt, long deletedAt, @NotNull User user, Collection<Comment> comments, @NotEmpty String description, String thumbnail, String code, String tag, String actor, int format, @NotNull Status status) {
        this.title = title;
        this.anotherName = anotherName;
        this.director = director;
        this.categories = categories;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.user = user;
        this.comments = comments;
        this.description = description;
        this.thumbnail = thumbnail;
        this.code = code;
        this.tag = tag;
        this.actor = actor;
        this.format = format;
        this.status = status;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void closeTask() {
        this.status = Status.CLOSED;
    }

    public void openTask() {
        this.status = Status.OPEN;
    }


    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getAnotherName() {
        return anotherName;
    }

    public void setAnotherName(String anotherName) {
        this.anotherName = anotherName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
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

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
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

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

}