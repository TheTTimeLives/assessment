package com.gormless.programmingexercise.model; // Defines the package of this class.

import jakarta.persistence.*; // Imports JPA annotations from Jakarta Persistence.

import java.math.BigDecimal; // Imports the BigDecimal class for handling monetary values.
import java.util.List; // Imports the List interface for handling collections of objects.
@Entity
@Table(name = "\"order\"") // the requirements for the assessment is a table named "order" but this a protected keyword in SQL
public class Order {
    @Id // Marks this field as the primary key of the entity.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies that the ID should be generated automatically by the database, using the identity column feature.
    private Long id; // The unique identifier for each order.
    public Long getId() {
        return id;
    }
    // just for tests
    public void setId(Long id) {
        this.id = id;
    }

    // The @ManyToOne annotation is incorrectly used here. It should be @OneToMany to indicate that one order can have many order items.
    @OneToMany(mappedBy = "id") // Indicates a one-to-many relationship between this order and multiple order items.
    private List<OrderItem> orderItems; // A list of order items associated with this order.
    public List<OrderItem> getOrderItems () {
        return orderItems;
    }
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    private int quantity; // This field seems to be misplaced. Typically, the quantity is specified within each OrderItem, not the Order itself.
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    private BigDecimal price; // This might represent the total price of the order. However, it's usually calculated from the order items, not stored directly.
    public BigDecimal getPrice() {
        return this.price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
