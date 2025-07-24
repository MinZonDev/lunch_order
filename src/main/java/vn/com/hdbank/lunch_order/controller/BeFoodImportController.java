package vn.com.hdbank.lunch_order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.hdbank.lunch_order.service.BeFoodImportService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/import")
public class BeFoodImportController {

    private final BeFoodImportService beFoodImportService;

    @PostMapping("/be")
    public ResponseEntity<?> importFromBe() {
        try {
            beFoodImportService.importFromBeApi();
            return ResponseEntity.ok("Imported successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

