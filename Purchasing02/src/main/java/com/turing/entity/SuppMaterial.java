package com.turing.entity;

import java.io.Serializable;

public class SuppMaterial implements Serializable {
    private Long supplierId;

    private Long materialId;

    private static final long serialVersionUID = 1L;

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }
}