package com.example.cardapio.food;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "foods") // Indica pro Spring que essa classe é a representacão de uma tabela do banco
@Entity(name = "foods") // Dá um nome a entidade
@Getter // pede pro lombok adiconar todos os metodos gett em run time
@Setter
@NoArgsConstructor // pede pro lombok declara um constructor que não recebe nenhum argumento
@AllArgsConstructor // pede pro lombok declara um constructor que recebe todos os argumentos
@EqualsAndHashCode(of="id")
public class Food {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // Incrementa o ID
    private Long id;
    private String title;
    private String image;
    private Integer price;

    public Food(FoodRequestDTO data){
        this.title = data.title();
        this.price = data.price();
        this.image = data.image();
    }
}
