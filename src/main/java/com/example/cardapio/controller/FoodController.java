package com.example.cardapio.controller;

import com.example.cardapio.food.Food;
import com.example.cardapio.food.FoodReponseDTO;
import com.example.cardapio.food.FoodRepository;
import com.example.cardapio.food.FoodRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Indica pro spring que essa classe é um controller
@RequestMapping("food") // Informa o endpoint que essa classe controla
public class FoodController {
    @Autowired // Diz pro spring que ele vai fazer a injecão dessa depedencia automaticamente
    private FoodRepository repository; // instancia do repository

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void saveFood(@RequestBody FoodRequestDTO data){
        Food foodData = new Food(data);
        repository.save(foodData);
        return;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping // só colocar um método não é suficiente para o spring saber quando esse método tem que ser chamado, usamos o Getmapping pra dizer que esse método deve ser chamado quando esse endpoint for chamado com o método get
    public List<FoodReponseDTO> getAll(){
        List<FoodReponseDTO> foodList = repository.findAll().stream().map(FoodReponseDTO::new).toList();
        return foodList;
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping
    @Transactional
    public ResponseEntity upgradeFood(@RequestBody FoodReponseDTO data){
        Optional<Food> optionalFood = repository.findById(data.id());
        if (optionalFood.isPresent()) {
            Food foodData = optionalFood.get();
            foodData.setTitle(data.title());
            foodData.setImage(data.image());
            foodData.setPrice(data.price());
            return ResponseEntity.ok(foodData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteFood(@PathVariable("id") Long id){
        Optional<Food> foodDelete = repository.findById(id);
        if (foodDelete.isPresent()){
            repository.delete(foodDelete.get());
            return ResponseEntity.noContent().build();
        }else {
            throw new EntityNotFoundException();
        }
    }

}
