package com.careerdevs.stockapiv1.repositories;

import com.careerdevs.stockapiv1.models.Overview;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface OverviewRepository extends CrudRepository<Overview, Long> {

    public Optional<Overview> findBySymbol (String symbol);

    public List<Overview> findByExchange (String exchange);

    public List<Overview> findByAssetType (String assetType);

    public Optional<Overview> findByName (String name);

    public List<Overview> findByCurrency (String currency);

    public List<Overview> findByCountry (String country);

    public List<Overview> findBySector (String sector);

    public Optional<Overview> deleteBySymbol(String symbol);

    public List<Overview> deleteByExchange (String exchange);

    public List<Overview> deleteByAssetType (String assetType);

}
