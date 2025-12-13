package com.dairyservice.controller;

import com.dairyservice.dto.*;
import com.dairyservice.entity.Bill;
import com.dairyservice.entity.User;
import com.dairyservice.service.AuthService;
import com.dairyservice.service.BillService;
import com.dairyservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CustomerController {
    private final CustomerService customerService;
    private final AuthService authService;
    private final BillService billService;

    // Helper method to get current user from token
    private User getCurrentUser(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        String token = authHeader.substring(7);
        return authService.validateToken(token);
    }

    @PostMapping("/request")
    public ResponseEntity<ApiResponse<MilkRequestDTO>> createMilkRequest(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody CreateMilkRequestDTO dto) {
        try {
            User user = getCurrentUser(authHeader);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Unauthorized"));
            }

            MilkRequestDTO request = customerService.createRequest(user.getId(), dto);
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Request created successfully", request)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to create request: " + e.getMessage()));
        }
    }

    @GetMapping("/requests")
    public ResponseEntity<ApiResponse<List<MilkRequestDTO>>> getMyRequests(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            User user = getCurrentUser(authHeader);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Unauthorized"));
            }

            List<MilkRequestDTO> requests = customerService.getMyRequests(user.getId());
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Requests retrieved successfully", requests)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to fetch requests: " + e.getMessage()));
        }
    }

    @GetMapping("/bills")
    public ResponseEntity<ApiResponse<List<BillDTO>>> getMyBills(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            User user = getCurrentUser(authHeader);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Unauthorized"));
            }

            List<BillDTO> bills = billService.getCustomerBills(user.getId());
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Bills retrieved successfully", bills)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to fetch bills: " + e.getMessage()));
        }
    }

    @GetMapping("/outstanding")
    public ResponseEntity<ApiResponse<Double>> getTotalOutstanding(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            User user = getCurrentUser(authHeader);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Unauthorized"));
            }

            Double outstanding = customerService.getTotalOutstanding(user.getId());
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Outstanding amount retrieved", outstanding)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to fetch outstanding: " + e.getMessage()));
        }
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<CustomerDashboardDTO>> getDashboard(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            User user = getCurrentUser(authHeader);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Unauthorized"));
            }

            CustomerDashboardDTO dashboard = customerService.getDashboard(user.getId());
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Dashboard retrieved successfully", dashboard)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to fetch dashboard: " + e.getMessage()));
        }
    }
}
