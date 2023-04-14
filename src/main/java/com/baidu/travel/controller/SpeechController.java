package com.baidu.travel.controller;



import com.baidu.travel.domain.ResultInfo;
import com.baidu.travel.service.SpeechService;
import com.baidu.travel.utils.Sample;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 语音搜索操作
 */
@RestController
@RequestMapping("/speech")
public class SpeechController {
    @Autowired
    private SpeechService speechService;
    @RequestMapping("/speechScanner")
    public ResultInfo speechScanner(HttpServletRequest request) {
        System.out.println("开始语音识别...");
        try {
            String filename;
            // 使用Apache文件上传组件处理文件上传步骤：
            // 1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 2、创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8");
            // 3、判断提交上来的数据是否是上传表单的数据
            if (!ServletFileUpload.isMultipartContent(request)) {
                // 按照传统方式获取数据
                System.out.println("yjf,太难顶了");
                return new ResultInfo(false,null,"太难了");
            }
            // 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = upload.parseRequest(request);


//            System.out.println(list.size());
            String[] value = new String[5];
            int i=0;
            for (FileItem item : list) {

// 如果fileitem中封装的是普通输入项的数据
                if (item.isFormField()) {
// System.out.println("歌曲名"+item.getString("musicName")+"类别"+item.getString("musicType"));
                    String name = item.getFieldName();
// 解决普通输入项的数据的中文乱码问题
                    value[i++] = item.getString("UTF-8");
// value = new String(value.getBytes("iso8859-1"),"UTF-8");
//System.out.println(name + "=" + value);
                } else {// 如果fileitem中封装的是上传文件
// 得到上传的文件名称，
                    filename = "test.wav";
                    item.getName();

                    if (filename == null || filename.trim().equals("")) {
                        continue;
                    }

                    filename = filename.substring(filename.lastIndexOf("\\") + 1);

                    InputStream in = item.getInputStream();

                    byte buffer[] = new byte[1024000];
                    in.read(buffer);
                    String r = Sample.getR(buffer);
                    System.out.println(r);
                    JSONObject jsonObject=new JSONObject(r);
                    String result = (String) jsonObject.getJSONArray("result").get(0);
                    //查找城市
                    String city = speechService.find(result,request);
                    in.close();
                    ResultInfo resultInfo=new ResultInfo();

                    if (city!=null){

                        resultInfo.setErrorMsg(city);
                        return resultInfo;
                    }else {
                        resultInfo.setErrorMsg("没有找到您想要的路线");
                        return resultInfo;
                    }
// 判断输入流中的数据是否已经读完的标识
                    //  int len = 0;
// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
              /*      while ((len = in.read(buffer)) > 0) {
// 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\"
// + filename)当中
                        out.write(buffer, 0, len);
                    }
// 关闭输入流
                    in.close();
// 关闭输出流
                    out.close();
// 删除处理文件上传时生成的临时文件
                    item.delete();

                    message = "文件上传成功！";*/
                }
            }
            System.out.println("完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultInfo(false,null,"");
    }
}
