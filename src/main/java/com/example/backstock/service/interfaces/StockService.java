package com.example.backstock.service.interfaces;

import com.example.backstock.entity.Stock;

import java.util.List;

public interface StockService {
    Stock createStock(Stock stock, String userName);
    Stock getStockById(long id);
    Stock updateStock(long id, Stock stock);
    void deleteStock(long id);
    void updateStockHistory(long id, String newHistory);
    List<Stock> comparerStocksQuantiteMin();
    long calculerDureeEnJours(Stock stock);
}
