package com.cmbchina.microray.cli.book.controller;

import com.cmbchina.microray.cli.book.entity.Book;
import com.cmbchina.microray.cli.common.ResponseResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("book")
@PreAuthorize("hasRole('ROLE_USER')")
public class BookController {

    @RequestMapping("list")
    public ResponseResult<List<Book>> listBook() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("aaa", "bbb", "ccc"));
        bookList.add(new Book("ddd", "eee", "fff"));
        bookList.add(new Book("ggg", "hhh", "jjj"));
        return new ResponseResult<>(bookList);
    }
}
