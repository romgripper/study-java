package com.bingo.stock;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stock")
/*@Table(
   name = "stock",
   uniqueConstraints = {
     @UniqueConstraint(columnNames = "STOCK_NAME"),
     @UniqueConstraint(columnNames = "STOCK_CODE")
   }
 )
 "uniqueConstraints" can be omitted as "unique" is defined for the two columns.
*/
public class Stock implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer stockId;
  private String stockCode;
  private String stockName;
  private StockDetail stockDetail;

  public Stock() {}

  public Stock(String stockCode, String stockName) {
    super();
    this.stockCode = stockCode;
    this.stockName = stockName;
  }

  public Stock(String stockCode, String stockName, StockDetail stockDetail) {
    this.stockCode = stockCode;
    this.stockName = stockName;
    this.stockDetail = stockDetail;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "STOCK_ID", unique = true, nullable = false)
  public Integer getStockId() {
    return stockId;
  }

  public void setStockId(Integer stockId) {
    this.stockId = stockId;
  }

  @Column(name = "STOCK_CODE", unique = true, nullable = false)
  public String getStockCode() {
    return stockCode;
  }

  public void setStockCode(String stockCode) {
    this.stockCode = stockCode;
  }

  @Column(name = "STOCK_NAME", unique = true, nullable = false, length = 20)
  public String getStockName() {
    return stockName;
  }

  public void setStockName(String stockName) {
    this.stockName = stockName;
  }

  @OneToOne(fetch = FetchType.LAZY, mappedBy = "stock", cascade = CascadeType.ALL)
  public StockDetail getStockDetail() {
    return stockDetail;
  }

  public void setStockDetail(StockDetail stockDetail) {
    this.stockDetail = stockDetail;
  }

  @Override
  public String toString() {
    return "Stock [" + stockId + ", " + stockCode + ", " + stockName + ", " + stockDetail + "]";
  }
}
