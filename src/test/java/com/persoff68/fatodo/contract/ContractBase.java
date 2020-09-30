package com.persoff68.fatodo.contract;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.TestImageUtils;
import com.persoff68.fatodo.model.GroupImage;
import com.persoff68.fatodo.repository.GroupImageRepository;
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

    @Autowired
    WebApplicationContext context;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    GroupImageRepository groupImageRepository;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.webAppContextSetup(context);
        groupImageRepository.deleteAll();
        byte[] bytes = TestImageUtils.loadMediumSquareJpg();
        GroupImage groupImage = new GroupImage("group-test_filename", new Binary(bytes), new Binary(bytes));
        groupImageRepository.save(groupImage);
    }

}
