package com.SCM.dto;

import com.SCM.model.Retailer;
import com.SCM.model.WorkOrderItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FetchDto {
	
    private int id;
    private String workOrderId;
    private Date workOrderDate;
    private int retailerPinCode;
    private boolean orderStatus;
    private String remarks;
    private int saleAgentId;
    private String createdBy;
    private Date dateCreated;
    private List<WorkOrderItem> workOderItems = new ArrayList<>();
    private Retailer retailer;

    public FetchDto(){}

    public FetchDto(int id, String workOrderId, Date workOrderDate, int retailerPinCode, boolean orderStatus, String remarks, int saleAgentId, String createdBy, Date dateCreated, List<WorkOrderItem> workOderItems, Retailer retailer) {
        this.id = id;
        this.workOrderId = workOrderId;
        this.workOrderDate = workOrderDate;
        this.retailerPinCode = retailerPinCode;
        this.orderStatus = orderStatus;
        this.remarks = remarks;
        this.saleAgentId = saleAgentId;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.workOderItems = workOderItems;
        this.retailer = retailer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }

    public Date getWorkOrderDate() {
        return workOrderDate;
    }

    public void setWorkOrderDate(Date workOrderDate) {
        this.workOrderDate = workOrderDate;
    }

    public int getRetailerPinCode() {
        return retailerPinCode;
    }

    public void setRetailerPinCode(int retailerPinCode) {
        this.retailerPinCode = retailerPinCode;
    }

    public boolean isOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(boolean orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getSaleAgentId() {
        return saleAgentId;
    }

    public void setSaleAgentId(int saleAgentId) {
        this.saleAgentId = saleAgentId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<WorkOrderItem> getWorkOderItems() {
        return workOderItems;
    }

    public void setWorkOderItems(List<WorkOrderItem> workOderItems) {
        this.workOderItems = workOderItems;
    }

    public Retailer getRetailer() {
        return retailer;
    }

    public void setRetailer(Retailer retailer) {
        this.retailer = retailer;
    }

    @Override
    public String toString() {
        return "FetchDto{" +
                "id=" + id +
                ", workOrderId='" + workOrderId + '\'' +
                ", workOrderDate=" + workOrderDate +
                ", retailerPinCode=" + retailerPinCode +
                ", orderStatus=" + orderStatus +
                ", remarks='" + remarks + '\'' +
                ", saleAgentId=" + saleAgentId +
                ", createdBy='" + createdBy + '\'' +
                ", dateCreated=" + dateCreated +
                ", workOderItems=" + workOderItems +
                ", retailer=" + retailer +
                '}';
    }
}
