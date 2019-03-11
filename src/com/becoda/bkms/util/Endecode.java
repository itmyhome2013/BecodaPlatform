package com.becoda.bkms.util;

/**
 * Created by IntelliJ IDEA.
 * User: HRSoft
 * Date: 2015-7-15
 * Time: 17:52:38
 * To change this template use File | Settings | File Templates.
 */
public class Endecode {

//    public static String encode(String code) {
//        String base = "";
//        String position;
//        Random r = new Random();
//        for (int i = 0; i < 125; i++) {
//            base += String.valueOf(Math.abs(r.nextInt()) % 10);
//        }
//        position = "1,4,7,9,12,25,46,33,68,54";
//
//        if (code == null || "".equals(code)) {
//            return "";
//        } else {
//            try {
//                String tmp = base;
//                int len = code.length();
//                String[] parray = position.split(",");
//                if (parray.length >= len) {
//                    for (int i = 0; i < len; i++) {
//                        String base1 = tmp.substring(0, Integer.parseInt(parray[i]) - 1);
//                        String base2 = tmp.substring(Integer.parseInt(parray[i]), tmp.length());
//                        String tmp1 = code.substring(i, i + 1);
//                        tmp = base1 + tmp1 + base2;
//                    }
//                    if (len > 9) {
//                        tmp = String.valueOf(len) + tmp;
//                    } else {
//                        tmp = "0" + String.valueOf(len) + tmp;
//                    }
//                    return tmp;
//
//                } else {
//                    return "";
//                }
//            } catch (Exception e) {
//                return "";
//            }
//        }
//    }
//
//    public static String decode(String code) {
//        String position = "1,4,7,9,12,25,46,33,68,54";
//
//        if (code == null && "".equals(code)) {
//            return "";
//        } else {
//            try {
//                String length = code.substring(0, 2);
//                int len = Integer.parseInt(length);
//                code = code.substring(2, code.length());
//                String[] parray = position.split(",");
//                String tmp = "";
//                if (parray.length >= len) {
//                    for (int i = 0; i < len; i++) {
//                        tmp += code.substring(Integer.parseInt(parray[i]) - 1, Integer.parseInt(parray[i]));
//
//                    }
//                    return tmp;
//
//                } else {
//                    return "";
//                }
//            } catch (Exception e) {
//                return "";
//            }
//        }
//    }
//
//    public static String encodeByKey(String txt) {
//        String password = Constants.ENCODE_KEY;
//        char[]   pwd = password.toCharArray();
//        StringBuffer buf = new StringBuffer();
//        for (int i = 0; i < txt.length(); i++) {
//            char ch = (char) (pwd[i % (pwd.length)] ^ txt.charAt(i));
//            buf.append(ch);
//        }
//        return buf.toString();
//    }
//
//    public static String decodeByKey(String txt) {
//        String password = Constants.ENCODE_KEY;
//        char[]   pwd = password.toCharArray();
//        StringBuffer buf = new StringBuffer();
//        for (int i = 0; i < txt.length(); i++) {
//            buf.append((char) (txt.charAt(i) ^ pwd[i % (pwd.length)]));
//        }
//        return buf.toString();
//    }

    public static final String Base64Chars =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@*-";


    /**
     * Encoding a string to a string follow the Base64 regular
     *
     * @param s
     */
    public static String base64Encode(final String s) {
        if (s == null || s.length() == 0) return s;
        byte[] b = null;
        try {
            b = s.getBytes("UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
            return s;
        }
        return base64EncodeFoArray(b);
    }

    /**
     * Encoding a byte array to a string follow the Base64 regular.
     *
     * @param s byte array
     */
    public static String base64EncodeFoArray(final byte[] s) {
        if (s == null) return null;
        if (s.length == 0) return "";
        StringBuffer buf = new StringBuffer();
        int b0, b1, b2, b3;
        int len = s.length;
        int i = 0;
        while (i < len) {
            byte tmp = s[i++];
            b0 = (tmp & 0xfc) >> 2;
            b1 = (tmp & 0x03) << 4;
            if (i < len) {
                tmp = s[i++];
                b1 |= (tmp & 0xf0) >> 4;
                b2 = (tmp & 0x0f) << 2;
                if (i < len) {
                    tmp = s[i++];
                    b2 |= (tmp & 0xc0) >> 6;
                    b3 = tmp & 0x3f;
                } else {
                    b3 = 64; // 1 byte "-" is supplement
                }
            } else {
                b2 = b3 = 64;// 2 bytes "-" are supplement
            }
            buf.append(Base64Chars.charAt(b0));
            buf.append(Base64Chars.charAt(b1));
            buf.append(Base64Chars.charAt(b2));
            buf.append(Base64Chars.charAt(b3));
        }
        return buf.toString();
    }

    /**
     * Decoding a string to a string follow the Base64 regular.
     *
     * @param s
     */
    public static String base64Decode(final String s) {
        byte[] b = base64DecodeToArray(s);
        if (b == null) return null;
        if (b.length == 0) return "";
        try {
            return new String(b, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Decoding a string to a byte array follow the Base64 regular
     *
     * @param s
     */
    public static byte[] base64DecodeToArray(final String s) {
        if (s == null) return null;

        int len = s.length();
        if (len == 0) return new byte[0];
        if (len % 4 != 0) {
            throw new java.lang.IllegalArgumentException(s);
        }

        byte[] b = new byte[(len / 4) * 3];
        int i = 0, j = 0, e = 0, c, tmp;
        while (i < len) {
            c = Base64Chars.indexOf((int) s.charAt(i++));
            tmp = c << 18;
            c = Base64Chars.indexOf((int) s.charAt(i++));
            tmp |= c << 12;
            c = Base64Chars.indexOf((int) s.charAt(i++));
            if (c < 64) {
                tmp |= c << 6;
                c = Base64Chars.indexOf((int) s.charAt(i++));
                if (c < 64) {
                    tmp |= c;
                } else {
                    e = 1;
                }
            } else {
                e = 2;
                i++;
            }

            b[j + 2] = (byte) (tmp & 0xff);
            tmp >>= 8;
            b[j + 1] = (byte) (tmp & 0xff);
            tmp >>= 8;
            b[j + 0] = (byte) (tmp & 0xff);
            j += 3;
        }

        if (e != 0) {
            len = b.length - e;
            byte[] copy = new byte[len];
            System.arraycopy(b, 0, copy, 0, len);
            return copy;
        }

        return b;
    }

    /**
     * @param args
     */
//    public static void main(String[] args) {
//        String s0 = "AB枫知`~!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?";
//        String s1 = base64Encode(s0);
//        System.err.println(s0 + " --> " + s1);
//        String s2 = base64Decode(s1);
//        System.err.println(s1 + " --> " + s2);
//
//    }

}
