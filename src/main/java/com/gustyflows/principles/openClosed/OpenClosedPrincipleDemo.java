package com.gustyflows.principles.openClosed;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//OCP + Specification pattern
public class OpenClosedPrincipleDemo {

    public static void main(String[] args) {
        List<Product> products = getProducts();
//
//        ProductFilterViolatesOCP productFilterViolatesOCP = new ProductFilterViolatesOCP();
//        System.out.println("Green products (old):");
//
//        productFilterViolatesOCP.filterByColour(products, Colour.GREEN)
//                .forEach(p -> System.out.println(" - " + p.getName() + " is green"));


        ProductsFilter productsFilter = new ProductsFilter();
        System.out.println("Green products (new):");
        ColorSpecification colorSpecification = new ColorSpecification(Colour.GREEN);
        SizeSpecification sizeSpecification = new SizeSpecification(Size.LARGE);

        productsFilter.filter(products, sizeSpecification).forEach(p -> System.out.println(" - " + p.getName() + " is LARGE"));
        productsFilter.filter(products, colorSpecification).forEach(p -> System.out.println(" - " + p.getName() + " is GREEN"));

        System.out.println("LARGE AND BLUE items:");
        AndSpecification<Product> colourAndSize = new AndSpecification<>(colorSpecification, sizeSpecification);
        productsFilter.filter(products, colourAndSize).forEach(p -> System.out.println(p.getName() + " is LARGE and BLUE"));
    }

    public static List<Product> getProducts() {
        Product apple = new Product("Apple", Colour.GREEN, Size.SMALL);
        Product tree = new Product("Tree", Colour.GREEN, Size.LARGE);
        Product house = new Product("House", Colour.BLUE, Size.LARGE);
        List<Product> products = List.of(apple, tree, house);
        return products;
    }

}

//I want a website that contains products and lets users filter these by var criteria:

enum Colour {
    RED, GREEN, BLUE
}

enum Size {
    SMALL, MEDIUM, LARGE, EXTRA_LARGE
}

class Product {
    private String name;
    private Colour colour;
    private Size size;

    public Product(String name, Colour colour, Size size) {
        this.name = name;
        this.colour = colour;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public Colour getColor() {
        return colour;
    }

    public Size getSize() {
        return size;
    }
}

class ProductFilterViolatesOCP {
    //first a request to filter by color
    public List<Product> filterByColour(List<Product> products, Colour colour) {
        return products.stream().filter(p -> p.getColor() == colour).collect(Collectors.toList());
    }

    //next a request to filter by size - OCP: open for extension but closed for modification..
    //we perform modification here, hence violation
    public List<Product> filterBySize(List<Product> products, Size size) {
        return products.stream().filter(p -> p.getSize() == size).collect(Collectors.toList());
    }

    public List<Product> filterBySizeAndColour(List<Product> products, Size size, Colour colour) {
        return products.stream().filter(p -> {
            return p.getSize() == size && p.getColor() == colour;
        }).collect(Collectors.toList());
    }

    //as criteria increases, this becomes unmanageable..
    //also we should not be jumping into code that is already written
}

//A better approach would be:
//T generic -> can be anything, not only Product

interface Specification<T> {
    boolean isSatisfied(T item);
}

interface Filter<T> {
    List<T> filter(List<T> items, Specification<T> specification);
}

class ColorSpecification implements Specification<Product> {

    private Colour colour;

    public ColorSpecification(Colour colour) {
        this.colour = colour;
    }

    @Override
    public boolean isSatisfied(Product item) {
        return item.getColor() == colour;
    }

}

class SizeSpecification implements Specification<Product> {
    private Size size;

    public SizeSpecification(Size size) {
        this.size = size;
    }

    @Override
    public boolean isSatisfied(Product item) {
        return item.getSize() == size;
    }
}

class AndSpecification<T> implements Specification<T> {
    private Specification<T> first, second;

    public AndSpecification(Specification<T> first, Specification<T> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean isSatisfied(T item) {
        return first.isSatisfied(item) && second.isSatisfied(item);
    }
}

class ProductsFilter implements Filter<Product> {

    @Override
    public List<Product> filter(List<Product> products, Specification<Product> productSpecification) {
        return products.stream()
                .filter(p -> productSpecification.isSatisfied(p))
                .collect(Collectors.toList());
    }
}

//further improvements
class CombinedSpecification<T> implements Specification<T> {
    private final List<Specification<T>> specifications;

    public CombinedSpecification(Specification<T>... specifications) {
        this.specifications = Arrays.asList(specifications);
    }

    @Override
    public boolean isSatisfied(T item) {
        return specifications.stream().allMatch(spec -> spec.isSatisfied(item));
    }
}

class OpenClosedPrincipleDemo2 {
    public static void main(String[] args) {
        List<Product> products = OpenClosedPrincipleDemo.getProducts();

        ProductsFilter productsFilter = new ProductsFilter();

        CombinedSpecification<Product> productCombinedSpecification =
                new CombinedSpecification<>(
                        new SizeSpecification(Size.LARGE),
                        new ColorSpecification(Colour.GREEN));

        productsFilter.filter(products, productCombinedSpecification)
                .forEach(p -> System.out.println(p.getName() + " is " + p.getSize() + " and " + p.getColor()));

    }
}






