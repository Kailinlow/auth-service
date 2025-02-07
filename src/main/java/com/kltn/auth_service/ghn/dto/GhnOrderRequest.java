package com.kltn.auth_service.ghn.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GhnOrderRequest {
    private Integer payment_type_id;
    private String note;
    private String required_note = "KHONGCHOXEMHANG";
    private String to_name;
    private String to_phone;
    private String to_address;
    private String to_ward_code;
    private Integer to_district_id;
    private Integer cod_amount;
    private Integer weight;
    private Integer length;
    private Integer width;
    private Integer height;
    private Integer insurance_value;
    private Integer service_type_id = 2;
    private String coupon;
    private List<Item> items;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item {
        private String name;
        private String code;
        private Integer quantity;
        private Integer price;
        private Integer length;
        private Integer width;
        private Integer height;
        private Integer weight;
    }
}
