package ru.meschanov.rest.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "carNumber")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class CarNumberModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public CarNumberModel(String numberCar) {
        this.numberCar = numberCar;
    }

    private String numberCar;

    public String getNumberCar() {
        return numberCar;
    }

    public void setNumberCar(String numberCar) {
        this.numberCar = numberCar;
    }
}
