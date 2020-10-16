package com.persoff68.fatodo.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.FactoryUtils;
import com.persoff68.fatodo.FatodoImageServiceApplication;
import com.persoff68.fatodo.TestImageUtils;
import com.persoff68.fatodo.annotation.WithCustomSecurityContext;
import com.persoff68.fatodo.model.UserImage;
import com.persoff68.fatodo.model.dto.ImageDTO;
import com.persoff68.fatodo.repository.UserImageRepository;
import org.bson.types.Binary;
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
public class UserImageResourceIT {
    static final String ENDPOINT = "/api/user-images";

    @Autowired
    WebApplicationContext context;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserImageRepository userImageRepository;

    MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        userImageRepository.deleteAll();
        UserImage userImage = new UserImage("test_filename", new Binary(new byte[]{}), new Binary(new byte[]{}));
        userImageRepository.save(userImage);
    }

    @Test
    @WithCustomSecurityContext(authority = "ROLE_USER")
    void testCreate_ok() throws Exception {
        ImageDTO dto = FactoryUtils.createImageDTO(null, TestImageUtils.loadMediumSquareJpg());
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
        ImageDTO dto = FactoryUtils.createImageDTO(null, TestImageUtils.loadMediumRectangularJpg());
        String requestBody = objectMapper.writeValueAsString(dto);
        mvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithCustomSecurityContext(authority = "ROLE_USER")
    void testCreate_badRequest_dimensionSmall() throws Exception {
        ImageDTO dto = FactoryUtils.createImageDTO(null, TestImageUtils.loadSmallSquareJpg());
        String requestBody = objectMapper.writeValueAsString(dto);
        mvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithCustomSecurityContext(authority = "ROLE_USER")
    void testCreate_badRequest_dimensionBig() throws Exception {
        ImageDTO dto = FactoryUtils.createImageDTO(null, TestImageUtils.loadBigSquareJpg());
        String requestBody = objectMapper.writeValueAsString(dto);
        mvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithCustomSecurityContext(authority = "ROLE_USER")
    void testCreate_badRequest_format() throws Exception {
        ImageDTO dto = FactoryUtils.createImageDTO(null, TestImageUtils.loadMediumSquarePng());
        String requestBody = objectMapper.writeValueAsString(dto);
        mvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithAnonymousUser
    void testCreate_unauthorized() throws Exception {
        ImageDTO dto = FactoryUtils.createImageDTO(null, TestImageUtils.loadMediumSquareJpg());
        String requestBody = objectMapper.writeValueAsString(dto);
        mvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithCustomSecurityContext(authority = "ROLE_USER")
    void testUpdate_ok() throws Exception {
        ImageDTO dto = FactoryUtils.createImageDTO("test_filename", TestImageUtils.loadMediumSquareJpg());
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
        ImageDTO dto = FactoryUtils.createImageDTO(null, TestImageUtils.loadMediumSquareJpg());
        String requestBody = objectMapper.writeValueAsString(dto);
        mvc.perform(put(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithCustomSecurityContext(authority = "ROLE_USER")
    void testUpdate_notExists() throws Exception {
        ImageDTO dto = FactoryUtils.createImageDTO("new_filename", TestImageUtils.loadMediumSquareJpg());
        String requestBody = objectMapper.writeValueAsString(dto);
        mvc.perform(put(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithAnonymousUser
    void testUpdate_unauthorized() throws Exception {
        ImageDTO dto = FactoryUtils.createImageDTO("test_filename", TestImageUtils.loadMediumSquareJpg());
        String requestBody = objectMapper.writeValueAsString(dto);
        mvc.perform(put(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithCustomSecurityContext(authority = "ROLE_USER")
    void testDelete_ok() throws Exception {
        String url = ENDPOINT + "/test_filename";
        mvc.perform(delete(url))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void testDelete_unauthorized() throws Exception {
        String url = ENDPOINT + "/test_filename";
        mvc.perform(delete(url))
                .andExpect(status().isUnauthorized());
    }

}
