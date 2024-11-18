package org.hkmadao.tcdt.utils;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.Set;

/**
 * 压缩工具类
 */
public class ZipUtil {

    /**
     * 将文件打包成 zip 压缩包文件
     *
     * @param dir             待压缩文件目录前缀，压缩后去掉前缀做成相对目录
     * @param sourceFileNames 待压缩的多个文件列表。只支持文件，不能有目录,否则抛异常。
     * @param zipFileFullName 压缩文件。文件可以不存在，但是目录必须存在，否则抛异常。如 D:\data\aa.zip
     * @return 是否压缩成功
     */
    public static boolean archiveFiles2Zip(String dir, Set<String> sourceFileNames, String zipFileFullName) {
        InputStream inputStream = null;//源文件输入流
        ZipArchiveOutputStream zipArchiveOutputStream = null;//压缩文件输出流
        if (CollectionUtils.isEmpty(sourceFileNames)) {
            return false;
        }
        try {
            File zipFile = new File(zipFileFullName);
            zipArchiveOutputStream = new ZipArchiveOutputStream(zipFile);//ZipArchiveOutputStream(File file)
            // ：根据文件构建压缩输出流，将源文件压缩到此文件.
            //setUseZip64(final Zip64Mode mode)：是否使用 Zip64 扩展。
            // Zip64Mode 枚举有 3 个值：Always：对所有条目使用 Zip64 扩展、Never：不对任何条目使用Zip64扩展、AsNeeded：对需要的所有条目使用Zip64扩展
            zipArchiveOutputStream.setUseZip64(Zip64Mode.AsNeeded);
            for (String fileNames : sourceFileNames) {
                File file = new File(fileNames);
                //将每个源文件用 ZipArchiveEntry 实体封装，然后添加到压缩文件中. 这样将来解压后里面的文件名称还是保持一致.
                ZipArchiveEntry zipArchiveEntry =
                        new ZipArchiveEntry(file.getAbsolutePath().substring((dir + File.separator).length()));
                zipArchiveOutputStream.putArchiveEntry(zipArchiveEntry);
                inputStream = new FileInputStream(file);//获取源文件输入流
                byte[] buffer = new byte[1024 * 5];
                int length = -1;//每次读取的字节大小。
                while ((length = inputStream.read(buffer)) != -1) {
                    //把缓冲区的字节写入到 ZipArchiveEntry
                    zipArchiveOutputStream.write(buffer, 0, length);
                }
            }
            zipArchiveOutputStream.closeArchiveEntry();//写入此条目的所有必要数据。如果条目未压缩或压缩后的大小超过4 GB 则抛出异常
            zipArchiveOutputStream.finish();//压缩结束.
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            //关闭输入、输出流，释放资源.
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
                if (null != zipArchiveOutputStream) {
                    zipArchiveOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 将 zip 压缩包解压成文件到指定文件夹下
     *
     * @param zipFile   待解压的压缩文件。亲测  .zip 文件有效；.7z 压缩解压时抛异常。
     * @param targetDir 解压后文件存放的目的地. 此目录必须存在，否则异常。
     * @return 是否成功
     */
    public static boolean decompressZip2Files(File zipFile, File targetDir) {
        InputStream inputStream = null;//源文件输入流，用于构建 ZipArchiveInputStream
        OutputStream outputStream = null;//解压缩的文件输出流
        ZipArchiveInputStream zipArchiveInputStream = null;//zip 文件输入流
        ArchiveEntry archiveEntry = null;//压缩文件实体.
        try {
            inputStream = new FileInputStream(zipFile);//创建输入流，然后转压缩文件输入流
            zipArchiveInputStream = new ZipArchiveInputStream(inputStream, "UTF-8");
            //遍历解压每一个文件.
            while (null != (archiveEntry = zipArchiveInputStream.getNextEntry())) {
                byte[] buffer = new byte[1024 * 5];
                outputStream = new FileOutputStream(targetDir);
                int length = -1;
                while ((length = zipArchiveInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (null != outputStream) {
                    outputStream.close();
                }
                if (null != zipArchiveInputStream) {
                    zipArchiveInputStream.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 将 zip 压缩包解压成文件到指定文件夹下
     *
     * @param bytes     待解压的压缩文件byte数据。亲测  .zip 文件有效；.7z 压缩解压时抛异常。
     * @param targetDir 解压后文件存放的目的地. 此目录必须存在，否则异常。
     * @return 是否成功
     */
    public static boolean decompressZipByte2Files(byte[] bytes, File targetDir) {
        InputStream inputStream = null;//源文件输入流，用于构建 ZipArchiveInputStream
        OutputStream outputStream = null;//解压缩的文件输出流
        ZipArchiveInputStream zipArchiveInputStream = null;//zip 文件输入流
        ArchiveEntry archiveEntry = null;//压缩文件实体.
        try {
            inputStream = new ByteArrayInputStream(bytes);
            zipArchiveInputStream = new ZipArchiveInputStream(inputStream, "UTF-8");
            //遍历解压每一个文件.
            while (null != (archiveEntry = zipArchiveInputStream.getNextEntry())) {
                String fileName = archiveEntry.getName();
                if (fileName.contains("/")) {
                    File dir = new File(targetDir + File.separator + fileName.substring(0, fileName.lastIndexOf("/")));
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                }
                if (archiveEntry.isDirectory()) {
                    continue;
                }
                byte[] buffer = new byte[1024 * 5];
                outputStream = new FileOutputStream(targetDir + File.separator + fileName);
                int length = -1;
                while ((length = zipArchiveInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (null != outputStream) {
                    outputStream.close();
                }
                if (null != zipArchiveInputStream) {
                    zipArchiveInputStream.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}