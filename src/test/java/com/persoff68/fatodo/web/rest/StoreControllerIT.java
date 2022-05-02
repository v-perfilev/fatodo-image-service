package com.persoff68.fatodo.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.FatodoImageServiceApplication;
import com.persoff68.fatodo.TestImageUtils;
import com.persoff68.fatodo.builder.TestImage;
import com.persoff68.fatodo.model.GroupImage;
import com.persoff68.fatodo.model.Image;
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
class StoreControllerIT {
    static final String ENDPOINT = "/api/store";

    private static final String IMAGE_NAME = "group-image-filename";
    private static final String NOT_EXISTING_IMAGE_NAME = "not-existing-filename";

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
        Image image = TestImage.defaultBuilder()
                .id(null)
                .filename(IMAGE_NAME)
                .content(new Binary(bytes))
                .thumbnail(new Binary(bytes))
                .build();
        GroupImage groupImage = new GroupImage(image);
        groupImageRepository.save(groupImage);
    }

    @Test
    @WithAnonymousUser
    void getImage_ok() throws Exception {
        String url = ENDPOINT + "/" + IMAGE_NAME;
        ResultActions resultActions = mvc.perform(get(url))
                .andExpect(status().isOk());
        String resultString = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(resultString).isNotBlank();
    }

    @Test
    @WithAnonymousUser
    void getImage_notExists() throws Exception {
        String url = ENDPOINT + "/" + NOT_EXISTING_IMAGE_NAME;
        mvc.perform(get(url))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithAnonymousUser
    void getImageThumbnail_ok() throws Exception {
        String url = ENDPOINT + "/" + IMAGE_NAME + "/thumbnail";
        ResultActions resultActions = mvc.perform(get(url))
                .andExpect(status().isOk());
        String resultString = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(resultString).isNotBlank();
    }

    @Test
    @WithAnonymousUser
    void getImageThumbnail_notExists() throws Exception {
        String url = ENDPOINT + "/" + NOT_EXISTING_IMAGE_NAME + "/thumbnail";
        mvc.perform(get(url))
                .andExpect(status().isNotFound());
    }

}
