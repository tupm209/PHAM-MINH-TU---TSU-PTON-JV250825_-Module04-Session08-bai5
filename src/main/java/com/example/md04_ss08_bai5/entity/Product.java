package com.example.md04_ss08_bai5.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String name;
    private Double price;
    private String imageName;
}
