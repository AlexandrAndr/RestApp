package ru.meschanov.rest.domains;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "car_number")
@Setter
@Getter
@NoArgsConstructor
public class CarNumberEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "number_car")
    private String numberCar;

    public CarNumberEntity(String numberCar) {
        this.numberCar = numberCar;
    }

}
