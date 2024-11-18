package org.hkmadao.tcdt.utils;

import org.hkmadao.core.base.BusinessException;

import java.io.*;
import java.util.Set;

public class FileUtil {

    /**
     * 创建相同目录结构
     *
     * @param srcFile  源文件夹
     * @param destFile 目标文件夹
     * @throws IOException
     */
    public static void copyFolderStruct(File srcFile, File destFile) throws IOException {
        //判断数据源File是否是目录
        if (srcFile.isDirectory()) {
            //获取数据源File下所有文件或目录的File数组
            File[] fileArray = srcFile.listFiles();
            //遍历该File数组，得到每一个File对象
            for (File file : fileArray) {
                if (file.isDirectory()) {
                    File newFolder = new File(destFile, file.getName());
                    if (!newFolder.exists()) {
                        newFolder.mkdirs();
                    }
                    //把该File作为数据源File对象，递归调用赋值文件夹的方法
                    copyFolderStruct(file, newFolder);
                }
            }
        }
    }

    /**
     * 复制文件或文件夹
     *
     * @param srcFile  源文件或文件夹
     * @param destFile 目标文件或文件夹
     * @throws IOException
     */
    public static void copyFolder(File srcFile, File destFile) throws IOException {
        //判断数据源File是否是目录
        if (srcFile.isDirectory()) {
            if(destFile.getAbsolutePath().equalsIgnoreCase(srcFile.getAbsolutePath())){
                throw new IOException("目标文件夹包含源文件夹，将造成循环拷贝问题，不能进行拷贝！");
            }
            //在目的地下创建和数据源File一样的目录
            String srcFileName = srcFile.getName();
            File newFolder = new File(destFile, srcFileName);
            if (!newFolder.exists()) {
                newFolder.mkdirs();
            }
            //获取数据源File下所有文件或目录的File数组
            File[] fileArray = srcFile.listFiles();
            //遍历该File数组，得到每一个File对象
            for (File file : fileArray) {
                //把该File作为数据源File对象，递归调用复制文件夹的方法
                copyFolder(file, newFolder);
            }
        } else {
            //说明是文件，直接复制
            File newFile = new File(destFile, srcFile.getName());
            copyFile(srcFile, newFile);
        }
    }

    /**
     * 复制目录下的所有文件到目标目录下
     *
     * @param srcDir  源文件夹
     * @param destDir 目标文件夹
     * @throws IOException
     */
    public static void copyFilesByFolder(File srcDir, File destDir) throws IOException {
        if(destDir.getAbsolutePath().startsWith(srcDir.getAbsolutePath())){
            throw new IOException("目标文件夹包含源文件夹，将造成循环拷贝问题，不能进行拷贝！");
        }
        //判断数据源File是否是目录
        if (srcDir.isDirectory()) {
            //获取数据源File下所有文件或目录的File数组
            File[] fileArray = srcDir.listFiles();
            //遍历该File数组，得到每一个File对象
            for (File file : fileArray) {
                //把该File作为数据源File对象，调用复制文件夹的方法
                copyFolder(file, destDir);
            }
        }
    }

    /**
     * 字节缓冲流复制文件
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     * @throws IOException
     */
    public static void copyFile(File srcFile, File destFile) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));
        byte[] bys = new byte[1024];
        int len;
        while ((len = bis.read(bys)) != -1) {
            bos.write(bys, 0, len);
        }
        bos.close();
        bis.close();
    }

    /**
     * 删除文件或文件夹
     *
     * @param path 文件或文件夹路径
     */
    public static void deleteFileOrDirectory(String path) {
        File fileOrDirectory = new File(path);
        if (fileOrDirectory.isDirectory()) {
            File[] listFiles = fileOrDirectory.listFiles();
            for (File file : listFiles) {
                deleteFileOrDirectory(file.getAbsolutePath());
            }
        }
        fileOrDirectory.delete();
    }

    /**
     * 获取目录下所有文件名称列表
     *
     * @param fileNames
     * @param path
     */
    public static void getFileNames(Set<String> fileNames, String path) {
        if (null == fileNames) {
            return;
        }
        File fileOrDirectory = new File(path);
        if (fileOrDirectory.isDirectory()) {
            File[] listFiles = fileOrDirectory.listFiles();
            for (File file : listFiles) {
                getFileNames(fileNames, file.getAbsolutePath());
            }
            return;
        }
        fileNames.add(fileOrDirectory.getAbsolutePath());
    }

    /**
     * 将带点号的包路径转为文件目录形式
     *
     * @param packagePath 包基础路径，用点（.）号隔开
     * @throws BusinessException
     */
    public static String packagePath2FilePath(String packagePath) throws BusinessException {
        String s = packagePath.replaceAll("\\.", "\\" + File.separator);
        return s;
    }
}
