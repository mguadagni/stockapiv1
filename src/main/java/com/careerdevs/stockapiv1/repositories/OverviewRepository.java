package com.careerdevs.stockapiv1.repositories;

import com.careerdevs.stockapiv1.models.Overview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface OverviewRepository extends JpaRepository<Overview, Long> {

    List<Overview> findById (long id);
    List<Overview> findBySymbol (String symbol);
    List<Overview> findByExchange (String exchange);
    List<Overview> findByAssetType (String assetType);
    List<Overview> findBySector (String sector);
    List<Overview> findByName (String name);
    List<Overview> findByCurrency (String currency);
    List<Overview> findByCountry (String country);

    List<Overview> findByMarketCapGreaterThanEqual (long marketCap);
    List<Overview> findByMarketCapLessThanEqual (long marketCap);
    List<Overview> findByYearHighGreaterThanEqual (long yearHigh);
    List<Overview> findByYearHighLessThanEqual (long yearHigh);
    List<Overview> findByYearLowGreaterThanEqual (long yearLow);
    List<Overview> findByYearLowLessThanEqual (long yearLow);
    List<Overview> findByDividendDateLessThanEqual(String dividendDate);
    List<Overview> findByDividendDateGreaterThanEqual(String dividendDate);


    List<Overview> deleteById (long id);
    List<Overview> deleteBySymbol (String symbol);
    List<Overview> deleteBySector (String sector);
    List<Overview> deleteByName (String name);
    List<Overview> deleteByCurrency (String currency);
    List<Overview> deleteByCountry (String country);
    List<Overview> deleteByAssetType (String assetType);
    List<Overview> deleteByExchange (String exchange);

    List<Overview> deleteByMarketCapGreaterThanEqual (long marketCap);
    List<Overview> deleteByMarketCapLessThanEqual (long marketCap);
    List<Overview> deleteByYearHighGreaterThanEqual (long yearHigh);
    List<Overview> deleteByYearHighLessThanEqual (long yearHigh);
    List<Overview> deleteByYearLowGreaterThanEqual (long yearLow);
    List<Overview> deleteByYearLowLessThanEqual (long yearLow);
    List<Overview> deleteByDividendDateLessThanEqual(String dividendDate);
    List<Overview> deleteByDividendDateGreaterThanEqual(String dividendDate);

}