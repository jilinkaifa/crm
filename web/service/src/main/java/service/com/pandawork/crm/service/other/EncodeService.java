package com.pandawork.crm.service.other;

/**
 * EncodeService
 * 对输入的字符串进行过滤
 * @author: dujuan
 * @time: 2015/9/11 9:15
 */
public interface EncodeService {
    /**
     * 防止sql注入
     *
     * @param input
     * @return
     * @throws
     */
    public String encodeSql(String input);

    /**
     * 过滤html标签
     *
     * @param input
     * @return
     */
    public String encodeHtmlTag(String input);

    /**
     * 进行防sql注入和html标签过滤
     * @param input
     * @return
     * @throws
     */
    public String encodeAll(String input);
}
