package com.example.cardapio.controller;

import com.example.cardapio.food.Food;
import com.example.cardapio.food.FoodReponseDTO;
import com.example.cardapio.food.FoodRepository;
import com.example.cardapio.food.FoodRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica pro spring que essa classe é um controller
@RequestMapping("food") // Informa o endpoint que essa classe controla
public class FoodController {
    @Autowired // Diz pro spring que ele vai fazer a injecão dessa depedencia
    private FoodRepository repository; // instancia do repository

    @PostMapping
    public void saveFood(@RequestBody FoodRequestDTO data){
        Food foodData = new Food(data);
        repository.save(foodData);
        return;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping // só colocar um método não é suficiente para o spring saber quando esse método tem que ser chamado, usamos o Getmapping pra dixer que esse método deve ser chamado quando esse endpoint for chamado com o método get
    public List<FoodReponseDTO> getAll(){
        List<FoodReponseDTO> foodList = repository.findAll().stream().map(FoodReponseDTO::new).toList();
        return foodList;
    }
}
