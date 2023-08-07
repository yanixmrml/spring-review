package com.example.cashcard.controller;

import com.example.cashcard.model.CashCard;
import com.example.cashcard.repository.CashCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/cashcards")
public class CashCardController {

    @Autowired
    private CashCardRepository cashCardRepository;

    @GetMapping("")
    public ResponseEntity<List<CashCard>> getCashCardList(){
        List<CashCard> cashCardList = new ArrayList<>();
        this.cashCardRepository.findAll().forEach(cashCardList::add);
        return ResponseEntity.ok(cashCardList);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<CashCard> getCashCard(@PathVariable("requestId") Long requestId){
        Optional<CashCard> cashCardOptional = this.cashCardRepository.findById(requestId);
        if(cashCardOptional.isPresent()){
            return ResponseEntity.ok(cashCardOptional.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<CashCard> createCashCard(@RequestBody CashCard cashCard){
        CashCard newCashCard = new CashCard(null,cashCard.getAmount());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.cashCardRepository.save(newCashCard));
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<CashCard> updateCashCard(@RequestBody CashCard cashCard,
                                            @PathVariable("requestId") Long requestId){
        Optional<CashCard> oldCashCardOpt = this.cashCardRepository.findById(requestId);
        if(oldCashCardOpt.isPresent()){
            cashCard.setId(oldCashCardOpt.get().getId());
            return ResponseEntity.ok(this.cashCardRepository.save(cashCard));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> deleteCashCard(@PathVariable("requestId") Long requestId){
        Optional<CashCard> oldCashCardOpt = this.cashCardRepository.findById(requestId);
        if(oldCashCardOpt.isPresent()){
            this.cashCardRepository.delete(oldCashCardOpt.get());
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
