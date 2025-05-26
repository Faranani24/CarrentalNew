package co.za.carrental.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/promotions")
public class PromotionsController {

    @GetMapping
    public String getAllPromotions() {
        return "Returning all promotions... (this should be a list eventually)";
    }

    @PostMapping
    public String createPromotion(@RequestBody String promotion) {
        return "Creating a promotion: " + promotion;
    }
}
