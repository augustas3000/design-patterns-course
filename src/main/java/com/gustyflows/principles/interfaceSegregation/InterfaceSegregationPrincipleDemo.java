package com.gustyflows.principles.interfaceSegregation;

//a recommendation of how to split interfaces into smaller interfaces

import javax.print.Doc;

public class InterfaceSegregationPrincipleDemo {

}

class Document {

}

interface Machine {
    void print(Document document);
    void fax(Document document);
    void scan(Document document);
}

class MultiFunctionPrinter implements Machine {
    @Override
    public void print(Document document) {

    }

    @Override
    public void fax(Document document) {

    }

    @Override
    public void scan(Document document) {

    }
}

//now the old fashioned printer class is forced to implement all 3 interface methods
//altough it only needs one
class OldFashionedPrinter implements Machine {
    @Override
    public void print(Document document) {

    }

    @Override
    public void fax(Document document) {
        //leave unimplemented or throw exceptions - problematic
    }

    @Override
    public void scan(Document document) {
        //leave unimplemented or throw exceptions - problematic
    }
}

// Interface segregation -> you should not put into a single interface more than the client is expected to implement - put apart, split interface


interface Printer {
    void print(Document document);
}

interface Fax {
    void fax(Document document);
}

interface Scanner {
    void scan(Document document);
}


//YAGNI - You ain't going to need it - supports the idea of interface segregation
class OrdinaryPrinter implements Printer {
    @Override
    public void print(Document document) {

    }
}

class MultiPrinter implements Printer, Scanner {
    @Override
    public void print(Document document) {

    }

    @Override
    public void scan(Document document) {

    }
}

//or an interface that extends both required interfaces
interface MultiFunctionDevice extends Printer, Scanner {

}

class MultiFuncPrinter implements MultiFunctionDevice {

    //decoration pattern can be used here:
    private Printer printer;
    private Scanner scanner;

    public MultiFuncPrinter(Printer printer, Scanner scanner) {
        this.printer = printer;
        this.scanner = scanner;
    }

    @Override
    public void print(Document document) {
        printer.print(document);
    }

    @Override
    public void scan(Document document) {
        scanner.scan(document);
    }
}

