package vn.com.hdbank.lunch_order.dto;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BeRestaurantInfo {
    private String name;
    private String address;
    private String image;
    private Boolean is_closed;
    private String email;
    private String phone_no;
    private String merchant_category_name;
}

