package com.pandawork.crm.service.other.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.crm.common.enums.other.SerialNumTemplateEnums;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.mapper.other.SerialNumMapper;
import com.pandawork.crm.service.other.SerialNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 流水号Service实现
 *
 * @author: zhangteng
 * @time: 2015/11/11 17:53
 **/
@Service("serialNumService")
public class SerialNumServiceImpl implements SerialNumService {

    @Autowired
    private SerialNumMapper serialNumMapper;

    @Override
    public String generateSerialNum(SerialNumTemplateEnums templateEnums) throws SSException {
        Assert.isNotNull(templateEnums, CrmException.SerialNumTemplateNotNull);
        String template = templateEnums.getTemplate();
        // 字符串"|"第一次出现的位置
        int firstDelimiterIndex = template.indexOf("|");
        // 在位置（包括该位置）firstDelimiterIndex + 1以后，字符串"|"第一次出现的位置
        int secondDelimiterIndex = template.indexOf("|", firstDelimiterIndex + 1);
        // template中第一个"|"之前的子串,取得的是RKD-
        String prefix = template.substring(0, firstDelimiterIndex);
        // RKD-|yyyyMMdd|####，取到的是yyyyMMdd
        String timestampFormat = template.substring(firstDelimiterIndex + 1, secondDelimiterIndex);
        // 取到的是####
        String numberFormat = template.substring(secondDelimiterIndex + 1);
        // 用"0"替代#
        numberFormat = numberFormat.replaceAll("#", "0");

        // 生成流水号
        SimpleDateFormat sdf = new SimpleDateFormat(timestampFormat);
        String timestamp = sdf.format(new Date());
        String serialKey = prefix + timestamp;
        Integer number = 0;
        try {
            number = serialNumMapper.querySerialNum(serialKey);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.SystemException, e);
        }
        DecimalFormat decimalFormat = new DecimalFormat(numberFormat);
        String numberStr = decimalFormat.format(number);

        return serialKey + numberStr;
    }
}
