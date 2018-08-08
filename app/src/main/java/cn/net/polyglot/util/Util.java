package cn.net.polyglot.util;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {

  public static String md5(String s){
    try {
      return md5(s, Charset.forName("UTF-8"));
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String md5(String s, Charset charset) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("MD5");
    digest.update(s.getBytes(charset));
    String hex = new BigInteger(1, digest.digest()).toString(16);
    // 补齐BigInteger省略的前置0
    return new String(new char[32 - hex.length()]).replace("\0", "0") + hex;
  }
}
