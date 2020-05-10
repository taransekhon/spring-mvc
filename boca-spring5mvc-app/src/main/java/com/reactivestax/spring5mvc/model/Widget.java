package com.reactivestax.spring5mvc.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "WIDGET")
public class Widget {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Pattern(regexp="[0-9][a-z][A-Z][-_]]{15,19}")
    @NotBlank(message = "name in request body is mandatory too")
    private String name;

    @NotBlank(message = "description in request body is mandatory too")
    private String description;

    public Widget() {
    }

    public Widget(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Widget(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
