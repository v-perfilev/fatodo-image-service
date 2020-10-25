package com.persoff68.fatodo.contract;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.TestImageUtils;
import com.persoff68.fatodo.builder.TestImage;
import com.persoff68.fatodo.model.GroupImage;
import com.persoff68.fatodo.model.Image;
import com.persoff68.fatodo.model.UserImage;
import com.persoff68.fatodo.repository.GroupImageRepository;
import com.persoff68.fatodo.repository.UserImageRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.bson.types.Binary;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMessageVerifier
public abstract class ContractBase {

    private static final String GROUP_IMAGE_NAME = "group-image-filename";
    private static final String USER_IMAGE_NAME = "user-image-filename";

    @Autowired
    WebApplicationContext context;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    GroupImageRepository groupImageRepository;
    @Autowired
    UserImageRepository userImageRepository;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.webAppContextSetup(context);

        byte[] bytes = TestImageUtils.loadMediumSquareJpg();

        Image image = TestImage.defaultBuilder()
                .id(null)
                .filename(GROUP_IMAGE_NAME)
                .content(new Binary(bytes))
                .thumbnail(new Binary(bytes))
                .build();
        GroupImage groupImage = new GroupImage(image);

        groupImageRepository.deleteAll();
        groupImageRepository.save(groupImage);

        image = TestImage.defaultBuilder()
                .id(null)
                .filename(USER_IMAGE_NAME)
                .content(new Binary(bytes))
                .thumbnail(new Binary(bytes))
                .build();
        UserImage userImage = new UserImage(image);

        userImageRepository.deleteAll();
        userImageRepository.save(userImage);
    }

}
