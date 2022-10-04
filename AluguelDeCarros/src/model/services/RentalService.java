package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
    Double pricepPerHour;
    Double pricePerDay;

    public BrazilTaxService taxService;

    public void RentalService(){

    }

    public RentalService(Double pricepPerHour, Double pricePerDay, BrazilTaxService taxService) {
        super();
        this.pricepPerHour = pricepPerHour;
        this.pricePerDay = pricePerDay;
        this.taxService=taxService;
    }

    public void processInvoice(CarRental carRental){
        long instant1 = carRental.getStart().getTime();
        long instant2 = carRental.getFinish().getTime();
        double hour = (double)(instant2 - instant1) / 1000 / 60 / 60;  //feito isso para transformar mili em horas.
        double basicPayment;
        if(hour <= 12.0){
            basicPayment = Math.ceil(hour) * pricepPerHour; //ceil serve para arredondar valores
        }
        else{
            basicPayment = Math.ceil(hour/24) * pricePerDay; //ceil serve para arredondar valores
        }

        double tax = taxService.tax(basicPayment);

        carRental.setInvoice(new Invoice(basicPayment, tax));
    }
}