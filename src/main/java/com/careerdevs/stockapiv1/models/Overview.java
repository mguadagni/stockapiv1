package com.careerdevs.stockapiv1.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Overview {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false, unique = true)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private long id;

    @JsonProperty("Symbol")
    @Column(name="symbol", nullable = false, unique = true)
    private String symbol;

    @JsonProperty("AssetType")
    @Column(name="asset_type", nullable = false)
    private String assetType;

    @JsonProperty("Name")
    @Column(name="name", nullable = false, unique = true)
    private String name;

    @JsonProperty("Exchange")
    @Column(name="exchange", nullable = false)
    private String exchange;

    @JsonProperty("Currency")
    @Column(name="currency", nullable = false)
    private String currency;

    @JsonProperty("Country")
    @Column(name="country", nullable = false)
    private String country;

    @JsonProperty("Sector")
    @Column(name="sector", nullable = false)
    private String sector;

    @JsonProperty("Industry")
    @Column(name="industry", nullable = false)
    private String industry;

    @JsonProperty("MarketCapitalization")
    @Column(name="market_cap", nullable = false)
    private long marketCap;

    @JsonProperty("52WeekHigh")
    @Column(name="year_high", nullable = false)
    private float yearHigh;

    @JsonProperty("52WeekLow")
    @Column(name="year_low", nullable = false)
    private float yearLow;

    @JsonProperty("DividendDate")
    @Column(name="dividend_date", nullable = false)
    private String dividendDate;

    public long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getAssetType() {
        return assetType;
    }

    public String getName() {
        return name;
    }

    public String getExchange() {
        return exchange;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCountry() {
        return country;
    }

    public String getSector() {
        return sector;
    }

    public String getIndustry() {
        return industry;
    }

    public long getMarketCap() {
        return marketCap;
    }

    public float getYearHigh() {
        return yearHigh;
    }

    public float getYearLow() {
        return yearLow;
    }

    public String getDividendDate() {
        return dividendDate;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"symbol\":\"" + symbol + '"' +
                ", \"assetType\":\"" + assetType + '"' +
                ", \"name\":\"" + name + '"' +
                ", \"exchange\":\"" + exchange + '"' +
                ", \"currency\":\"" + currency + '"' +
                ", \"country\":\"" + country + '"' +
                ", \"sector\":\"" + sector + '"' +
                ", \"industry\":\"" + industry + '"' +
                ", \"marketCap\":" + marketCap +
                ", \"yearHigh\":" + yearHigh +
                ", \"yearLow\":" + yearLow +
                ", \"dividendDate\":\"" + dividendDate + '"' +
                '}';
    }
}
