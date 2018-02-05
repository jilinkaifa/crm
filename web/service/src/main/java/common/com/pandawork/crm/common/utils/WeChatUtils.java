package com.pandawork.crm.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.pandawork.wechat.config.WeChatConfig;
import com.pandawork.wechat.utils.HttpsUtil;

/**
 * WechatUtils
 * 一些微信公众号的方法
 *
 * 今后可以把这些功能加入Core包
 *
 * @author: yangch
 * @time: 2016/6/8 15:37
 */
public class WeChatUtils {
    /**
     * 通过Code获取网页授权AccessToken
     * @param code
     * @return
     */
    public static JSONObject getAccessTokenByCode(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        url = url.replaceAll("APPID", WeChatConfig.appid).
                replaceAll("SECRET", WeChatConfig.secret).
                replaceAll("CODE", code);

        JSONObject jsonObject = HttpsUtil.sendRequest(url, HttpsUtil.GET, null);
//            Integer errCode = jsonObject.getInt("errcode");
//            if (errCode.compareTo(0) != 0) {
//                throw new WeChatException(errCode);
//            }

        return jsonObject;
    }

    /**
     * 根据网址生成微信授权地址
     * @param url
     * @param base 授权方式: true: 简易授权(snsapi_base)，仅能获取OpenId
     *                      false: 标准授权(snsapi_userinfo)，需跳转至授权页，可获取昵称头像所在地等信息
     * @return
     */
    public static String createAuthorizationUrl(String url, Boolean base) {
        String authorizationUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
        authorizationUrl = authorizationUrl.replaceAll("APPID", WeChatConfig.appid).
                replaceAll("STATE", "123").
                replaceAll("REDIRECT_URI", url);

        // 根据不同授权方式生成不同网址
        if (base == true) {
            authorizationUrl = authorizationUrl.replaceAll("SCOPE", "snsapi_base");
        } else {
            authorizationUrl = authorizationUrl.replaceAll("SCOPE", "snsapi_userinfo");
        }

        return authorizationUrl;
    }

    /**
     * 获取用户信息(当Scope为snsapi_userinfo时才可用)
     * @param openId
     * @param accessToken
     * @return
     */
    public static JSONObject getUserInfo(String openId, String accessToken) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        url = url.replaceAll("ACCESS_TOKEN", accessToken).
                replaceAll("OPENID", openId);

        JSONObject jsonObject = HttpsUtil.sendRequest(url, HttpsUtil.GET, null);
//            Integer errCode = jsonObject.getInt("errcode");
//            if (errCode.compareTo(0) != 0) {
//                throw new WeChatException(errCode);
//            }

        return jsonObject;
    }
}
