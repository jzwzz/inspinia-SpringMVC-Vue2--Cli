package com.cmbchina.microray.cli.book.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private String ISBN;
    private String name;
    private String author;

}
