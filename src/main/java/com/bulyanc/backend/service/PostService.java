package com.bulyanc.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bulyanc.backend.entity.Post;
import com.bulyanc.backend.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public Post update(Post post, Long authorId) {
        Optional<Post> existingPost = postRepository.findByIdAndAuthorId(post.getId(), authorId);
        if (existingPost.isPresent()) {
            return postRepository.save(post);
        } else {
            throw new IllegalArgumentException("Only the author can update this post.");
        }
    }

    public void delete(Long postId, Long authorId) {
        Optional<Post> existingPost = postRepository.findByIdAndAuthorId(postId, authorId);
        if (existingPost.isPresent()) {
            postRepository.deleteById(postId);
        } else {
            throw new IllegalArgumentException("Only the author can delete this post.");
        }
    }
}
