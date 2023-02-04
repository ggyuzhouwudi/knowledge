package kaptcha.controller;

import java.awt.image.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import com.google.code.kaptcha.Producer;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Oliver
 * @date 2023年01月06日 17:29
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KaptchaController {

    private final Producer producer;

    @GetMapping("/get-kaptcha")
    public String get(HttpSession session) throws IOException {
        // 生成验证码
        String code = producer.createText();
        session.setAttribute("kaptcha",code);
        BufferedImage image = producer.createImage(code);
        FastByteArrayOutputStream fos = new FastByteArrayOutputStream();
        ImageIO.write(image,"png",fos);
        return Base64.encodeBase64String(fos.toByteArray());
    }

}
