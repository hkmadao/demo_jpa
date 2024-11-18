package org.hkmadao.tcdt.utils;

import java.io.File;

public class DeletePackage {

    public static void main(String[] args) {
        String[] dirs = {"20221109105404", "20221109105523", "20221109105622", "20221109105650", "20221109110101"};
        for (String dir : dirs) {
        }
        deletePath("D:\\temp\\temp\\20221115154458", "download\\com\\mrlh\\map\\pi");
    }

    /**
     * 删除循环嵌套的多层文件夹
     *
     * @param prePath 路径前缀部分
     * @param filePathPart 路径循环部分
     * @return
     */
    private static String deletePath(String prePath, String filePathPart) {
        String filePath = prePath;
        for (int i = 0; i < 1000; i++) {
            filePath += filePathPart;
        }
        int i = 0;
        while (filePath.contains(filePathPart)) {
            filePath = filePath.substring(0, filePath.length() - filePathPart.length());
            System.out.println(i++);
            System.out.println(deleteAll(filePath));
        }
        return filePath;
    }

    private static boolean deleteAll(String filePath) {
        File file = new File(filePath);
        if (file.isFile()) {//如果是文件，直接删除
            System.out.println(file + "文件" + (file.delete() ? "已删除" : "未删除"));
        }
        if (file.isDirectory()) {
            File[] fileAll = file.listFiles();//获取文件夹下的子目录
            if (fileAll == null) {//如果文件夹为空，直接return，预防NullPointException
                return true;
            }
            if (fileAll.length != 0) {//如果文件夹内子文件夹的长度不为0则说明该文件夹下还有子目录
                for (File part : fileAll) {//继续遍历子目录列表，向下递归
                    deleteAll(part.getAbsolutePath());
                }
            } else {//反之，长度为0说明没有该文件夹下没有子目录，直接删除即可
                System.out.println(file + "文件夹" + (file.delete() ? "已删除" : "未删除"));
            }
            System.out.println(file + "文件夹" + (file.delete() ? "已删除" : "未删除"));
        }
        return true;
    }
}
