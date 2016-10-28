package com.lk.comecrazy.myself.view;

import com.lk.comecrazy.utils.SignUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Administrator on 2016/10/22.
 */
public class PayService {

    //以下字段:必须放在服务器端来写!!!
    // 商户PID
    public static final String PARTNER = "2088411303578161";
    // 商户收款账号
    public static final String SELLER = "2088411303578161";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALUeed/5DK5wEpmc3t2UxelLChZupAahZ4je3svDaaAaliwmnuwQ0y0YIDiKHjdfSz0kbsypuNG4ex+Oj5dY/MKXTrn8Zn4l0ibZf0HSHHab+1G4bQgt58Rm+ee6jBPi7sHXPO1pfY8P/dJsxZM1sNPzR3bSG20FnpTFHSa0DjCXAgMBAAECgYEAnJKXC18Sbm4mNjOdmnI1UUN0mgXVuIGprnH0qrPoyOaS8eIFOtAkrvQPTrFgA8BWm6m2Oylxw6M2lQY57b2Y6u9Gkh/part2Z7cViRMa6dVYyxYCEL7ywVnLHhgfe5m7Y0pUX8rte6zu9Eu5dNXC/kbRQQu+EnkbMJNAnDB445kCQQDeHnWF58q/d25x0B1RIBpjIN/q34liDDx9yvyqSUHJhVFUbjXLFzcF9rmxMxCkIrqz8nceMtH5GzTCLM5ajyZlAkEA0L8B6mTcUzsruv8dvbz0sh67WkvzLmajcvmlCuUVhR3iXxEqumeY62cItO5dnDJ1CeNvHgpZc67QRgZ78SqdSwJAEwUwsyLCLdsgTgawO/4vxEVQZ2B09zxtAaicMkjLpWIRMNWvLZDzWUNja1UgG64NptMgeCmJ6Xb28Zm8oeyYMQJBAJPSz753Unc2bc9snlkhGH+St8ZIBpwTXygqla/Tbpvn/8im1YVPm8EII+hM7MmSCcPI5YV2esYrQ55zNQ4hyWUCQDphe6vJocLj9dm5pwHtNdVahIHfg4WurHk7Aeu777AhgziUEiLkc1a4qNKXzJLqq6I8FswBZzV/gtH/NlrniqQ=";

    /**
     * create the order info. 创建订单信息
     * 服务端生成的方法!!!
     */
    public String getOrderInfo(String subject, String body, String price) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     * 服务端的方法
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     * 服务端创建!
     *
     * @param orderInfo 待签名订单信息
     */
    public String sign(String orderInfo) {
        return SignUtils.sign(orderInfo, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     * 服务端的方法!!!
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

}
