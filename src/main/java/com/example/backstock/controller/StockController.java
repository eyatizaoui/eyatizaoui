package com.example.backstock.controller;

import com.example.backstock.entity.Stock;
import com.example.backstock.service.interfaces.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping("/create")
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock, @RequestParam String userName) {
        Stock createdStock = stockService.createStock(stock, userName);
        if (createdStock != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStock);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable long id) {
        Stock stock = stockService.getStockById(id);
        if (stock != null) {
            return ResponseEntity.ok(stock);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable long id, @RequestBody Stock updatedStock) {
        Stock updated = stockService.updateStock(id, updatedStock);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/history")
    public ResponseEntity<Void> updateStockHistory(@PathVariable long id, @RequestBody String newHistory) {
        stockService.updateStockHistory(id, newHistory);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/compare")
    public ResponseEntity<List<Stock>> compareStocksWithMinQuantity() {
        List<Stock> result = stockService.comparerStocksQuantiteMin();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/duration/{id}")
    public ResponseEntity<Long> calculateDurationInDays(@PathVariable long id) {
        Stock stock = stockService.getStockById(id);
        long duration = stockService.calculerDureeEnJours(stock);
        return ResponseEntity.ok(duration);
    }
}

