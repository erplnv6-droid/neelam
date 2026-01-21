package com.SCM.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DistributorMinimumStockItems {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private float stockqty;
  private float kgstockqty;
  private float amount;
  private float rate;
  
  @ManyToOne(cascade = {CascadeType.DETACH}, fetch = FetchType.EAGER)
  @JoinColumn(name = "productId")
  private Product product;
  
  public int getId() {
    return this.id;
  }
  
  public float getStockqty() {
    return this.stockqty;
  }
  
  public float getKgstockqty() {
    return this.kgstockqty;
  }
  
  public float getAmount() {
    return this.amount;
  }
  
  public float getRate() {
    return this.rate;
  }
  
  public Product getProduct() {
    return this.product;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public void setStockqty(float stockqty) {
    this.stockqty = stockqty;
  }
  
  public void setKgstockqty(float kgstockqty) {
    this.kgstockqty = kgstockqty;
  }
  
  public void setAmount(float amount) {
    this.amount = amount;
  }
  
  public void setRate(float rate) {
    this.rate = rate;
  }
  
  public void setProduct(Product product) {
    this.product = product;
  }
}
