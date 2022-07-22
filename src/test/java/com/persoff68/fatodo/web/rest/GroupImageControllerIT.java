package com.persoff68.fatodo.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.FatodoImageServiceApplication;
import com.persoff68.fatodo.TestImageUtils;
import com.persoff68.fatodo.annotation.WithCustomSecurityContext;
import com.persoff68.fatodo.builder.TestImage;
import com.persoff68.fatodo.builder.TestImageDTO;
import com.persoff68.fatodo.model.GroupImage;
import com.persoff68.fatodo.model.Image;
import com.persoff68.fatodo.model.dto.ImageDTO;
import com.persoff68.fatodo.repository.GroupImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FatodoImageServiceApplication.class)
class GroupImageControllerIT {
    static final String ENDPOINT = "/api/group-image";

    private static final String IMAGE_NAME = "image-filename";
    private static final String NEW_IMAGE_NAME = "new-filename";

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
        Image image = TestImage.defaultBuilder().id(null).filename(IMAGE_NAME).build();
        GroupImage groupImage = new GroupImage(image);
        groupImageRepository.save(groupImage);
    }

    @Test
    @WithCustomSecurityContext(authority = "ROLE_USER")
    void testCreate_ok() throws Exception {
        ImageDTO dto = TestImageDTO.defaultBuilder()
                .filename(null)
                .content(TestImageUtils.loadMediumSquareJpg())
                .build();
        String requestBody = objectMapper.writeValueAsString(dto);
        ResultActions resultActions = mvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated());
        String resultString = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(resultString).isNotBlank();
    }

    @Test
    @WithCustomSecurityContext(authority = "ROLE_USER")
    void testCreate_badRequest_radio() throws Exception {
        ImageDTO dto = TestImageDTO.defaultBuilder()
                .filename(null)
                .content(TestImageUtils.loadMediumRectangularJpg())
                .build();
        String requestBody = objectMapper.writeValueAsString(dto);
        mvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithCustomSecurityContext(authority = "ROLE_USER")
    void testCreate_badRequest_dimensionSmall() throws Exception {
        ImageDTO dto = TestImageDTO.defaultBuilder()
                .filename(null)
                .content(TestImageUtils.loadSmallSquareJpg())
                .build();
        String requestBody = objectMapper.writeValueAsString(dto);
        mvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithCustomSecurityContext(authority = "ROLE_USER")
    void testCreate_badRequest_dimensionBig() throws Exception {
        ImageDTO dto = TestImageDTO.defaultBuilder()
                .filename(null)
                .content(TestImageUtils.loadBigSquareJpg())
                .build();
        String requestBody = objectMapper.writeValueAsString(dto);
        mvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithCustomSecurityContext(authority = "ROLE_USER")
    void testCreate_badRequest_format() throws Exception {
        ImageDTO dto = TestImageDTO.defaultBuilder()
                .filename(null)
                .content(TestImageUtils.loadMediumSquarePng())
                .build();
        String requestBody = objectMapper.writeValueAsString(dto);
        mvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithAnonymousUser
    void testCreate_unauthorized() throws Exception {
        ImageDTO dto = TestImageDTO.defaultBuilder()
                .filename(null)
                .content(TestImageUtils.loadMediumSquareJpg())
                .build();
        String requestBody = objectMapper.writeValueAsString(dto);
        mvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithCustomSecurityContext(authority = "ROLE_USER")
    void testUpdate_ok() throws Exception {
        ImageDTO dto = TestImageDTO.defaultBuilder()
                .filename(IMAGE_NAME)
                .content(TestImageUtils.loadMediumSquareJpg())
                .build();
        String requestBody = objectMapper.writeValueAsString(dto);
        ResultActions resultActions = mvc.perform(put(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());
        String resultString = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(resultString).isNotBlank();
    }

    @Test
    @WithCustomSecurityContext(authority = "ROLE_USER")
    void testUpdate_noFilename() throws Exception {
        ImageDTO dto = TestImageDTO.defaultBuilder()
                .filename(null)
                .content(TestImageUtils.loadMediumSquareJpg())
                .build();
        String requestBody = objectMapper.writeValueAsString(dto);
        mvc.perform(put(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithCustomSecurityContext(authority = "ROLE_USER")
    void testUpdate_notExists() throws Exception {
        ImageDTO dto = TestImageDTO.defaultBuilder()
                .filename(NEW_IMAGE_NAME)
                .content(TestImageUtils.loadMediumSquareJpg())
                .build();
        String requestBody = objectMapper.writeValueAsString(dto);
        mvc.perform(put(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithAnonymousUser
    void testUpdate_unauthorized() throws Exception {
        ImageDTO dto = TestImageDTO.defaultBuilder()
                .filename(IMAGE_NAME)
                .content(TestImageUtils.loadMediumSquareJpg())
                .build();
        String requestBody = objectMapper.writeValueAsString(dto);
        mvc.perform(put(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithCustomSecurityContext(authority = "ROLE_USER")
    void testDelete_ok() throws Exception {
        String url = ENDPOINT + "/" + IMAGE_NAME;
        mvc.perform(delete(url))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void testDelete_unauthorized() throws Exception {
        String url = ENDPOINT + "/" + IMAGE_NAME;
        mvc.perform(delete(url))
                .andExpect(status().isUnauthorized());
    }

}
