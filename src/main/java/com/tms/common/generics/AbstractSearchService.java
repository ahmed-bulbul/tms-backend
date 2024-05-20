package com.tms.common.generics;



import com.tms.common.constant.ApplicationConstant;
import com.tms.common.models.User;
import com.tms.common.payload.response.PageData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;




public abstract class AbstractSearchService<E extends AbstractDomainBasedEntity, D extends IDto, S extends SDto>
        extends AbstractService<E, D> implements ISearchService<E, D, S> {
    private final AbstractRepository<E> repository;

    public AbstractSearchService(AbstractRepository<E> repository) {
        super(repository);
        this.repository = repository;
    }

    /**
     * search entity by criteria
     *
     * @param searchDto {@link S}
     * @param pageable {@link Pageable}
     * @return {@link User}
     */
    @Override
    public PageData search(S searchDto, Pageable pageable) {
        Specification<E> propellerSpecification = buildSpecification(searchDto).and(new CustomSpecification<E>()
            .active(Objects.nonNull(searchDto.getIsActive()) ? searchDto.getIsActive() : true, ApplicationConstant.IS_ACTIVE_FIELD));
        Page<E> pagedData = repository.findAll(propellerSpecification, pageable);
        List<Object> models = pagedData.getContent()
                .stream().map(this::convertToResponseDto).collect(Collectors.toList());
        return PageData.builder()
                .model(models)
                .totalPages(pagedData.getTotalPages())
                .totalElements(pagedData.getTotalElements())
                .currentPage(pageable.getPageNumber() + 1)
                .build();
    }
    protected abstract Specification<E> buildSpecification(S searchDto);
}
