package com.SCM.mapper;

import com.SCM.dto.CartItemsDto;
import com.SCM.model.CartItems;

import lombok.Getter;
import lombok.Setter;

public class CartItemMapper {

    public static CartItemsDto toDto(CartItems item) {
        CartItemsDto dto = new CartItemsDto();
        dto.setId(item.getId());
        dto.setUnit(item.getUnit());
        dto.setQty(item.getQty());
        dto.setQtykg(item.getQtykg());

//        dto.setBrand(item.getBrand());
//        dto.setCapacity(item.getCapacity());
//        dto.setCategory(item.getCategory());
//        dto.setProduct_name(item.getProductName());
//        dto.setShort_name(item.getShortName());
//        dto.setProduct_type(item.getProductType());
//        dto.setUom_primary(item.getUomPrimary());
//        dto.setUom_secondary(item.getUomSecondary());
//        dto.setLocation(item.getLocation());
//        dto.setEan_code(item.getEanCode());
//        dto.setHsn_code(item.getHsnCode());
//        dto.setDiameter(item.getDiameter());
//        dto.setDlp(item.getDlp());
//        dto.setMrp(item.getMrp());
//        dto.setStandard_qty_per_box(item.getStandardQtyPerBox());
//        dto.setCgst(item.getCgst());
//        dto.setSgst(item.getSgst());
//        dto.setIgst(item.getIgst());

        dto.setProduct(item.getProduct()); // agar full Product chahiye
        return dto;
    }
    
    
 // 🔹 New method: DTO → Entity
    public static CartItems toEntity(CartItemsDto dto) {
        CartItems item = new CartItems();
        item.setId(dto.getId());
        item.setUnit(dto.getUnit());
        item.setQty(dto.getQty());
        item.setQtykg(dto.getQtykg());

//        item.setBrand(dto.getBrand());
//        item.setCapacity(dto.getCapacity());
//        item.setCategory(dto.getCategory());
//        item.setProductName(dto.getProduct_name());
//        item.setShortName(dto.getShort_name());
//        item.setProductType(dto.getProduct_type());
//        item.setUomPrimary(dto.getUom_primary());
//        item.setUomSecondary(dto.getUom_secondary());
//        item.setLocation(dto.getLocation());
//        item.setEanCode(dto.getEan_code());
//        item.setHsnCode(dto.getHsn_code());
//        item.setDiameter(dto.getDiameter());
//        item.setDlp(dto.getDlp());
//        item.setMrp(dto.getMrp());
//        item.setStandardQtyPerBox(dto.getStandard_qty_per_box());
//        item.setCgst(dto.getCgst());
//        item.setSgst(dto.getSgst());
//        item.setIgst(dto.getIgst());

        item.setProduct(dto.getProduct());
        return item;
    }
}
