package vn.com.hdbank.lunch_order.dto.response;

import lombok.Data;
import vn.com.hdbank.lunch_order.dto.BeCategory;
import vn.com.hdbank.lunch_order.dto.BeRestaurantInfo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BeFoodResponse {
    private BeRestaurantInfo restaurant_info;
    private List<BeCategory> categories;
}

