package vn.com.hdbank.lunch_order.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.com.hdbank.lunch_order.dto.BeCategory;
import vn.com.hdbank.lunch_order.dto.BeItem;
import vn.com.hdbank.lunch_order.dto.BeRestaurantInfo;
import vn.com.hdbank.lunch_order.dto.response.BeFoodResponse;
import vn.com.hdbank.lunch_order.entity.Item;
import vn.com.hdbank.lunch_order.entity.Menu;
import vn.com.hdbank.lunch_order.entity.Restaurant;
import vn.com.hdbank.lunch_order.repository.ItemRepository;
import vn.com.hdbank.lunch_order.repository.MenuRepository;
import vn.com.hdbank.lunch_order.repository.RestaurantRepository;
import vn.com.hdbank.lunch_order.repository.UserRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BeFoodImportService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public void importFromBeApi() throws IOException {
        // 1. Call API
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjowLCJhdWQiOiJndWVzdCIsImV4cCI6MTc1MzQzMDQ2MiwiaWF0IjoxNzUzMzQ0MDYyLCJpc3MiOiJiZS1kZWxpdmVyeS1nYXRld2F5In0.SM5-roATtf3aLTWt4hSftq3krmxRUH7SMWI2gnuklXU");
        String json = """
        {
            "restaurant_id": "23992",
            "locale": "vi",
            "app_version": "11280",
            "version": "1.1.280",
            "device_type": 3,
            "operator_token": "0b28e008bc323838f5ec84f718ef11e6",
            "customer_package_name": "xyz.be.food",
            "device_token": "8cf04e281318af421dc03fc482e00bfd",
            "ad_id": "",
            "screen_width": 360,
            "screen_height": 640,
            "client_info": {
                "locale": "vi",
                "app_version": "11280",
                "version": "1.1.280",
                "device_type": 3,
                "operator_token": "0b28e008bc323838f5ec84f718ef11e6",
                "customer_package_name": "xyz.be.food",
                "device_token": "8cf04e281318af421dc03fc482e00bfd",
                "ad_id": "",
                "screen_width": 360,
                "screen_height": 640
            },
            "latitude": 10.77253621500006,
            "longitude": 106.69798153800008
        }
        """;

        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<JsonNode> response = restTemplate.postForEntity(
                "https://gw.be.com.vn/api/v1/be-marketplace/web/restaurant/detail",
                entity, JsonNode.class);

        JsonNode dataNode = response.getBody().path("data");
        BeFoodResponse beResponse = objectMapper.treeToValue(dataNode, BeFoodResponse.class);

        // 2. Save Restaurant
        BeRestaurantInfo rInfo = beResponse.getRestaurant_info();
        Restaurant restaurant = new Restaurant();
        restaurant.setName(rInfo.getName());
        restaurant.setAddress(rInfo.getAddress());
        restaurant.setImage(rInfo.getImage());
        restaurant.setIsClosed(rInfo.getIs_closed());
        restaurant.setEmail(rInfo.getEmail());
        restaurant.setPhoneNo(rInfo.getPhone_no());
        restaurant.setMerchantCategoryName(rInfo.getMerchant_category_name());
        restaurant.setCreatedAt(LocalDateTime.now());
        restaurantRepository.save(restaurant);

        // 3. Create Menu
        Menu menu = new Menu();
        menu.setMenuName("Menu BE Import");
        menu.setCreateTime(LocalDateTime.now());
        menu.setIsValid(true);
        menu.setRestaurant(restaurant);
        menu.setCreator(userRepository.findById(1L).orElseThrow()); // giả định user ID 1 là admin
        menuRepository.save(menu);

        // 4. Save Items
        List<BeCategory> categories = beResponse.getCategories();
        for (BeCategory cat : categories) {
            for (BeItem i : cat.getItems()) {
                Item item = new Item();
                item.setItemName(i.getItem_name());
                item.setPrice(i.getPrice());
                item.setOldPrice(i.getOld_price());
                item.setItemImage(i.getItem_image());
                item.setItemDetails(i.getItem_details());
                item.setIsActive(i.getIs_active());
                item.setMenu(menu);
                itemRepository.save(item);
            }
        }
    }
}

