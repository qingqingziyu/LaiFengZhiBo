package com.lk.comecrazy.myself.view;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lk.comecrazy.R;
import com.lk.comecrazy.base.BaseActivity;
import com.lk.comecrazy.utils.PayResult;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.OnClick;

public class MoneyActivity extends BaseActivity {


    @BindView(R.id.myself_money_toolbar)
    Toolbar mMoneyToolbar;
    @BindView(R.id.money_count)
    TextView mMoneyCount;
    @BindView(R.id.money_pay)
    TextView mMoneyPay;

    private int SDK_PAY_FLAG = 1;


    //初始化布局
    @Override
    protected void initView() {

        setIconBack();
    }

    //设置图标返回
    private void setIconBack() {
        mMoneyToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

//    //初始化布局
//    private void initView() {
//        mBackBar = (Toolbar) findViewById(R.id.myself_money_toolbar);
//
//    }

    //加载布局
    @Override
    public int getLayoutId() {
        return R.layout.activity_money;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

            if (msg.what == SDK_PAY_FLAG) {
                PayResult payResult = new PayResult((String) msg.obj);
                //获取支付的状态码
                String resultStatus = payResult.getResultStatus();
                if ("9000".equals(resultStatus)) {
                    Toast.makeText(MoneyActivity.this, "支付成功!", Toast.LENGTH_SHORT).show();
                } else {
                    if ("8000".equals(resultStatus)) {
                        Toast.makeText(MoneyActivity.this, "正在处理中...", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MoneyActivity.this, "支付失败!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }

        ;
    };


//    final String orderInfo = "{\"memo\" : \"冲会员\",\"result\" : \"\n" +
//            "{\\\"alipay_trade_app_pay_response\\\":\n" +
//            "{\\\"code\\\":\\\"10000\\\",\\\"msg\\\":\\\"Success\\\",\\\"app_id\\\":\\\"2016102200007148\\\",\\\"out_trade_no\\\":\\\"081622560194853\\\",\\\"trade_no\\\":\\\"2016081621001004400236957647\\\",\\\"total_amount\\\":\\\"0.01\\\",\\\"seller_id\\\":\\\"2088702849871851\\\",\\\"charset\\\":\\\"utf-8\\\"},\n" +
//            "\\\"sign\\\":\\\"NGfStJf3i3ooWBuCDIQSumOpaGBcQz+aoAqyGh3W6EqA/gmyPYwLJ2REFijY9XPTApI9YglZyMw+ZMhd3kb0mh4RAXMrb6mekX4Zu8Nf6geOwIa9kLOnw0IMCjxi4abDIfXhxrXyj********\\\",\\\"sign_type\\\":\\\"RSA\\\"\n" +
//            "}\",\n" +
//            " \"resultStatus\" : \"9000\"\n" +
//            "}";   // 订单信息

    Runnable payRunnable = new Runnable() {

        @Override
        public void run() {
            PayService payService = new PayService();

            try {
                //服务端生成的订单信息!
                String orderInfo = payService.getOrderInfo("黄金VIP", "只要一分钱，物有所值", "0.01");
                //服务端生成的签名方法:对订单信息进行签名
                String sign = payService.sign(orderInfo);
                //服务端:进行URLEncoder的编码
                sign = URLEncoder.encode(sign, "UTF-8");
                PayTask alipay = new PayTask(MoneyActivity.this);
                String result = alipay.pay(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


        }
    };


    //付款
    @OnClick(R.id.money_pay)
    public void onClick() {
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
