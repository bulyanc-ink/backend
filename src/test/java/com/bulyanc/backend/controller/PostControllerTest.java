package com.bulyanc.backend.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.bulyanc.backend.entity.Post;
import com.bulyanc.backend.entity.User;
import com.bulyanc.backend.service.PostService;
import com.bulyanc.backend.service.UserService;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    private UserService userService;

    @Test
    public void save() throws Exception {
        User user = User.builder().id(1L).build();
        Post post = Post.builder().id(1L).title("Title").content("Content").author(user).build();
        given(userService.findById(1L)).willReturn(Optional.of(user));
        given(postService.save(Mockito.any(Post.class))).willReturn(post);

        mockMvc.perform(post("/api/posts")
                .param("userId", "1")
                .contentType("application/json")
                .content("{\"title\": \"Title\", \"content\": \"Content\"}"))
            .andExpect(status().isOk());
    }

    @Test
    public void update_Unauthorized() throws Exception {
        given(postService.update(Mockito.any(Post.class), Mockito.eq(1L)))
            .willThrow(new IllegalArgumentException());

        mockMvc.perform(put("/api/posts/1")
                .param("userId", "1")
                .contentType("application/json")
                .content("{\"title\": \"Updated Title\", \"content\": \"Updated Content\"}"))
            .andExpect(status().isForbidden());
    }
}