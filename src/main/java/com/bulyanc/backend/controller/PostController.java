package com.bulyanc.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bulyanc.backend.entity.Post;
import com.bulyanc.backend.entity.User;
import com.bulyanc.backend.service.PostService;
import com.bulyanc.backend.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @Operation(summary = "Get all posts")
    @GetMapping
    public List<Post> findAll() {
        return postService.findAll();
    }

    @Operation(summary = "Get post by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Post> findById(@PathVariable Long id) {
        Optional<Post> post = postService.findById(id);
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new post")
    @PostMapping
    public ResponseEntity<Post> save(@RequestBody Post post, @RequestParam Long userId) {
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            Post newPost = Post.newInstance(post.getTitle(), post.getContent(), user.get());
            return ResponseEntity.ok(postService.save(newPost));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Update post")
    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@PathVariable Long id, @RequestBody Post post, @RequestParam Long userId) {
        Post saved = Post.saved(id, post.getTitle(), post.getContent(), null);
        try {
            return ResponseEntity.ok(postService.update(saved, userId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(403).body(null); // 403 Forbidden
        }
    }

    @Operation(summary = "Delete post")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam Long userId) {
        try {
            postService.delete(id, userId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(403).build();
        }
    }
}
