package com.rupesh.billing_system.controller;

import com.rupesh.billing_system.entities.Bill;
import com.rupesh.billing_system.service.BillService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/bill")
public class BillController {

    @Autowired
    private BillService billService;



    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody Bill bill){
        Bill bill1 = billService.createBill(bill);
        return ResponseEntity.status(HttpStatus.CREATED).body(bill1);

    }

    @GetMapping
    public ResponseEntity<List<Bill>> getAllBills(){
        List<Bill> getAllBills = billService.getAllBills();
        return  ResponseEntity.status(HttpStatus.OK).body(getAllBills);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable String id){
        Bill bill = billService.getBillById(id);
        return ResponseEntity.status(HttpStatus.OK).body(bill);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable String id) {
        billService.deleteBill(id);
        return ResponseEntity.noContent().build();

    }


    @GetMapping("/invoce/{id}")
    public ResponseEntity<Void> downloadInvoice(HttpServletResponse response ,@PathVariable String id)
            throws IOException {

        var resut= billService.generateInvoice(id);
        byte[] pdfReport = resut.toByteArray();
        String mimeType =  "application/pdf";
        response.setContentType(mimeType);

        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + "invoice.pdf"+"\""));

        response.setContentLength((int)pdfReport.length);

        InputStream inputStream = new ByteArrayInputStream(pdfReport);

        //Copy bytes from source to destination(outputstream in this example), closes both streams.
        FileCopyUtils.copy(inputStream, response.getOutputStream());
        return new ResponseEntity<Void>(HttpStatus.OK) ;
    }
}
