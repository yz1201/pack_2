package cn.dbdj1201.bookmark.service.impl;

import cn.dbdj1201.bookmark.entity.Bookmark;
import cn.dbdj1201.bookmark.entity.vo.BookmarkFolderVo;
import cn.dbdj1201.bookmark.mapper.BookmarkMapper;
import cn.dbdj1201.bookmark.service.IBookmarkService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * <p>
 * 书签 服务实现类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-11
 */
@Service
@Slf4j
public class BookmarkServiceImpl extends ServiceImpl<BookmarkMapper, Bookmark> implements IBookmarkService {

    @Override
    public void resolve(MultipartFile file) {
        /*
       解析上传的json文件，并入库
        */
//        InputStream inputStream = file.getInputStream();
//        Bookmark bookmark = new Bookmark();
//
//        try {
//            Object parseObject = JSON.parseObject(file.getInputStream(), Bookmark.class, Feature.AllowArbitraryCommas);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public void activeLocalBookmark() {
        String jsonText = BookmarkServiceImpl.readNIO();
        JSONObject jsonObject = JSONObject.parseObject(jsonText);
        Object root = jsonObject.get("root");


    }


    public static void main(String[] args) {
        Bookmark bookmark = new Bookmark();
        bookmark.setChildren(new ArrayList<>());
        String jsonText = BookmarkServiceImpl.readNIO();
        buildBookmarkTree(jsonText);
    }

    public static Bookmark buildBookmarkTree(String json) {
        Bookmark bookmark = new Bookmark();
        ArrayList<Bookmark> childrenArray = new ArrayList<>();

        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONObject root = jsonObject.getJSONObject("roots");
        JSONObject bookmarkBar = root.getJSONObject("bookmark_bar");

        Long date_added = (Long) bookmarkBar.get("date_added");
        Long date_modified = (Long) bookmarkBar.get("date_modified");
        String name = (String) bookmarkBar.get("name");
        String guid = (String) bookmarkBar.get("guid");
        String id = (String) bookmarkBar.get("id");
        Integer type = (Integer) bookmarkBar.get("type");

        bookmark.setDateAdded(LocalDateTimeUtil.of(DateUtil.date(date_added)));
        bookmark.setDateModified(LocalDateTimeUtil.of(DateUtil.date(date_modified)));
        bookmark.setGuid(guid);
        bookmark.setId(id);
        bookmark.setType(type);
        bookmark.setName(name);
        //顶级书签层
        bookmark.setPid("0");

        /*
        填充下级书签列表
         */

        JSONArray children = bookmarkBar.getJSONArray("children");

        if (children != null) {

            children.forEach(child -> {




            });
        }


        bookmark.setChildren(childrenArray);
//        System.out.println(date_added);
//        System.out.println(date_modified);
//        System.out.println(name);
//        System.out.println(guid);
//        System.out.println(id);
//        System.out.println(type);


//        JSONArray children = bookmark_bar.getJSONArray("children");


//        children.forEach(c -> {
//            JSONArray children1 = ((JSONObject) c).getJSONArray("children");
//            children1.forEach(c2 -> {
//                System.out.println("----------------------------------");
//                Bookmark parse = (Bookmark) JSONObject.parse(((JSONObject) c2).toJSONString());
//                System.out.println(parse);
//            });
//        });


        return null;
    }


    /**
     * 读取本地书签文件
     */
    private static String readNIO() {
        FileInputStream fin = null;
        StringBuilder jsonText = new StringBuilder();
        try {
            fin = new FileInputStream(new File("C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Bookmarks"));
            FileChannel channel = fin.getChannel();

            int capacity = 1024;// 字节
            ByteBuffer bf = ByteBuffer.allocate(capacity);
            int length;
            while ((length = channel.read(bf)) != -1) {
                /*
                 * 注意，读取后，将位置置为0，将limit置为容量, 以备下次读入到字节缓冲中，从0开始存储
                 */
                bf.clear();
                byte[] bytes = bf.array();
                jsonText.append(new String(bytes, 0, length));
            }
            channel.close();
            return jsonText.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                assert fin != null;
                fin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
