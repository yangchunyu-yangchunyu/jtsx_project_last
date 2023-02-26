package com.example.demo.mapper;

import com.example.demo.entity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;


public interface BookMapper extends BaseMapper<Book> {
    //根据书的种类找到这一类型的所有书
    @Select("select * from book\n" +
            "where book.category=#{_parameter}")
    List<Book> findAllByCategory(String category);


    ////根据书的种类和用户账户找到这一类型的所有书
    @Select("Select * from book where book.category=#{param1} and book.userid=#{param2}")
    List<Book> findAllByCategoryAndUid(String category, Long uid);


    //根据书名模糊找到书
    @Select("select * from book\n" +
            "where book.bookname like '%${_parameter}%'")
    List<Book> findAllByBookName(String bookname);


    //找到所有的书
    @Select("select * from book")
    List<Book> findAllBooks();

    //根据用户账号返回所有的书
    @Select("Select * from book where userid=#{_parameter}")
    List<Book> findAllBooksByUid(Long uid);

    //根据用户账号删除所有的书
    @Select("delete  from book where userid=#{_parameter}")
    List<Book> deleteAllBooksByUid(Long uid);

    //根据id返回书
    @Select("Select * from book where id=#{_parameter}")
    Book findBooksByid(Long id);

    //根据id删除书籍
    @Delete("delete  from book where id=#{id}")
    int deleteBookById(Long id);

    //添加图书
    @Insert("insert into jtsx_bookstore.book(bookname,ISBN,price,newold,talk,userid,category,img,author)\n" +
            "values(#{param1},#{param2},#{param3},#{param4},#{param5},#{param6},#{param7},#{param8},#{param9})")
    int insertBook(String bookname,String ISBN,BigDecimal price,String newold,String talk,Long userid,String category,String img,String author);


}
