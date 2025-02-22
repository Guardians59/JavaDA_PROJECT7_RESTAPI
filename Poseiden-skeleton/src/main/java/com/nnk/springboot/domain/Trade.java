package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import java.sql.Timestamp;

@Entity
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_id")
    private int tradeId;

    @NotBlank
    @Column(name = "account")
    private String account;

    @NotBlank
    @Column(name = "type")
    private String type;

    @Positive (message = "Must be positive")
    @Column(name = "buy_quantity")
    private Double buyQuantity;

    @Column(name = "sell_quantity")
    private Double sellQuantity;

    @Column(name = "buy_price")
    private Double buyPrice;

    @Column(name = "sell_price")
    private Double sellPrice;

    @Column(name = "benchmark")
    private String benchmark;

    @Column(name = "trade_date")
    private Timestamp tradeDate;

    @Column(name = "security")
    private String security;

    @Column(name = "status")
    private String status;

    @Column(name = "trader")
    private String trader;

    @Column(name = "book")
    private String book;

    @Column(name = "creation_name")
    private String creationName;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "revision_name")
    private String revisionName;

    @Column(name = "revision_date")
    private Timestamp revisionDate;

    @Column(name = "deal_name")
    private String dealName;

    @Column(name = "deal_type")
    private String dealType;

    @Column(name = "source_list_id")
    private String sourceListId;

    @Column(name = "side")
    private String side;

    public Trade() {

    }

    public Trade(int tradeId, @NotBlank String account, @NotBlank String type,
	    @Positive(message = "Must be positive") Double buyQuantity, Double sellQuantity, Double buyPrice,
	    Double sellPrice, String benchmark, Timestamp tradeDate, String security, String status, String trader,
	    String book, String creationName, Timestamp creationDate, String revisionName, Timestamp revisionDate,
	    String dealName, String dealType, String sourceListId, String side) {
	this.tradeId = tradeId;
	this.account = account;
	this.type = type;
	this.buyQuantity = buyQuantity;
	this.sellQuantity = sellQuantity;
	this.buyPrice = buyPrice;
	this.sellPrice = sellPrice;
	this.benchmark = benchmark;
	this.tradeDate = tradeDate;
	this.security = security;
	this.status = status;
	this.trader = trader;
	this.book = book;
	this.creationName = creationName;
	this.creationDate = creationDate;
	this.revisionName = revisionName;
	this.revisionDate = revisionDate;
	this.dealName = dealName;
	this.dealType = dealType;
	this.sourceListId = sourceListId;
	this.side = side;
    }

    public int getTradeId() {
	return tradeId;
    }

    public void setTradeId(int tradeId) {
	this.tradeId = tradeId;
    }

    public String getAccount() {
	return account;
    }

    public void setAccount(String account) {
	this.account = account;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public Double getBuyQuantity() {
	return buyQuantity;
    }

    public void setBuyQuantity(Double buyQuantity) {
	this.buyQuantity = buyQuantity;
    }

    public Double getSellQuantity() {
	return sellQuantity;
    }

    public void setSellQuantity(Double sellQuantity) {
	this.sellQuantity = sellQuantity;
    }

    public Double getBuyPrice() {
	return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
	this.buyPrice = buyPrice;
    }

    public Double getSellPrice() {
	return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
	this.sellPrice = sellPrice;
    }

    public String getBenchmark() {
	return benchmark;
    }

    public void setBenchmark(String benchmark) {
	this.benchmark = benchmark;
    }

    public Timestamp getTradeDate() {
	return tradeDate;
    }

    public void setTradeDate(Timestamp tradeDate) {
	this.tradeDate = tradeDate;
    }

    public String getSecurity() {
	return security;
    }

    public void setSecurity(String security) {
	this.security = security;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getTrader() {
	return trader;
    }

    public void setTrader(String trader) {
	this.trader = trader;
    }

    public String getBook() {
	return book;
    }

    public void setBook(String book) {
	this.book = book;
    }

    public String getCreationName() {
	return creationName;
    }

    public void setCreationName(String creationName) {
	this.creationName = creationName;
    }

    public Timestamp getCreationDate() {
	return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
	this.creationDate = creationDate;
    }

    public String getRevisionName() {
	return revisionName;
    }

    public void setRevisionName(String revisionName) {
	this.revisionName = revisionName;
    }

    public Timestamp getRevisionDate() {
	return revisionDate;
    }

    public void setRevisionDate(Timestamp revisionDate) {
	this.revisionDate = revisionDate;
    }

    public String getDealName() {
	return dealName;
    }

    public void setDealName(String dealName) {
	this.dealName = dealName;
    }

    public String getDealType() {
	return dealType;
    }

    public void setDealType(String dealType) {
	this.dealType = dealType;
    }

    public String getSourceListId() {
	return sourceListId;
    }

    public void setSourceListId(String sourceListId) {
	this.sourceListId = sourceListId;
    }

    public String getSide() {
	return side;
    }

    public void setSide(String side) {
	this.side = side;
    }

}
