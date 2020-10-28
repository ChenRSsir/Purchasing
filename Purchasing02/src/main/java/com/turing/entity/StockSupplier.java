package com.turing.entity;

import java.io.Serializable;

public class StockSupplier implements Serializable {
    private Long stockId;

    private Long supplierId;

    private static final long serialVersionUID = 1L;

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
}