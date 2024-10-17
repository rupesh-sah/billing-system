package com.rupesh.billing_system.service;

import com.rupesh.billing_system.entities.Bill;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
@Service
public interface BillService {
    Bill createBill(Bill bill);

    List<Bill> getAllBills();

    Bill getBillById(String id);

    Bill updateBill(Bill bill);

    void deleteBill(String billId);

    ByteArrayOutputStream generateInvoice(String id) throws IOException;
}
