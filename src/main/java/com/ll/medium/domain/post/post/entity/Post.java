package com.ll.medium.domain.post.post.entity;

import static jakarta.persistence.CascadeType.*;
import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.post.postComment.entity.PostComment;
import com.ll.medium.domain.post.postLike.entity.PostLike;
import com.ll.medium.global.jpa.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
public class Post extends BaseEntity {
    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    @Builder.Default
    private List<PostLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    @Builder.Default
    @OrderBy("id DESC")
    private List<PostComment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String body;
    private boolean published;
    @Setter(PROTECTED)
    private long hit;
    private int minMembershipLevel;

    public void increaseHit() {
        hit++;
    }

    public void addLike(Member member) {
        if (hasLike(member)) {
            return;
        }

        likes.add(PostLike.builder()
            .post(this)
            .member(member)
            .build());
    }

    public boolean hasLike(Member member) {
        return likes.stream()
            .anyMatch(postLike -> postLike.getMember().equals(member));
    }

    public void deleteLike(Member member) {
        likes.removeIf(postLike -> postLike.getMember().equals(member));
    }

    public PostComment writeComment(Member actor, String body) {
        PostComment postComment = PostComment.builder()
            .post(this)
            .author(actor)
            .body(body)
            .build();

        comments.add(postComment);

        return postComment;
    }
}