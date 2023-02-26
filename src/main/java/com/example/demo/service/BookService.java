package com.example.demo.service;


import com.example.demo.mapper.BookMapper;
import com.example.demo.entity.Book;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service(value = "bookService")
public class BookService {
    @Resource(name = "bookMapper")
    private BookMapper bookMapper;

    /*******查询******/
    //返回所有的图书
    public List<Book> findAll() {
        return bookMapper.findAllBooks();
    }

    //根据用户id返回所有的书
    public List<Book> findAllByUid(Long uid) {
        return bookMapper.findAllBooksByUid(uid);
    }

    //根据用户id删除所有的书
    public List<Book> deleteAllByUid(Long uid) {
        return bookMapper.deleteAllBooksByUid(uid);
    }

    //根据id返回书
    public Book findBookByid(Long id) {
        return bookMapper.findBooksByid(id);
    }

    //根据书的种类返回所有的书
    public List<Book> findAllByCategory(String category) {
        return bookMapper.findAllByCategory(category);
    }

    //根据书的种类和用户账户返回所有的书
    public List<Book> findAllByCategoryAndUid(String category, long uid) {
        return bookMapper.findAllByCategoryAndUid(category, uid);
    }

    //根据书名找到所有的数据
    public List<Book> finAllByTitle(String bookname) {
        return bookMapper.findAllByBookName(bookname);
    }

    //根据id删除图书
    public int deleteBookById(Long id) {
        return bookMapper.deleteBookById(id);
    }

    //添加一本书
    public int insertBook(Book book) {
        return bookMapper.insertBook(book.getBookname(),book.getIsbn(),book.getPrice(),book.getNewold(),book.getTalk(),book.getUserid(),book.getCategory(),book.getImg(),book.getAuthor());
    }
}
