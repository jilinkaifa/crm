package com.pandawork.crm.common.utils;

/**
 * PrintUtils
 * 打印机工具类
 *
 * @author: yangch
 * @time: 2015/11/24 8:46
 */
public class PrintUtils {
    /**
     * 初始化打印机参数，防止上次打印的参数影响本次打印
     * @return
     */
    public static byte[] initPrinter() {
        byte[] bytes = new byte[2];
        bytes[0] = 0x1B;
        bytes[1] = 0x40;

        return bytes;
    }

    /**
     * 设置显示位置: 0-左对齐;1-居中;2-右对齐
     * @param parameter
     * @return
     */
    public static byte[] setLocation(int parameter) {
        byte[] bytes = new byte[3];
        bytes[0] = 0x1B;
        bytes[1] = 0x61;
        bytes[2] = (byte)parameter;

        return bytes;
    }

    /**
     * 设置显示大小，0为正常字体，最大值为7
     * @param weight
     * @param height
     * @return
     */
    public static byte[] setSize(int weight, int height) {
        byte[] bytes = new byte[3];
        bytes[0] = 0x1D;
        bytes[1] = 0x21;
        bytes[2] = 0x00;
        bytes[2] += height;
        bytes[2] += weight * 16;

        return bytes;
    }

    /**
     * 打印文本
     * @param text
     * @return
     */
    public static byte[] printText(String text) {
        try {
            return text.getBytes("GBK");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 移动到横向第n个位置
     * @param n
     * @return
     */
    public static byte[] move(int n) {
        byte[] bytes = new byte[4];
        bytes[0] = 0x1B;
        bytes[1] = 0x24;
        bytes[2] = (byte)(770 + n * 10);//770页面最左侧，以后每一个位置加10。不要问为什么，这TM是试出来的...
        bytes[3] = 0;

        return bytes;
    }

    /**
     * 走纸
     * @param n
     * @return
     */
    public static byte[] println(int n) {
        byte[] bytes = new byte[3];
        bytes[0] = 0x1B;
        bytes[1] = 0x64;
        bytes[2] = (byte)n;

        return bytes;
    }

    /**
     * 切纸
     * @return
     */
    public static byte[] cutPaper() {
        byte[] bytes = new byte[3];
        bytes[0] = 0x1D;
        bytes[1] = 0x56;
        bytes[2] = (byte)0; //0-全切;1-半切

        return bytes;
    }

    /**
     * 设置条码高度，若不设置，默认为162
     * @param height
     * @return
     */
    public static byte[] setBarCodeHeight(int height) {
        byte[] bytes = new byte[3];
        bytes[0] = 0x1D;
        bytes[1] = 0x68;
        bytes[2] = (byte)height;

        return bytes;
    }

    /**
     * 设置条码高度，若不设置，默认为3
     * @param weight
     * @return
     */
    public static byte[] setBarCodeWeight(int weight) {
        byte[] bytes = new byte[3];
        bytes[0] = 0x1D;
        bytes[1] = 0x77;
        bytes[2] = (byte)weight;

        return bytes;
    }

    /**
     * 打印条形码
     * @param barCode
     * @return
     */
    public static byte[] printBarCode(String barCode) {
        try {
            byte[] bytes = new byte[barCode.length() + 4];
            bytes[0] = 0x1D;
            bytes[1] = 0x6B;
            bytes[2] = (byte) 4; //设置条形码类型，此处使用CODE39，可打印1-255个字符
            int num = 1; //对barCode计数
            for (byte b : barCode.getBytes("GBK")){
                bytes[2 + num] = b;
                num++;
            }
            bytes[barCode.length() + 3] = 0x00;

            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 旧版餐饮项目中的打印条形码
     * 用上面的打印条码的话扫码枪读不出来
     * @param str
     * @return
     */

    public static byte[] newPrintBarCode(String str) {
        // 不足4位的补零
        // 使用code39时，必须大于4位，扫码枪才能读出来
        int len = str.length() >= 4 ? str.length() : 4;
        StringBuffer sb = new StringBuffer();
        for (int i = 0;i < 4 - str.length(); ++i) {
            sb.append('0');
        }
        sb.append(str);
        str = sb.toString();

        byte[] bytes = new byte[len + 4];
        // 采用code 39
        bytes[0] = 0x1D;  bytes[1] = 0x6B;
        bytes[2] = (byte)69;
        bytes[3] = (byte) (len);
        byte[] strBytes = str.getBytes();
        int i = 0;
        int j = 4;
        for (i = 0;i < strBytes.length; ++i) {
            bytes[j ++] = strBytes[i];
        }
        return bytes;
    }
}
