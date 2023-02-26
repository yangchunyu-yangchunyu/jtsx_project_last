package com.example.demo.controller;


import com.example.demo.mapper.BookMapper;
import com.example.demo.service.BookService;
import com.example.demo.result.Result;
import com.example.demo.entity.Book;
import net.coobird.thumbnailator.Thumbnails;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
public class BookController {
    private String  file_name="";
    @Resource(name = "bookService")
    private BookService bookService;

    //返回所有的图书
    @CrossOrigin
    @GetMapping("api/books")
    public List<Book> findAll() throws Exception {
        return bookService.findAll();
    }

    //根据用户账号返回所有的书
    @CrossOrigin
    @PostMapping("api/booksByUid")
    public List<Book> searchBooksByUid(@RequestParam Map<String, Object> keywords, HttpSession session) {
        Long uid=Long.parseLong(keywords.get("uid").toString());
        System.out.println(uid);
        return bookService.findAllByUid(uid);
    }

    //根据bookid返回书
    @CrossOrigin
    @PostMapping("api/bookById")
    public Book searchBookByid(@RequestParam Map<String, Object> keywords, HttpSession session) {
        Long id=Long.parseLong(keywords.get("id").toString());
        System.out.println(id);
        return bookService.findBookByid(id);
    }

    //根据种类查询图书
    @CrossOrigin
    @PostMapping("api/booksByCategory")
    public List<Book> listByCategory(@RequestParam Map<String, Object> keywords) throws Exception {
        String category=keywords.get("category").toString();
        if (!category.equals("")) {
            return bookService.findAllByCategory(category);
        } else {
            return findAll();
        }
    }

    //根据用户账号删除所有的书
    @CrossOrigin
    @PostMapping("api/deleteBooksByUid")
    public Result deleteBooksByUid(@RequestParam Map<String, Object> keywords, HttpSession session) {
        Long uid=Long.parseLong(keywords.get("uid").toString());
        System.out.println(uid);
        List<Book> deletebooks=bookService.findAllByUid(uid);
        for (Book deletebook : deletebooks) {
            if (!deletebook.getImg().equals("")) {
                //String img_1 = book.getImg().split("/api/file/")[1];
                deleteImg(deletebook.getImg());
            }
        }
        bookService.deleteAllByUid(uid);
        return new Result(200);
    }


    //根据id删除图书
    @CrossOrigin
    @PostMapping("api/deleteBookById")
    public Result deleteBookById(@RequestParam Map<String, Object> keywords) {
        Long id=Long.parseLong(keywords.get("id").toString());
        System.out.println(id);
        Book deleteBook=bookService.findBookByid(id);
        int num = bookService.deleteBookById(id);
        if (num == 0) {
            return new Result(400);
        } else {
            if (!deleteBook.getImg().equals("")) {
                //String img_1 = book.getImg().split("/api/file/")[1];
                deleteImg(deleteBook.getImg());
            }
            return new Result(200);
        }
    }




    //添加一本图书
    @CrossOrigin
    @PostMapping("api/insertBook")
    //注意这里是RequestBody 是post body json
    public Result insertBook(@RequestBody Book book) {
        book.setImg(file_name);
        System.out.println(book);
        if (bookService.insertBook(book) == 0) {
            return new Result(400);
        } else {
            return new Result(200);
        }
    }

    //根据书名查找图书
    //使用form表单传递数据
    @CrossOrigin
    @PostMapping("api/searchBookByName")
    public List<Book> searchBooksByTitle(@RequestParam Map<String, Object> keywords, HttpSession session) {
        return bookService.finAllByTitle((String) keywords.get("name"));
    }

    //上传图片1
    @CrossOrigin
    @PostMapping("api/uploadImg")
    public String coversUpload(MultipartFile file) throws Exception {
        //查看图片类型
        //System.out.println("++"+file);
        String type = file.getContentType().split("/")[1];
        if (type == null || file.getSize() > 1048576) {
            return null;
        }
        String folder = "/home/bookstore-imgs";
        File imageFolder = new File(folder);
        File f = new File(imageFolder, getRandomString(10) + file.getOriginalFilename()
                .substring(file.getOriginalFilename().length() - 4));
        if (!f.getParentFile().exists())
            //如果不存在文件则创建文件夹
            f.getParentFile().mkdirs();
        try {
            //存入图片
            file.transferTo(f);
            //对存入的图片进行压缩,并转换为jpeg格式
            Thumbnails.of(f)
                    .outputFormat("jpg")
                    .scale(0.5f)//图片比例压缩
                    .outputQuality(0.5f)//图片清晰度压缩
                    .toFile(f.getPath().split("\\.")[0] + ".jpg");
            if (!type.equals("jpeg")) {
                //删除图片
                f.delete();
            }
            String imgURL = "http://118.25.61.247:8445/api/file/" + f.getName().split("\\.")[0] + ".jpg";
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    //上传图片2
    @RequestMapping(value = "api/uploadImg2")
    @ResponseBody
    public String upload( MultipartFile file, HttpServletRequest request) throws JSONException {
        Calendar currTime = Calendar.getInstance();
        String time = String.valueOf(currTime.get(Calendar.YEAR))+String.valueOf((currTime.get(Calendar.MONTH)+1));
        String path ="d:"+File.separator+"img"+File.separator+time;
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        suffix = suffix.toLowerCase();
        if(suffix.equals(".jpg") || suffix.equals(".jpeg") || suffix.equals(".png") || suffix.equals(".gif")){
            String fileName = UUID.randomUUID().toString()+suffix;
            File targetFile = new File(path, fileName);
            if(!targetFile.getParentFile().exists()){    //注意，判断父级路径是否存在
                targetFile.getParentFile().mkdirs();
            }
            long size = 0;
            //保存
            try {
                file.transferTo(targetFile);
                size = file.getSize();
            } catch (Exception e) {
                e.printStackTrace();
            }
            JSONObject result = new JSONObject();
            result.put("fileUrl", "/img/"+time+fileName);
            result.put("url", "/img/"+time+fileName);
            result.put("state", "SUCCESS");
            result.put("title", fileName);
            result.put("original", fileName);
            result.put("type", suffix);
            result.put("size", size);
            return result.toString();
        }else{
            JSONObject result = new JSONObject();
            result.put("ss", false);
            result.put("msg", "格式不支持");
            return result.toString();
        }

    }

    //上传图片3 使用
    @CrossOrigin
    @RequestMapping(value = "api/uploadImg3")
    @ResponseBody
    public Object upload(MultipartFile fileUpload){
        //获取文件名
        String fileName = fileUpload.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名
        fileName = UUID.randomUUID()+suffixName;
        //指定本地文件夹存储图片，写到需要保存的目录下
        String filePath = "E:\\idea_workspace\\jtsx_project_last\\src\\main\\resources\\static\\img\\";
        try {
            //将图片保存到static文件夹里
            fileUpload.transferTo(new File(filePath+fileName));
            file_name=fileName;
            //返回提示信息
            return new Result(200);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400);
        }
    }

    //删除图片
    @CrossOrigin
    @PostMapping("api/deleteImg")
    public void deleteImg(@RequestParam Map<String, Object> requestMap) {
//        String imgName = requestMap.get("imgUrl").toString().split("/api/file/")[1];
        String imgName = requestMap.get("imgUrl").toString();
        System.out.println(imgName);
        deleteImg(imgName);
    }

    @CrossOrigin
    public void deleteImg(String imgName) {
        String imgUrl = "E:\\idea_workspace\\jtsx_project_last\\src\\main\\resources\\static\\img\\" + imgName;
        File img = new File(imgUrl);
        //System.out.println(imgName);
        img.delete();
    }

    //生成length个随机数组成的字符串
    public String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
