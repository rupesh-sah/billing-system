package com.rupesh.billing_system.service.impl;

import com.rupesh.billing_system.entities.Bill;
import com.rupesh.billing_system.entities.Item;
import com.rupesh.billing_system.repo.BillRepo;
import com.rupesh.billing_system.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class Bill_Impl implements BillService {

    @Autowired
    private BillRepo billRepo;

    @Autowired
    private InvoicePdfService invoicePdfService;

    public Bill createBill(Bill bill){
        double finalPrice = 0;
        for (Item item : bill.getItems()) {
           double price =  item.getQuantity()* item.getRate();
            finalPrice += price;
        }
        bill.setTotalAmount(finalPrice);
        bill.setGrandTotal(bill.getTotalAmount()+bill.getSgst()+bill.getCgst()+bill.getTotalTax());
        return billRepo.save(bill);
    }

    @Override
    public List<Bill> getAllBills() {
        return billRepo.findAll();
    }

    @Override
    public Bill getBillById(String id) {
        return billRepo.findById(id).orElseThrow(()-> new  RuntimeException("User not found"));
    }

    @Override
    public Bill updateBill(Bill bill) {
        return null;
    }

    @Override
    public void deleteBill(String billId) {
        billRepo.deleteById(billId);
    }

    @Override
    public ByteArrayOutputStream generateInvoice(String id) throws IOException {
        Bill bill = getBillById(id);
        return invoicePdfService.generateInvoice(bill);
    }


}
