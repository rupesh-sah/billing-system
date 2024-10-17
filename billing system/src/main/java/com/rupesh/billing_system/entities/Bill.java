package com.rupesh.billing_system.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bill")
public class Bill {
    @Id
    private String invoiceNumber;
    private String date;

    private String shopAdresh;
    private String shopName;
    private String shopPhoneNumber;

    private String customerAdress;
    private String customerName;
    private String customerPhoneNumber;




    private double totalAmount;
    private double sgst;
    private double cgst;
    private double totalTax;
    private double grandTotal;

    @ManyToMany
    private List<Item> items;
}

