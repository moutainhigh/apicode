package com.ycandyz.master.aliyun.sms;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class AliSmsMessage {

    //对应accessKeyId
    @Value("${aliyun.access.keyId}")
    private String accessKeyId;
    //对应accessKeySercret
    @Value("${aliyun.access.secret}")
    private String accessSecret;
    //对应accessKeySercret
    @Value("${aliyun.access.region}")
    private String regionId;
    //短信API产品域名
    @Value("${aliyun.sms.domain}")
    private String domain;
    //短信API产品域名
    @Value("${aliyun.sms.product}")
    private String product;
    //短信API签名
    @Value("${aliyun.sms.sign}")
    private String signName;

    /**
     * telephone：电话，list集合
     * message：jsonobject字符串，如果没有传null
     * @param telephone [\"1500000000\",\"1500000001\"]
     * @param message   {\"name\":\"Tom\", \"code\":\"123\"}
     */
    public void sendMsg(List<String> telephone, String message, String templateCode){
        if (telephone==null || telephone.size()==0){
            log.error("传入的手机号码不允许为空值!");
            return;
        }
        try {
            //设置超时时间-可自行调整
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
            //初始化ascClient,暂时不支持多region（请勿修改）
            IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId,
                    accessSecret);
            DefaultProfile.addEndpoint(regionId, regionId, product, domain);
            IAcsClient client = new DefaultAcsClient(profile);
            SendBatchSmsRequest request = new SendBatchSmsRequest();
            //使用post提交
            request.setMethod(MethodType.POST);
            //必填:待发送手机号。支持JSON格式的批量调用，批量上限为100个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
            JSONArray phoneArray = new JSONArray(Collections.singletonList(telephone));
            request.setPhoneNumberJson(phoneArray.toJSONString());
            JSONArray signNameArray = new JSONArray();  //发送消息的签名jsonarray
            JSONArray msgArray = new JSONArray();       //发送消息中特殊字符的替换jsonarray
            for (int i=0;i<telephone.size();i++) {
                signNameArray.add(i,signName);
                if (message!=null && !"".equals(message)) {
                    msgArray.add(i, JSONObject.parseObject(message));
                }else {
                    msgArray.add(i, JSONObject.parseObject("{}"));
                }
            }
            //必填:短信签名-支持不同的号码发送不同的短信签名
            request.setSignNameJson(signNameArray.toJSONString());
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(templateCode);
            //必填:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            request.setTemplateParamJson(msgArray.toJSONString());
            //请求失败这里会抛ClientException异常
            SendBatchSmsResponse sendSmsResponse = client.getAcsResponse(request);
            if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                //请求成功
            }else {
                //发送失败
                log.error("消息发送失败,错误码：{};错误内容：{}",sendSmsResponse.getCode(),sendSmsResponse.getMessage());
            }
        }catch (ClientException e){
            log.error(e.getMessage(),e);
        }
    }

    public static void main(String[] args) {
        DateTime dateTime = DateUtil.offset(new Date(), DateField.DAY_OF_YEAR, -7);
        String ste = DateUtil.format(DateUtil.beginOfDay(dateTime),"yyyy-MM-dd HH:mm:ss");
        System.out.println(ste);

    }
}