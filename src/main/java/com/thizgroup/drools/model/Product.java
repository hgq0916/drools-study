package com.thizgroup.drools.model;

import lombok.Data;

@Data
public class Product {
  // 钻石
  public static final String DIAMOND = "0";
  // 黄金
  public static final String GOLD = "1";

  // 类别
  private String type;
  // 折扣
  private int discount;

}
