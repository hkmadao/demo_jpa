package org.hkmadao.tcdt.conf.security;

import org.springframework.util.DigestUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyUtil {
    // 获取私匙md5
    public static String getPrivateKeyMd5FromPem(String privateKeyFileName) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(privateKeyFileName));
        String s = br.readLine();
        String str = "";
        s = br.readLine();
        while (s.charAt(0) != '-') {
            str += s;
            s = br.readLine();
        }
        byte[] b = Base64.getDecoder().decode(str.replace("/r/n", ""));

        String md5 = DigestUtils.md5DigestAsHex(b);
        return md5;
    }

    // 获取私匙
    public static PrivateKey getPrivateKeyFromPem(String privateKeyFileName) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(privateKeyFileName));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            //文件以--开头的行不读取
            if (line.startsWith("--")) {
                continue;
            }
            sb.append(line);
        }
        byte[] b = Base64.getDecoder().decode(sb.toString()
                .replaceAll("\r\n", "")
                .replaceAll("\n", "")
        );

        // 生成私匙
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(b);
        PrivateKey privateKey = kf.generatePrivate(keySpec);
        return privateKey;
    }

    // 获取公钥
    public static PublicKey getPublicKeyFromPem(String publicKeyFileName) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(publicKeyFileName));
        String s = br.readLine();
        String str = "";
        s = br.readLine();
        while (s.charAt(0) != '-') {
            str += s;
            s = br.readLine();
        }
        byte[] b = Base64.getDecoder().decode(str.replace("/r/n", ""));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(b);
        PublicKey pubKey = kf.generatePublic(keySpec);
        return pubKey;
    }

}
