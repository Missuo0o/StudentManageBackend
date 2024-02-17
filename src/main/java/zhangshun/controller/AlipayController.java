package zhangshun.controller;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import zhangshun.domain.Code;
import zhangshun.domain.Dormitory;
import zhangshun.domain.Result;
import zhangshun.domain.User;
import zhangshun.service.AlipayService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;

@RestController
public class AlipayController {
    @Autowired
    private AlipayService alipayService;

    @GetMapping("/student/getDomInfo")
    public Result GetDomInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        Dormitory dormitory = alipayService.GetDormitoryInfo(user.getUsername());
        int code = dormitory != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(dormitory, code);
    }

    //判断是否支付
    @GetMapping("/student/getPaymentStatus/{tradeId}")
    public Result GetPaymentStatus(@PathVariable String tradeId, HttpServletRequest request) {
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipay.com";
        config.signType = "RSA2";

        config.appId = "";

        config.merchantPrivateKey ="";
        config.alipayPublicKey="";
        Factory.setOptions(config);
        try {
            AlipayTradeQueryResponse query = Factory.Payment.Common()
                    .query(tradeId);
            if (!"0.00".equals(query.buyerPayAmount)) {
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("username");
                boolean flag = alipayService.StatusUpdate(user.getUsername());
                return new Result(flag, flag ? Code.GET_OK : Code.GET_ERR);
            } else {
                return new Result(Code.GET_ERR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(Code.GET_ERR);
    }

    //生成二维码
    @GetMapping("/student/getPaymentQRCode")
    public Result GetQRCode(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        Dormitory dormitory = alipayService.GetDormitoryInfo(user.getUsername());
        if (dormitory == null) {
            return new Result(Code.GET_ERR);
        }

        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipay.com";
        config.signType = "RSA2";

        config.appId = "";

        config.merchantPrivateKey ="";
        config.alipayPublicKey="";

        Random ran1 = new Random();
        Factory.setOptions(config);
        try {
            // 2. 发起API调用（以创建当面付收款二维码为例）
            AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace()
                    .preCreate(String.valueOf(dormitory.getId()), "zs" + (10000 + ran1.nextInt(10000)), "" + dormitory.getPrice());
            // 3. 处理响应或异常

            if (ResponseChecker.success(response)) {
                System.out.println("调用成功");
                return new Result(response.getQrCode(), Code.GET_OK, response.outTradeNo);
            } else {
                System.err.println("调用失败，原因：" + response.msg + "，" + response.subMsg);
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
        return new Result(Code.GET_ERR);
    }

}
