package pw.feed.postwriter.in.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pw.feed.postwriter.exception.PostNotFoundException;
import pw.feed.postwriter.model.post.Post;
import pw.feed.postwriter.model.user.User;
import pw.feed.postwriter.service.PostService;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pw.feed.postwriter.util.TestUtil.createPost;
import static pw.feed.postwriter.util.TestUtil.createUser;


@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreatePostWhenRequestIsValid() throws Exception {
        // Given
        User user = createUser("user");
        Post post = createPost("Title!.'?-_", user, null);
        Post savedPost = createPost("Title", user, null);

        // When
        when(postService.createPost(post)).thenReturn(savedPost);

        // Then
        mockMvc.perform(post("/api/pw/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is(savedPost.getTitle())))
                .andExpect(jsonPath("$.description", is(savedPost.getDescription())));
    }

    @Test
    void shouldRetrieveAllPostsSuccessfully() throws Exception {
        // Given
        List<Post> posts = List.of(
                createPost("Title1", null, null),
                createPost("Title2", null, null)
        );

        // When
        when(postService.getAllPosts()).thenReturn(posts);

        // Then
        mockMvc.perform(get("/api/pw/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is(posts.get(0).getTitle())))
                .andExpect(jsonPath("$[1].title", is(posts.get(1).getTitle())));
    }

    @Test
    void shouldFetchPostDetailsWhenIdExists() throws Exception {
        // Given
        Post post = createPost("Title", null, null);

        // When
        when(postService.getPostById(1L)).thenReturn(post);

        // Then
        mockMvc.perform(get("/api/pw/posts/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(post.getTitle())))
                .andExpect(jsonPath("$.description", is(post.getDescription())));
    }

    @Test
    void shouldUpdatePostDetailsWhenIdExistsAndRequestIsValid() throws Exception {
        // Given
        User user = createUser("user");

        Post updatedPost = createPost("Title1", user, null);

        // When
        when(postService.updatePost(1L, updatedPost)).thenReturn(updatedPost);

        // Then
        mockMvc.perform(put("/api/pw/posts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPost)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(updatedPost.getTitle())))
                .andExpect(jsonPath("$.description", is(updatedPost.getDescription())));
    }

    @Test
    void shouldReturnNotFoundWhenPostIdDoesNotExist() throws Exception {
        // Given
        when(postService.getPostById(1L)).thenThrow(new PostNotFoundException("Post not found with id 1"));

        // Then
        mockMvc.perform(get("/api/pw/posts/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Post not found")));
    }

    @Test
    void shouldReturnBadRequestWhenPostBrokeValidations() throws Exception {
        // Given
        Post postWithoutUser = new Post();
        postWithoutUser.setTitle("Some title%%");
        postWithoutUser.setDescription("");
        postWithoutUser.setUser(null);

        // Then
        mockMvc.perform(post("/api/pw/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(postWithoutUser)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("User must be set for a post")))
                .andExpect(content().string(containsString("Title must not contain special characters")))
                .andExpect(content().string(containsString("Description must be set for a post")));
    }
}