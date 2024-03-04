// Specifies the package name which organizes the class into a module. It's a convention to prevent class name conflicts.
package com.gormless.programmingexercise.model;

// Import statements for dependencies used in this class.
import jakarta.persistence.*; // Imports all classes from the Jakarta Persistence API for database operations.
import java.math.BigDecimal; // Imports the BigDecimal class for precise decimal number representations, useful for currency.
@Entity
public class OrderItem {
    // Annotates the id field as the primary key of the entity.
    @Id
    // Specifies the strategy to generate primary key values automatically. IDENTITY relies on the database to generate a unique id.
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id; // Declares a field named id of type Long to store the unique identifier of the OrderItem.
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    // Specifies a many-to-one relationship between OrderItem entities and a Product entity.
    @ManyToOne
    // Defines the foreign key column named "product_id" in the OrderItem table that references a Product entity.
    @JoinColumn(name = "product_id")
    private Product product; // Declares a field named product of type Product to store the associated Product entity.
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    private int quantity; // Declares a field named quantity of type int to store the quantity of the Product ordered.
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    private BigDecimal price; // Declares a field named price of type BigDecimal to store the price of the Product when ordered.
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
