package com.rupesh.billing_system.repo;

import com.rupesh.billing_system.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepo extends JpaRepository<Bill,String> {
}
