package com.zhou.textinclientsdk.utils;

import com.zhou.textinclientsdk.common.BusinessException;
import com.zhou.textinclientsdk.common.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 合合信息 发送 AI 调用请求
 */
@Slf4j
public class TextlnSendUtils {

    /**
     * 发送请求给 AI
     * @param requestUrl 请求 url
     * @param image 需要解析的图片
     * @return 解析后字符串
     */
    public static String CallAI (String requestUrl, MultipartFile image, String appId, String secretCode) {
        // 从 MultipartFile 中获取图片数据
        byte[] imgData;
        try {
            imgData = image.getBytes();
        } catch (IOException e) {
            log.error("Error reading image file", e);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件错误");
        }

        // 通用文字识别
        String url = requestUrl;
        BufferedReader in = null;
        DataOutputStream out = null;
        String result = "";
        // todo 引入 Guava Retrying 重试机制
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setRequestProperty("x-ti-app-id", appId);
            conn.setRequestProperty("x-ti-secret-code", secretCode);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST"); // 设置请求方式
            out = new DataOutputStream(conn.getOutputStream());
            out.write(imgData);
            out.flush();
            out.close();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("发送 POST 请求出现异常！" + e);
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error("关闭图片识别流出现异常！" + ex);
            }
        }

        return result;
    }

    /**
     * 图片转 PDF 发送 AI 请求
     */
    public static String CallAIWithImageToPdf(String requestUrl, MultipartFile image, String appId, String secretCode) {
        // 从 MultipartFile 中获取图片数据
        byte[] imgData;
        try {
            imgData = image.getBytes();
        } catch (IOException e) {
            log.error("Error reading image file", e);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件错误");
        }

        // 构造JSON请求体
        String base64Image = Base64.encodeBase64String(imgData);;
        String jsonBody = "{\"files\": [\"" + base64Image + "\"]}";

        // 通用文字识别
        String url = requestUrl;
        BufferedReader in = null;
        DataOutputStream out = null;
        String result = "";

        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");  // Set Content-Type to application/json
            conn.setRequestProperty("x-ti-app-id", appId);
            conn.setRequestProperty("x-ti-secret-code", secretCode);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST"); // 设置请求方式

            // Write JSON body to the request
            out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(jsonBody);
            out.flush();

            // Read response from the server
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("发送 POST 请求出现异常！" + e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error("关闭图片识别流出现异常！" + ex);
            }
        }

        return result;
    }

}
