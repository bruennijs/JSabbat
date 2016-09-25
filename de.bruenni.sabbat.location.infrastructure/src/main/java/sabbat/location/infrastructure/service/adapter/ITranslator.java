package sabbat.location.infrastructure.service.adapter;

import sabbat.location.core.domain.model.Activity;

/**
 * Created by bruenni on 25.09.16.
 */
public interface ITranslator<TDomainModel, TDto> {
    /***
     * Translates DTO object to domain model
     * @param dto
     * @return
     */
    TDomainModel fromDto(TDto dto);

    /**
     * Translates domain model object to DTO object.
     * @param domainModel
     * @return
     */
    TDto toDto(TDomainModel domainModel);
}
