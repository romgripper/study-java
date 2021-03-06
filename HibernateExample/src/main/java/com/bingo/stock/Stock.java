package com.bingo.stock;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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

  private Integer id;
  private String code;
  private String name;
  private StockDetail detail;
  private Set<Category> categories = new HashSet<>();

  /* The actual interface might be java.util.Set, java.util.Collection, java.util.List,
   * java.util.Map, java.util.SortedSet, java.util.SortedMap
   */
  private Set<StockDailyRecord> records = new HashSet<>();

  public Stock() {}

  public Stock(String code, String name) {
    super();
    this.code = code;
    this.name = name;
  }

  public Stock(String code, String name, StockDetail detail) {
    this.code = code;
    this.name = name;
    this.detail = detail;
  }

  // These annotations can only be defined on getters
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "STOCK_ID", unique = true, nullable = false)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(name = "STOCK_CODE", unique = true, nullable = false)
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Column(name = "STOCK_NAME", unique = true, nullable = false, length = 20)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /* The mappedBy property is what we use to tell Hibernate which variable we are using
   * to represent the parent class in our child class.
   * In this case, it is StockDetail.stock
   */
  @OneToOne(fetch = FetchType.LAZY, mappedBy = "stock", cascade = CascadeType.ALL)
  public StockDetail getDetail() {
    return detail;
  }

  public void setDetail(StockDetail detail) {
    this.detail = detail;
  }

  /* The mappedBy property is what we use to tell Hibernate which variable we are using
   * to represent the parent class in our child class.
   * In this case, it is StockDailyRecord.stock.
   * If CascadeType.ALL is not used, the records will not be inserted automatically when
   * a Stock is saved.
   */
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "stock", cascade = CascadeType.ALL)
  public Set<StockDailyRecord> getRecords() {
    return records;
  }

  public void setRecords(Set<StockDailyRecord> records) {
    this.records = records;
  }

  /* For n:m mapping, @JoinTable can be defined at either side
   * @JoinTable and mappedBy are defined at different sides
   * The side with @JoinTable is the managing side of the relationship,
   * therefore it will be responsible for updating the join table.
   */
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(
    name = "stock_category",
    joinColumns = {@JoinColumn(name = "STOCK_ID", nullable = false, updatable = false)},
    inverseJoinColumns = {@JoinColumn(name = "CATEGORY_ID", nullable = false, updatable = false)}
  )
  public Set<Category> getCategories() {
    return categories;
  }

  public void setCategories(Set<Category> categories) {
    this.categories = categories;
  }

  @Override
  public String toString() {
    String s = "Stock [" + id + ", " + code + ", " + name + ", " + detail + "]";

    for (StockDailyRecord rec : records) {
      s += "\n  " + rec;
    }
    for (Category cat : categories) {
      s += "\n  " + cat;
    }

    return s;
  }
}
