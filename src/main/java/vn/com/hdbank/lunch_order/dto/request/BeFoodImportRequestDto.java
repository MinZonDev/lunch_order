package vn.com.hdbank.lunch_order.dto.request;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class BeFoodImportRequestDto {
    private String beFoodToken;
    private JsonNode payload;
}
