//package com.ruoyi.web.wexin.uplod;
//
//import com.ruoyi.common.config.Global;
//import com.ruoyi.common.utils.StringUtils;
//import com.ruoyi.common.utils.file.FileUtils;
//import com.ruoyi.web.JsonResult;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Controller
//public class ImageDownController {
//
//    @ResponseBody
//    @PostMapping("/downImage")
//    public JsonResult reImage(HttpServletResponse response){
//
//        try
//        {
//            if (!com.ruoyi.common.utils.file.FileUtils.isValidFilename(fileName))
//            {
//                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
//            }
//            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
//            String filePath = Global.getDownloadPath() + fileName;
//
//            response.setCharacterEncoding("utf-8");
//            response.setContentType("multipart/form-data");
//            response.setHeader("Content-Disposition",
//                    "attachment;fileName=" + com.ruoyi.common.utils.file.FileUtils.setFileDownloadHeader(request, realFileName));
//            com.ruoyi.common.utils.file.FileUtils.writeBytes(filePath, response.getOutputStream());
//
//        }
//        catch (Exception e)
//        {
//
//        }
//        return  null;
//    }
//}
