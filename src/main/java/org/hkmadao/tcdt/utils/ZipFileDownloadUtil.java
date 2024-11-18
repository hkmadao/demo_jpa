package org.hkmadao.tcdt.utils;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 压缩文件下载工具类。用于将多文件文件压缩后，提供给 ServletOutputStream 输出流共用户网页下载
 */
public class ZipFileDownloadUtil {

    /**
     * 压缩本地多个文件并提供输出流下载
     *
     * @param dir         待压缩文件目录前缀，压缩后去掉前缀做成相对目录
     * @param filePaths   ：本地文件路径，如 ["D:\\temp\\data1.json","D:\\temp\\data2.json"]。只支持文件，不能有目录,否则抛异常。
     * @param zipFileName ：压缩文件输出的名称，如 "filename" 不含扩展名。默认文件当前时间。如 20220308111150.zip
     * @param response    ：提供输出流
     */
    public static void zipFileDownloadByFile(String dir, Set<String> filePaths, String zipFileName,
                                             HttpServletResponse response) {
        ZipArchiveOutputStream zipArchiveOutputStream = null;
        try {
            //1）参数校验
            if (filePaths == null || filePaths.size() <= 0) {
                throw new RuntimeException("待压缩导出文件为空.");
            }
            if (zipFileName == null || "".equals(zipFileName)) {
                zipFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".zip";
            } else {
                zipFileName = zipFileName + ".zip";
            }
            //2）设置 response 参数。这里文件名如果是中文，则导出乱码，可以
            response.reset();
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("content-type:octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(zipFileName, "utf-8"));

            //3）通过 OutputStream 创建 zip 压缩流。如果是压缩到本地，也可以直接使用 ZipArchiveOutputStream(final File file)
            ServletOutputStream servletOutputStream = response.getOutputStream();
            zipArchiveOutputStream = new ZipArchiveOutputStream(servletOutputStream);
            //4）setUseZip64(final Zip64Mode mode)：是否使用 Zip64 扩展。
            // Zip64Mode 枚举有 3 个值：Always：对所有条目使用 Zip64 扩展、Never：不对任何条目使用Zip64扩展、AsNeeded：对需要的所有条目使用Zip64扩展
            zipArchiveOutputStream.setUseZip64(Zip64Mode.AsNeeded);

            for (String filePath : filePaths) {
                zipArchiveOutput(dir, zipArchiveOutputStream, filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //7）最后关闭 zip 压缩输出流.
            if (zipArchiveOutputStream != null) {
                try {
                    zipArchiveOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void zipArchiveOutput(String dir, ZipArchiveOutputStream zipArchiveOutputStream, String filePath) {
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            File file = new File(filePath);
            inputStream = new FileInputStream(file);
            //5）使用 ByteArrayOutputStream 读取文件字节
            byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int readLength = -1;
            while ((readLength = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, readLength);
            }
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.flush();
            }
            byte[] fileBytes = byteArrayOutputStream.toByteArray();//整个文件字节数据

            //6）用指定的名称创建一个新的 zip 条目(zip压缩实体)，然后设置到 zip 压缩输出流中进行写入.
            ArchiveEntry entry =
                    new ZipArchiveEntry(file.getAbsolutePath().substring((dir + File.separator).length()));
            zipArchiveOutputStream.putArchiveEntry(entry);
            //6.1、write(byte b[])：从指定的字节数组写入 b.length 个字节到此输出流
            zipArchiveOutputStream.write(fileBytes);
            //6.2、写入此条目的所有必要数据。如果条目未压缩或压缩后的大小超过4 GB 则抛出异常
            zipArchiveOutputStream.closeArchiveEntry();
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != byteArrayOutputStream) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 压缩本地多个文件并提供输出流下载。只支持文件，不能有目录,否则抛异常。
     *
     * @param dir         待压缩文件目录前缀，压缩后去掉前缀做成相对目录
     * @param fileLists   ：本地文件路径，如 ["D:\\temp\\data1.json","D:\\temp\\data2.json"]。只支持文件，不能有目录,否则抛异常。
     * @param zipFileName ：压缩文件输出的名称，如 "filename" 不含扩展名。默认文件当前时间。如 20220308111150.zip
     * @param response    ：提供输出流
     */
    public static void zipFileDownloadByFile(String dir, List<File> fileLists, String zipFileName,
                                             HttpServletResponse response) {
        if (fileLists == null || fileLists.size() <= 0) {
            throw new RuntimeException("待压缩导出文件为空.");
        }
        Set<String> filePaths = new HashSet<>();
        for (File file : fileLists) {
            filePaths.add(file.getAbsolutePath());
        }
        zipFileDownloadByFile(dir, filePaths, zipFileName, response);
    }

    /**
     * 压缩网络文件。
     *
     * @param urlLists，待压缩的网络文件地址，如 ["http://www.baidu.com/img/bd_logo1.png"]
     * @param zipFileName
     * @param response
     */
    public static void zipFileDownloadByUrl(List<URL> urlLists, String zipFileName, HttpServletResponse response) {
        try {
            //1）参数校验
            if (urlLists == null || urlLists.size() <= 0) {
                throw new RuntimeException("待压缩导出文件为空.");
            }
            if (zipFileName == null || "".equals(zipFileName)) {
                zipFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".zip";
            } else {
                zipFileName = zipFileName + ".zip";
            }
            //2）设置 response 参数
            response.reset();
            response.setContentType("content-type:octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(zipFileName, "utf-8"));

            //3）通过 OutputStream 创建 zip 压缩流。如果是压缩到本地，也可以直接使用 ZipArchiveOutputStream(final File file)
            ServletOutputStream servletOutputStream = response.getOutputStream();
            ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(servletOutputStream);
            //4）setUseZip64(final Zip64Mode mode)：是否使用 Zip64 扩展。
            // Zip64Mode 枚举有 3 个值：Always：对所有条目使用 Zip64 扩展、Never：不对任何条目使用Zip64扩展、AsNeeded：对需要的所有条目使用Zip64扩展
            zipArchiveOutputStream.setUseZip64(Zip64Mode.AsNeeded);

            for (URL url : urlLists) {
                String fileName = getNameByUrl(url);
                InputStream inputStream = url.openStream();
                //5）使用 ByteArrayOutputStream 读取文件字节
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int readLength = -1;
                while ((readLength = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, readLength);
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.flush();
                }
                byte[] fileBytes = byteArrayOutputStream.toByteArray();//整个文件字节数据

                //6）用指定的名称创建一个新的 zip 条目(zip压缩实体)，然后设置到 zip 压缩输出流中进行写入.
                ArchiveEntry entry = new ZipArchiveEntry(fileName);
                zipArchiveOutputStream.putArchiveEntry(entry);
                //6.1、write(byte b[])：从指定的字节数组写入 b.length 个字节到此输出流
                zipArchiveOutputStream.write(fileBytes);
                //6.2、写入此条目的所有必要数据。如果条目未压缩或压缩后的大小超过4 GB 则抛出异常
                zipArchiveOutputStream.closeArchiveEntry();
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            }
            //7）最后关闭 zip 压缩输出流.
            if (zipArchiveOutputStream != null) {
                zipArchiveOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩网络文件
     *
     * @param urlPaths    待压缩的网络文件地址，如 ["http://www.baidu.com/img/bd_logo1.png"]
     * @param zipFileName
     * @param response
     */
    public static void zipFileDownloadByUrl(Set<String> urlPaths, String zipFileName, HttpServletResponse response) {
        if (urlPaths == null || urlPaths.size() <= 0) {
            throw new RuntimeException("待压缩导出文件为空.");
        }
        List<URL> urlList = new ArrayList<>();
        for (String urlPath : urlPaths) {
            try {
                urlList.add(new URL(urlPath));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            zipFileDownloadByUrl(urlList, zipFileName, response);
        }
    }

    /**
     * 通过 url 获取文件的名称
     *
     * @param url，如 http://www.baidu.com/img/bd_logo1.png
     * @return
     */
    private static String getNameByUrl(URL url) {
        String name = url.toString();
        int lastIndexOf1 = name.lastIndexOf("/");
        int lastIndexOf2 = name.lastIndexOf("\\");
        if (lastIndexOf1 > 0) {
            name = name.substring(lastIndexOf1 + 1, name.length());
        } else if (lastIndexOf2 > 0) {
            name = name.substring(lastIndexOf1 + 2, name.length());
        }
        return name;
    }
}
