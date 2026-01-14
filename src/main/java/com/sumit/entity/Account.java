package com.sumit.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEntity {

    @Id
    @Column(name = "account_number", nullable = false, updatable = false)
    private Long accountNumber;

    private String accountType;

    private String branchAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")   // foreign key column
    @JsonBackReference                  // ignored during serialization to avoid recursion.
    private Customer customer;


}