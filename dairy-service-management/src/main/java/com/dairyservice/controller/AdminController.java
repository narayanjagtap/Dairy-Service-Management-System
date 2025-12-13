package com.dairyservice.controller;

import com.dairyservice.dto.*;
import com.dairyservice.entity.User;
import com.dairyservice.service.AdminService;
import com.dairyservice.service.AuthService;
import com.dairyservice.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {
    private final AdminService adminService;
    private final BillService billService;
    private final AuthService authService;

    // Helper method to get current user from token and verify role
    private User getCurrentAdminUser(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        String token = authHeader.substring(7);
        User user = authService.validateToken(token);

        if (user != null && user.getRole() == User.UserRole.ADMIN) {
            return user;
        }
        return null;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<AdminDashboardDTO>> getDashboard(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            User user = getCurrentAdminUser(authHeader);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Unauthorized or not an admin"));
            }

            AdminDashboardDTO dashboard = adminService.getDashboard();
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Dashboard retrieved successfully", dashboard)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to fetch dashboard: " + e.getMessage()));
        }
    }

    @GetMapping("/requests")
    public ResponseEntity<ApiResponse<List<MilkRequestDTO>>> getAllRequests(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            User user = getCurrentAdminUser(authHeader);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Unauthorized or not an admin"));
            }

            List<MilkRequestDTO> requests = adminService.getAllRequests();
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Requests retrieved successfully", requests)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to fetch requests: " + e.getMessage()));
        }
    }

    @GetMapping("/requests/today")
    public ResponseEntity<ApiResponse<List<MilkRequestDTO>>> getTodayRequests(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            User user = getCurrentAdminUser(authHeader);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Unauthorized or not an admin"));
            }

            List<MilkRequestDTO> requests = adminService.getTodayRequests();
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Today's requests retrieved successfully", requests)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to fetch today's requests: " + e.getMessage()));
        }
    }

    @PutMapping("/request/{id}/status")
    public ResponseEntity<ApiResponse<String>> updateRequestStatus(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable Long id,
            @RequestBody UpdateRequestStatusDTO dto) {
        try {
            User user = getCurrentAdminUser(authHeader);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Unauthorized or not an admin"));
            }

            String message = adminService.updateRequestStatus(id, dto.getStatus());
            return ResponseEntity.ok(
                    new ApiResponse<>(true, message)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to update status: " + e.getMessage()));
        }
    }

    @PostMapping("/bill")
    public ResponseEntity<ApiResponse<BillDTO>> createBill(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody CreateBillDTO dto) {
        try {
            User user = getCurrentAdminUser(authHeader);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Unauthorized or not an admin"));
            }

            BillDTO bill = billService.createBill(dto);
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Bill created successfully", bill)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to create bill: " + e.getMessage()));
        }
    }

    @GetMapping("/bills")
    public ResponseEntity<ApiResponse<List<BillDTO>>> getAllBills(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            User user = getCurrentAdminUser(authHeader);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Unauthorized or not an admin"));
            }

            List<BillDTO> bills = billService.getAllBills();
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Bills retrieved successfully", bills)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to fetch bills: " + e.getMessage()));
        }
    }

    @PutMapping("/bill/{id}/paid")
    public ResponseEntity<ApiResponse<String>> markBillAsPaid(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable Long id) {
        try {
            User user = getCurrentAdminUser(authHeader);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Unauthorized or not an admin"));
            }

            String message = billService.markBillAsPaid(id);
            return ResponseEntity.ok(
                    new ApiResponse<>(true, message)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to mark bill as paid: " + e.getMessage()));
        }
    }

    @PutMapping("/bill/{id}/unpaid")
    public ResponseEntity<ApiResponse<String>> markBillAsUnpaid(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable Long id) {
        try {
            User user = getCurrentAdminUser(authHeader);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Unauthorized or not an admin"));
            }

            String message = billService.markBillAsUnpaid(id);
            return ResponseEntity.ok(
                    new ApiResponse<>(true, message)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to mark bill as unpaid: " + e.getMessage()));
        }
    }

    @GetMapping("/customers")
    public ResponseEntity<ApiResponse<List<CustomerDTO>>> getAllCustomers(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            User user = getCurrentAdminUser(authHeader);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Unauthorized or not an admin"));
            }

            List<CustomerDTO> customers = adminService.getAllCustomers();
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Customers retrieved successfully", customers)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to fetch customers: " + e.getMessage()));
        }
    }
}
