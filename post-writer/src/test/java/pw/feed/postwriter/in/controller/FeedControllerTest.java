package pw.feed.postwriter.in.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pw.feed.postwriter.service.FeedService;
import pw.feed.postwriter.service.dto.PostRecord;


import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pw.feed.postwriter.util.TestUtil.createPostRecord;

@WebMvcTest(FeedController.class)
class FeedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedService feedService;

    @Test
    void shouldFetchFeed() throws Exception {
        // Given
        List<PostRecord> feed = List.of(
                createPostRecord("title1", "desc1"),
                createPostRecord("title2", "desc2")
        );

        // When
        when(feedService.getFeedForUser(any())).thenReturn(feed);

        // Then
        mockMvc.perform(get("/api/pw/feed/{userName}", "userName"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is(feed.get(0).title())))
                .andExpect(jsonPath("$[1].title", is(feed.get(1).title())));
    }
}