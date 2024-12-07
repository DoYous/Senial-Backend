package com.senials.common.mapper;

import ch.qos.logback.core.model.ComponentModel;
import com.senials.category.CategoryDTO;
import com.senials.category.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    /* Category -> CategoryDTO */
    CategoryDTO toCategoryDTO(Category category);
}
