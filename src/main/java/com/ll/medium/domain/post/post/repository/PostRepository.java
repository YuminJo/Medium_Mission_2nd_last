package com.ll.medium.domain.post.post.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.post.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    List<Post> findTop30ByPublishedOrderByIdDesc(boolean Published);

	Optional<Post> findByAuthorAndPublishedAndTitle(Member author, boolean Published, String title);
}
