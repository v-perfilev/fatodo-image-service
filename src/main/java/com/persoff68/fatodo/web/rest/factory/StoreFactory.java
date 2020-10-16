package com.persoff68.fatodo.web.rest.factory;

import com.persoff68.fatodo.service.GroupImageService;
import com.persoff68.fatodo.service.StoreService;
import com.persoff68.fatodo.service.UserImageService;
import com.persoff68.fatodo.service.exception.ModelNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreFactory {

    private final GroupImageService groupImageService;
    private final UserImageService userImageService;

    public StoreService getServiceForFilename(String filename) {
        if (filename.startsWith(GroupImageService.PREFIX)) {
            return groupImageService;
        } else if (filename.startsWith(UserImageService.PREFIX)) {
            return userImageService;
        } else {
            throw new ModelNotFoundException();
        }
    }


}
