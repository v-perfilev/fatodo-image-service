package com.persoff68.fatodo.service;

import com.persoff68.fatodo.model.Image;

public interface StoreService {

    Image getByFilename(String filename);

}
