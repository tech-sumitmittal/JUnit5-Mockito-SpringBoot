package com.sumit.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {

    @Id
    @Column(name = "customer_id", nullable = false, updatable = false)
    private Long customerId;

    private String name;

    private String email;

    private String mobileNumber;

    // Optional: list of accounts
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference                       //serialized normally
    private List<Account> accounts;


}