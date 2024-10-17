package com.rupesh.billing_system.service.impl;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.rupesh.billing_system.entities.Bill;
import com.rupesh.billing_system.entities.Item;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class InvoicePdfService {



    String fontPath = "static/fonts/montserrat_egular.ttf";
    Color blackRgb = new DeviceRgb(0, 0, 0);
    Color grayRgb = new DeviceRgb(51, 57, 69);
    Color grayRgb2 = new DeviceRgb(218, 224, 226);


    public ByteArrayOutputStream generateInvoice(Bill bill) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Create a PdfWriter object to write to a file
        PdfWriter writer = new PdfWriter(baos);

        // Create a PdfDocument object to represent the PDF
        PdfDocument pdf = new PdfDocument(writer);

        // Create a Document object to add elements to the PDF
        Document document = new Document(pdf);
        Table topTable = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
        topTable.setBorder(Border.NO_BORDER);

        PdfFont montserratFont = PdfFontFactory.createFont(fontPath);


        DeviceRgb deviceRgb = new DeviceRgb(249, 190, 143);
        Color backgroundColor = deviceRgb; // Specify RGB values

        DeviceRgb blueRgb = new DeviceRgb(0, 0, 255);
        Color blueColor = blueRgb; // Specify RGB values

        DeviceRgb greenRgb = new DeviceRgb(34, 139, 34);
        Color greenColor = greenRgb; // Specify RGB values


        Image image = new Image(ImageDataFactory.create("https://bmsit.ac.in/public/assets/images/bmslogo.png"));
        image.setMargins(5, 0, 5, 0);
        image.scaleToFit(200f, 50f);
        Cell imageCell = new Cell();
        imageCell.add(image);
        imageCell.setBorder(Border.NO_BORDER); // Remove borders for this specific cell
        topTable.addCell(imageCell);
        document.add(topTable);


        Paragraph invoiceTitle1 = new Paragraph("Gst Invoice");

        invoiceTitle1.setTextAlignment(TextAlignment.LEFT);
        invoiceTitle1.setFontSize(12);
        invoiceTitle1.setFont(montserratFont);
        invoiceTitle1.setMarginTop(-5);
        document.add(invoiceTitle1);

        Table secondTable = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
        secondTable.setMarginTop(10);
        secondTable.setBorder(Border.NO_BORDER);
        setFomData(secondTable,bill, montserratFont);
        setDetailData(secondTable,bill, montserratFont);
        document.add(secondTable);

        Table thirdTable = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
        thirdTable.setMarginTop(10);
        thirdTable.setBorder(Border.NO_BORDER);
        setToData(thirdTable, bill, montserratFont);
        thirdTable.addCell(new Cell().setBorder(Border.NO_BORDER));
        document.add(thirdTable);

        Paragraph summeryTitle = new Paragraph("Summary");

        summeryTitle.setTextAlignment(TextAlignment.LEFT);
        summeryTitle.setFontSize(14);
        summeryTitle.setFont(montserratFont);
        summeryTitle.setMarginTop(8);
        document.add(summeryTitle);

        document.add(new Cell().setMargin(5));
        setLine(document, 0, 0);
        document.add(new Cell().setMargin(5));
//        setTotalUses(document, montserratFont);
//        document.add(new Cell().setMargin(5));
        setLine(document, 0, 0);

        setBillTitle(document, montserratFont);
        setLine(document, 0, 0);
        for (Item item : bill.getItems()) {
            setBillValue(document, item, montserratFont);
        }




        Paragraph totalDueTitle = new Paragraph("Total due");

        totalDueTitle.setTextAlignment(TextAlignment.LEFT);
        totalDueTitle.setFontSize(14);
        totalDueTitle.setFont(montserratFont);
        totalDueTitle.setBold();
        totalDueTitle.setMarginTop(8);
        document.add(totalDueTitle);
        setLine(document, 0, 0);

        document.add(new Cell().setMargin(5));
        setTotalDue(document, bill, montserratFont);
//        setTotalDueTitle(document,montserratFont);
//        setLine(document,0,0);
//        setTotalDueValue(document,montserratFont);

        document.close();


        return baos;
    }

    void setTotalUses(Document document, PdfFont montserratFont) {
        Table headerTable = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
        headerTable.setBorder(Border.NO_BORDER);

        String totalUsesTitle = "Total usage charges";


        headerTable.addCell(new Cell().add(new Paragraph(totalUsesTitle).setFont(montserratFont).setFontSize(8)
                .setFontColor(blackRgb).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

        headerTable.addCell(new Cell().add(new Paragraph("14,400.00 INR").setFont(montserratFont).setFontSize(8)
                .setFontColor(blackRgb).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
        document.add(headerTable);

    }

    void setTotalKeyAndValue(Document document, PdfFont montserratFont, String key, String value) {
        Table headerTable = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
        headerTable.setBorder(Border.NO_BORDER);

        headerTable.addCell(new Cell().add(new Paragraph(key).setFont(montserratFont).setFontSize(8)
                .setFontColor(blackRgb).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

        headerTable.addCell(new Cell().add(new Paragraph(value).setFont(montserratFont).setFontSize(8)
                .setFontColor(blackRgb).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
        document.add(headerTable);

    }

    void setLine(Document document, float right, float left) {
        Table headerTable = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();
        headerTable.setBorder(new SolidBorder(grayRgb2, 0.1f)); // Set border color and width
        headerTable.setMargins(0, right, 0, left);
        document.add(headerTable);
    }

    void setBillTitle(Document document,  PdfFont montserratFont) {
        Table headerTable = new Table(UnitValue.createPercentArray(5)).useAllAvailableWidth();
        headerTable.setBorder(Border.NO_BORDER);
        headerTable.setMarginTop(20);

        headerTable.addCell(new Cell().add(new Paragraph("Description").setFont(montserratFont).setFontSize(8).setBold()
                .setFontColor(grayRgb).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));

        headerTable.addCell(new Cell().add(new Paragraph("HSN/SAC").setFont(montserratFont).setFontSize(8).setBold()
                .setFontColor(grayRgb).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));

        headerTable.addCell(new Cell().add(new Paragraph("Rate").setFont(montserratFont).setFontSize(8).setBold()
                .setFontColor(grayRgb).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));


        headerTable.addCell(new Cell().add(new Paragraph("Per").setFont(montserratFont).setFontSize(8).setBold()
                .setFontColor(grayRgb).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));


        headerTable.addCell(new Cell().add(new Paragraph("Amount").setFont(montserratFont).setFontSize(8).setBold()
                .setFontColor(grayRgb).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));

        document.add(headerTable);
    }

    void setTotalDue(Document document, Bill bill, PdfFont montserratFont) {
        setTotalKeyAndValue(document, montserratFont, "Total", String.valueOf(bill.getTotalAmount()));
        setTotalKeyAndValue(document, montserratFont, "Sgst", String.valueOf(bill.getSgst()));
        setTotalKeyAndValue(document, montserratFont, "Cgst", String.valueOf(bill.getCgst()));
        setTotalKeyAndValue(document, montserratFont, "totalTax", String.valueOf(bill.getTotalTax()));
//        setTotalKeyAndValue(document, montserratFont, "grandTotal", String.valueOf(bill.getGrandTotal()));


        Table headerTable = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
        headerTable.setBorder(Border.NO_BORDER);

        headerTable.addCell(new Cell().add(new Paragraph("Grand Total").setFont(montserratFont).setFontSize(8)
                .setFontColor(blackRgb).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

        headerTable.addCell(new Cell().add(new Paragraph(String.valueOf(bill.getGrandTotal())).setFont(montserratFont).setFontSize(8).setBold()
                .setFontColor(blackRgb).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
        document.add(headerTable);
    }

    void setBillValue(Document document, Item item, PdfFont montserratFont) {
        Table headerTable = new Table(UnitValue.createPercentArray(5)).useAllAvailableWidth();
        headerTable.setBorder(Border.NO_BORDER);

        headerTable.addCell(new Cell().add(new Paragraph(item.getDescription()).setFont(montserratFont).setFontSize(8)
                .setFontColor(blackRgb).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));

        headerTable.addCell(new Cell().add(new Paragraph(item.getHsn()).setFont(montserratFont).setFontSize(8)
                .setFontColor(blackRgb).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));

        headerTable.addCell(new Cell().add(new Paragraph(String.valueOf(item.getRate())).setFont(montserratFont).setFontSize(8)
                .setFontColor(blackRgb).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));

        headerTable.addCell(new Cell().add(new Paragraph(String.valueOf(item.getQuantity())).setFont(montserratFont).setFontSize(8)
                .setFontColor(blackRgb).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));

        headerTable.addCell(new Cell().add(new Paragraph(String.valueOf(item.getQuantity()* item.getRate())).setFont(montserratFont).setFontSize(8)
                .setFontColor(blackRgb).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));

        document.add(headerTable);
    }

    void setFomData(Table table, Bill bill, PdfFont montserratFont) {

        List fromList = new List();
        fromList.setListSymbol("");

        ListItem fromTitle = new ListItem("From");
        ;

        fromTitle.setTextAlignment(TextAlignment.LEFT);
        fromTitle.setFontSize(9);
        fromTitle.setBold();
        fromTitle.setFontColor(grayRgb);
        fromTitle.setFont(montserratFont);
        fromList.add(fromTitle);


        ListItem companyTitle = new ListItem(bill.getShopName());
        companyTitle.setTextAlignment(TextAlignment.LEFT);
        companyTitle.setFontSize(8);
        companyTitle.setFont(montserratFont);
        fromList.add(companyTitle);


        ListItem addressTitle = new ListItem(bill.getShopAdresh());
        addressTitle.setTextAlignment(TextAlignment.LEFT);
        addressTitle.setFontSize(8);
        addressTitle.setFont(montserratFont);
        addressTitle.setMarginRight(100);
        fromList.add(addressTitle);


        ListItem phoneTitle = new ListItem(bill.getShopPhoneNumber());
        phoneTitle.setTextAlignment(TextAlignment.LEFT);
        phoneTitle.setFontSize(8);
        phoneTitle.setFont(montserratFont);
        phoneTitle.setMarginRight(100);
        fromList.add(phoneTitle);

        Cell fromDetailCell = new Cell();
        fromDetailCell.add(fromList);
        fromDetailCell.setBorder(Border.NO_BORDER); // Remove borders for this specific cell
        table.addCell(fromDetailCell);
    }

    void setToData(Table table, Bill bill, PdfFont montserratFont) {

        List toList = new List();
        toList.setListSymbol("");

        ListItem fromTitle = new ListItem("To");

        fromTitle.setTextAlignment(TextAlignment.LEFT);
        fromTitle.setFontSize(9);
        fromTitle.setBold();
        fromTitle.setFontColor(grayRgb);
        fromTitle.setFont(montserratFont);
        toList.add(fromTitle);


        ListItem companyTitle = new ListItem(bill.getCustomerName());
        companyTitle.setTextAlignment(TextAlignment.LEFT);
        companyTitle.setFontSize(9);
        companyTitle.setFont(montserratFont);
        toList.add(companyTitle);


        ListItem addressTitle = new ListItem(bill.getCustomerAdress());
        addressTitle.setTextAlignment(TextAlignment.LEFT);
        addressTitle.setFontSize(8);
        addressTitle.setFont(montserratFont);
        addressTitle.setMarginRight(100);
        toList.add(addressTitle);

//        ListItem emailTitle = new ListItem("testemail@test.com");
//        emailTitle.setTextAlignment(TextAlignment.LEFT);
//        emailTitle.setFontSize(8);
//        emailTitle.setFont(montserratFont);
//        toList.add(emailTitle);


        ListItem phoneNumber = new ListItem(bill.getCustomerPhoneNumber());
        phoneNumber.setTextAlignment(TextAlignment.LEFT);
        phoneNumber.setFontSize(8);
        phoneNumber.setFont(montserratFont);
        phoneNumber.setMarginRight(100);
        toList.add(phoneNumber);


        Cell toDetailCell = new Cell();
        toDetailCell.add(toList);
        toDetailCell.setBorder(Border.NO_BORDER); // Remove borders for this specific cell
        table.addCell(toDetailCell);
    }

    void setDetailData(Table table, Bill bill, PdfFont montserratFont) {
        List invoiceDetailList = new List();
        invoiceDetailList.setListSymbol("");

        ListItem detailsTitle = new ListItem("Details");
        ;
        detailsTitle.setTextAlignment(TextAlignment.LEFT);
        detailsTitle.setFontSize(9);
        detailsTitle.setBold();
        detailsTitle.setFontColor(grayRgb);
        detailsTitle.setFont(montserratFont);
        invoiceDetailList.add(detailsTitle);


        Cell invoiceDetailListCell = new Cell();
        invoiceDetailListCell.add(invoiceDetailList);
        invoiceDetailListCell.setBorder(Border.NO_BORDER); // Remove borders for this specific cell
        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
//        String formattedDate = formatter.format(date);
//        System.out.println(formattedDate);
        setDetailKeyAndValue(invoiceDetailListCell, montserratFont, "Date: ", bill.getDate());
        setDetailKeyAndValue(invoiceDetailListCell, montserratFont, "Invoice No: ", bill.getInvoiceNumber());
        table.addCell(invoiceDetailListCell);
    }

    void setDetailKeyAndValue(Cell invoiceDetailListCell, PdfFont montserratFont, String key, String value) {
        Table headerTable = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
        headerTable.setBorder(Border.NO_BORDER);

        headerTable.addCell(new Cell().add(new Paragraph(key).setFont(montserratFont).setFontSize(8)
                .setFontColor(blackRgb).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

        headerTable.addCell(new Cell().add(new Paragraph(value).setFont(montserratFont).setFontSize(8)
                .setFontColor(blackRgb).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
        invoiceDetailListCell.add(headerTable);

    }


}
