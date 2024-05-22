package com.example.backstock.service.implementation;

import com.example.backstock.entity.Stock;
import com.example.backstock.entity.UserEntity;
import com.example.backstock.repository.StockRepository;
import com.example.backstock.repository.UserEntityRepo;
import com.example.backstock.service.interfaces.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private UserEntityRepo userEntityRepository;

    public Stock createStock(Stock stock, String userName) {
        Optional<UserEntity> optionalUser = userEntityRepository.findByUserName(userName);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            stock.setResponsable(user);
            return stockRepository.save(stock);
        } else {
            return null;
        }
    }
    @Override
    public Stock getStockById(long id) {
        Optional<Stock> optionalStock = stockRepository.findById(id);
        return optionalStock.orElse(null);
    }

    @Override
    public Stock updateStock(long id, Stock updatedStock) {
        Stock existingStock = getStockById(id);
        if (existingStock != null) {
            existingStock.setQuantiteStock(updatedStock.getQuantiteStock());
            existingStock.setArticleDispo(updatedStock.getArticleDispo());
            existingStock.setDateDebut(updatedStock.getDateDebut());
            existingStock.setDateFin(updatedStock.getDateFin());
            existingStock.setEmplacement(updatedStock.getEmplacement());
            existingStock.setResponsable(updatedStock.getResponsable());
            return stockRepository.save(existingStock);
        }
        return null;
    }

    @Override
    public void deleteStock(long id) {
        stockRepository.deleteById(id);
    }

    @Override
    public void updateStockHistory(long id, String newHistory) {
        Stock existingStock = getStockById(id);
        if (existingStock != null) {
            existingStock.setHistoriqueStock(newHistory);
            stockRepository.save(existingStock);
        }
    }
    public List<Stock> comparerStocksQuantiteMin() {
        List<Stock> stocks = stockRepository.findAll();
        List<Stock> resultat = new ArrayList<>();
        for (Stock stock : stocks) {
            float quantiteStock = stock.getQuantiteStock();
            float quantiteMinStock = stock.getQuantiteMinStock();
            if (quantiteStock < quantiteMinStock) {
                resultat.add(stock);
            }
        }
        return resultat;
    }
    public long calculerDureeEnJours(Stock stock) {
        if (stock.getDateDebut() != null && stock.getDateFin() != null) {
            LocalDate debut = stock.getDateDebut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fin = stock.getDateFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return ChronoUnit.DAYS.between(debut, fin);
        }
        return 0;
    }
}