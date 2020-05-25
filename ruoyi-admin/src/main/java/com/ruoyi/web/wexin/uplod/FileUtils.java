package com.ruoyi.web.wexin.uplod;



import java.io.*;

public class FileUtils {

    /**
     * @Author xiexin
     * 文件工具
     **/
    public static Boolean uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        FileOutputStream out = null;
        try {
            File targetFile = new File(filePath);
            //如果目录不存在，创建目录
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            out = new FileOutputStream(filePath+fileName);
            out.write(file);
            out.flush();
            //写入成功
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            //写入失败
            return false;
        } finally {
            out.close();
        }
    }

    public static Integer deleteFile(String path){
        int lastIndexOf = path.lastIndexOf("/");
        String img_path = path.substring(lastIndexOf + 1, path.length());
        //System.out.println(img_path);
        String os = System.getProperty("os.name");
        //文件保存路径
        String filePath="";
        if(os.toLowerCase().startsWith("win")){
            //windows下的路径
            filePath ="d:/pictureUpload/project/" + img_path;
        }else {
            //linux下的路径
            filePath="/root/pictureUpload/project/" + img_path;
        }
        //System.out.println(img_path);//输出测试一下文件路径是否正确
        File file = new File(filePath);
        if (file.exists()) {//文件是否存在
            if (file.delete()) {//存在就删了，返回1
                return 0;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

    /**
     * 获取图片在本地的地址
     * @param path
     * @return
     */
    public static String getImageLocalUrl(String path){
        int lastIndexOf = path.lastIndexOf("/");
        String img_path = path.substring(lastIndexOf + 1, path.length());
        //System.out.println(img_path);
        String os = System.getProperty("os.name");
        //文件保存路径
        String filePath="";
        if(os.toLowerCase().startsWith("win")){
            //windows下的路径
            filePath ="d:/pictureUpload/project/" + img_path;
        }else {
            //linux下的路径
            filePath="/root/pictureUpload/project/" + img_path;
        }
        return filePath;
    }

}