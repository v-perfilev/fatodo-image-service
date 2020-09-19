package com.persoff68.fatodo.model.mapper;

import com.persoff68.fatodo.model.ImageFile;
import com.persoff68.fatodo.model.dto.ImageFileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImageMapper {

    ImageFile imageDTOToImage(ImageFileDTO imageFileDTO);

}
