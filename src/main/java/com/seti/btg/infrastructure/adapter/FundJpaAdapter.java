package com.seti.btg.infrastructure.adapter;

import com.seti.btg.domain.constant.ErrorConstant;
import com.seti.btg.domain.model.Fund;
import com.seti.btg.domain.model.FundSubscription;
import com.seti.btg.domain.model.dto.FundDto;
import com.seti.btg.domain.model.dto.FundSubscriptionDto;
import com.seti.btg.domain.repository.FundRepositoryPort;
import com.seti.btg.infrastructure.adapter.entity.FundEntity;
import com.seti.btg.infrastructure.adapter.exception.ErrorException;
import com.seti.btg.infrastructure.adapter.mapper.FundDbMapper;
import com.seti.btg.infrastructure.adapter.mapper.FundSubscriptionDbMapper;
import com.seti.btg.infrastructure.adapter.repository.FundRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FundJpaAdapter implements FundRepositoryPort {

    @Autowired
    private FundRepository repository;
    @Autowired
    private FundDbMapper dbMapper;

    @Autowired
    private FundSubscriptionDbMapper fundSubscriptionDbMapper;

    @Autowired
    private EntityManager entityManager;

    /**
     * Crea un nuevo fondo
     *
     * @param fund
     * @return
     */
    @Override
    public Fund createNewFund(Fund fund) {
        if (repository.existsByName(fund.getName())) {
            throw new ErrorException(HttpStatus.BAD_REQUEST, ErrorConstant.FUND_ALREADY_EXISTS);
        }
        var newFund = dbMapper.toDbo(fund);
        var fundCreated = repository.save(newFund);

        return dbMapper.toDomain(fundCreated);
    }

    /**
     * Obtiene una lista de fondos
     *
     * @return
     */
    @Override
    public List<Fund> getAll() {
        return repository.findAll().stream().map(dbMapper::toDomain).collect(Collectors.toList());
    }

    /**
     * Obtiene el fondo por su id
     *
     * @param id
     * @return
     */
    @Override
    public Fund getFundById(Long id) {
        var fund = repository.findById(id);

        if (fund.isEmpty()) {
            throw new ErrorException(HttpStatus.NOT_FOUND,
                    String.format(ErrorConstant.FUND_NOT_FOUND, id));
        }

        return dbMapper.toDomain(fund.get());
    }

    @Override
    public List<FundSubscription> getFundsByCustomerId(Long customerId) {
        String query = "SELECT f, s.amount FROM FundEntity f " +
                "JOIN SubscriptionEntity s ON f.id = s.fund.id " +
                "WHERE s.customer.id = :customerId";

        List<Object[]> resultList = entityManager.createQuery(query)
                .setParameter("customerId", customerId)
                .getResultList();

        return resultList.stream()
                .map(result -> {
                    FundEntity fund = (FundEntity) result[0];
                    BigDecimal amount = (BigDecimal) result[1];
                    FundDto fundDto = new FundDto(fund.getId(), fund.getName(), fund.getMinAmount().doubleValue(), fund.getCategory());
                    FundSubscriptionDto fundSubscriptionDto = new FundSubscriptionDto(fundDto, amount);
                    return fundSubscriptionDbMapper.toEntity(fundSubscriptionDto);
                })
                .collect(Collectors.toList());
    }
}
