package common.tools.conversion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lindec on 2016/3/7.
 */
public class ConversionTools {
    private static final Logger logger = LoggerFactory.getLogger(ConversionTools.class);

    public static ConversionTools Tool() {
        return new ConversionTools();
    }


    /**
     * Btye 转换成 Str
     *
     * @param b    byte
     * @param flag true ：表示输出如0x03->03 ;false:表示输出0x03->3
     * @return Str
     */
    public String byte2Str(byte b, boolean flag) {
        String stmp = Integer.toHexString(b & 0xFF);
        if (!flag) {
            return stmp;
        } else {
            StringBuilder sb = new StringBuilder("");
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
            stmp = sb.toString();
            return stmp.toUpperCase().trim();
        }


    }

    /**
     * bytes转换成十六进制字符串
     *
     * @param b byte数组
     * @return String 每个Byte值之间空格分隔  {0x01,0x02} -> "01 02"
     */
    public String byte2HexStr(byte[] b) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
            sb.append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }


    /**
     * bytes字符串转换为Byte值
     *
     * @param src  Byte字符串，每个Byte之间没有分隔符发
     * @param flag true：允许超过7f（127） false：不允许允许超过7f（127），如：80就会有错误
     * @return byte[]    "020a7f" -> {0x02,0x0a,0x7f}
     */
    public byte[] hexStr2Bytes(String src, boolean flag) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        if (flag) {
            for (int i = 0; i < l; i++) {
                int temp = Integer.decode("0x" + src.substring(i * 2, i * 2 + 1) + src.substring(i * 2 + 1, i * 2 + 2));
                ret[i] = (byte) temp;
            }
        } else {
            for (int i = 0; i < l; i++) {
                ret[i] = Byte.decode("0x" + src.substring(i * 2, i * 2 + 1) + src.substring(i * 2 + 1, i * 2 + 2));
            }
        }

        return ret;
    }

    /**
     * String的字符串转换成unicode的String
     *
     * @param strText 全角字符串
     * @return String 每个unicode之间无分隔符 如："我是天才" -> "\u6211\u662f\u5929\u624d"
     * @throws Exception
     */
    public String strToUnicode(String strText)
            throws Exception {
        char c;
        StringBuilder str = new StringBuilder();
        int intAsc;
        String strHex;
        for (int i = 0; i < strText.length(); i++) {
            c = strText.charAt(i);
            intAsc = (int) c;
            strHex = Integer.toHexString(intAsc);
            if (intAsc > 128)
                str.append("\\u" + strHex);
            else // 低位在前面补00
                str.append("\\u00" + strHex);
        }
        return str.toString();
    }

    /**
     * unicode的String转换成String的字符串
     *
     * @param hex 16进制值字符串 （一个unicode为2byte）
     * @return String 全角字符串 如："\\u6211\\u662f\\u5929\\u624d" -> "我是天才"
     */
    public String unicodeToString(String hex) {
        int t = hex.length() / 6;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < t; i++) {
            String s = hex.substring(i * 6, (i + 1) * 6);
            // 高位需要补上00再转
            String s1 = s.substring(2, 4) + "00";
            // 低位直接转
            String s2 = s.substring(4);
            // 将16进制的string转为int
            int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
            // 将int转换为字符
            char[] chars = Character.toChars(n);
            str.append(new String(chars));
        }
        return str.toString();
    }
}