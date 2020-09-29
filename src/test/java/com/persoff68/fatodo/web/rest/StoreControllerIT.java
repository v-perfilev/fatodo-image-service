package com.persoff68.fatodo.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.FatodoImageServiceApplication;
import com.persoff68.fatodo.TestImageUtils;
import com.persoff68.fatodo.model.GroupImage;
import com.persoff68.fatodo.repository.GroupImageRepository;
import org.bson.types.Binary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FatodoImageServiceApplication.class)
public class StoreControllerIT {
    static final String ENDPOINT = "/api/store";

    @Autowired
    WebApplicationContext context;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    GroupImageRepository groupImageRepository;

    MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        groupImageRepository.deleteAll();
        byte[] bytes = TestImageUtils.loadMediumSquareJpg();
        GroupImage groupImage = new GroupImage("group-test_filename", new Binary(bytes), new Binary(bytes));
        groupImageRepository.save(groupImage);
    }

    @Test
    @WithAnonymousUser
    void getImage_ok() throws Exception {
        String url = ENDPOINT + "/group-test_filename";
        ResultActions resultActions = mvc.perform(get(url))
                .andExpect(status().isOk());
        String resultString = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(resultString).isNotBlank();
    }

    @Test
    @WithAnonymousUser
    void getImage_notExists() throws Exception {
        String url = ENDPOINT + "/other_filename";
        mvc.perform(get(url))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithAnonymousUser
    void getImageThumbnail_ok() throws Exception {
        String url = ENDPOINT + "/group-test_filename/thumbnail";
        ResultActions resultActions = mvc.perform(get(url))
                .andExpect(status().isOk());
        String resultString = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(resultString).isNotBlank();
    }

    @Test
    @WithAnonymousUser
    void getImageThumbnail_notExists() throws Exception {
        String url = ENDPOINT + "/other_filename/thumbnail";
        mvc.perform(get(url))
                .andExpect(status().isNotFound());
    }

}
