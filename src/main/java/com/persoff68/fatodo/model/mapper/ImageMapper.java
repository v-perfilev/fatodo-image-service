package com.persoff68.fatodo.model.mapper;

import com.persoff68.fatodo.model.Image;
import com.persoff68.fatodo.model.dto.ImageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImageMapper {

    Image imageDTOToImage(ImageDTO imageDTO);

}
