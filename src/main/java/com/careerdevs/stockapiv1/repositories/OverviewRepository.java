package com.careerdevs.stockapiv1.repositories;

import com.careerdevs.stockapiv1.models.Overview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface OverviewRepository extends JpaRepository<Overview, Long> {

    public List<Overview> findById (long id);
    public List<Overview> findBySymbol (String symbol);
    public List<Overview> findBySector (String sector);
    public List<Overview> findByName (String name);
    public List<Overview> findByCurrency (String currency);
    public List<Overview> findByCountry (String country);

    public List<Overview> deleteById (long id);
    public List<Overview> deleteBySymbol (String symbol);

}


//**Below code is for OverviewControllerDeprecated class

//    public Optional<Overview> findBySymbol (String symbol);
//
//    public Optional<Overview> deleteBySymbol(String symbol);
//
//    public List<Overview> findByExchange (String exchange);
//
//    public List<Overview> deleteByExchange (String exchange);
//
//    public List<Overview> findByAssetType (String assetType);
//
//    public List<Overview> deleteByAssetType (String assetType);
//
//    public Optional<Overview> findByName (String name);
//
//    public Optional <Overview> deleteByName (String name);
//
//    public List<Overview> findByCurrency (String currency);
//
//    public List<Overview> deleteByCurrency (String currency);
//
//    public List<Overview> findByCountry (String country);
//
//    public List<Overview> deleteByCountry (String country);
//
//    public List<Overview> findBySector (String sector);
//
//    public List<Overview> deleteBySector (String sector);