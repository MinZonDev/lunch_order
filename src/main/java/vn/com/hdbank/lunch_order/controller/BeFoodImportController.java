package vn.com.hdbank.lunch_order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.hdbank.lunch_order.dto.request.BeFoodImportRequestDto;
import vn.com.hdbank.lunch_order.service.BeFoodImportService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/import")
public class BeFoodImportController {

    private final BeFoodImportService beFoodImportService;

    @PostMapping("/be")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> importFromBe(@RequestBody BeFoodImportRequestDto requestDto) {
        try {
            beFoodImportService.importFromBeApi(requestDto.getBeFoodToken(), requestDto.getPayload());
            return ResponseEntity.ok("Imported successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
