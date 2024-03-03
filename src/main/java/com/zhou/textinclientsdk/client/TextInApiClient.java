package com.zhou.textinclientsdk.client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zhou.textinclientsdk.common.ErrorCode;
import com.zhou.textinclientsdk.common.ThrowUtils;
import com.zhou.textinclientsdk.constant.TextlnConstant;
import com.zhou.textinclientsdk.constant.TextlnErrorCodeEnum;
import com.zhou.textinclientsdk.utils.TextlnSendUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 调用第三方接口的客户端
 */
@Slf4j
public class TextInApiClient {

    private String appId;

    private String secretCode;

    public TextInApiClient(String appId, String secretCode) {
        this.appId = appId;
        this.secretCode = secretCode;
    }

    public String testConnect() {
        return "恭喜你！连接成功！\n You know, for TextIn";
    }

    /**
     * 图片文字识别
     * @param image
     * @return
     */
    public List<String> judgeImage (MultipartFile image) {
        String response = TextlnSendUtils.CallAI(TextlnConstant.RECOGNIZE_URL, image, appId, secretCode);
        // 解析结果 json 字符串
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);

        // 校验返回值
        int code = jsonObject.get("code").getAsInt();
        ThrowUtils.throwIf(!Objects.equals(code, 200), ErrorCode.SYSTEM_ERROR, TextlnErrorCodeEnum.getMessageByCode(code));

        // 获取 "lines" 数组 => 每行文字的识别结果
        JsonArray linesArray = jsonObject.getAsJsonObject("result").getAsJsonArray("lines");

        List<String> textList = new ArrayList<>();

        // 遍历 "lines" 数组
        for (JsonElement lineElement : linesArray) {
            JsonObject lineObject = lineElement.getAsJsonObject();
            // 获取 "text" 字段的值
            String text = lineObject.get("text").getAsString();
            textList.add(text);
        }

        return textList;
    }

    /**
     * 识别图片中的表格
     * @param image
     * @return  base 64 编码的文件数据 => 需要前端解码
     */
    public String judgeTableReturnFile (MultipartFile image) {
        // 修改 url, 设置参数 excel = 1 表示返回 base 64 格式的 excel 文件
        String table_url = TextlnConstant.TABLE_URL + "?excel=1";
        // 调用接口，发送请求
        String response = TextlnSendUtils.CallAI(table_url, image, appId, secretCode);

        // 使用 Gson 解析 JSON 字符串为 JsonObject
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);

        // 校验返回值
        int code = jsonObject.get("code").getAsInt();
        ThrowUtils.throwIf(!Objects.equals(code, 200), ErrorCode.SYSTEM_ERROR, TextlnErrorCodeEnum.getMessageByCode(code));

        // 获取 base 64 编码的文件字符串
        String fileStr = jsonObject.getAsJsonObject("result").get("excel").getAsString();

        return fileStr;
    }

    /**
     * 图片切边增强
     * @param image
     * @return  base 64 编码的文件数据
     */
    public List<String> cropEnhanceImage (MultipartFile image) {
        // 调用接口，发送请求
        String response = TextlnSendUtils.CallAI(TextlnConstant.CROP_ENHANCE_IMAGE_URL, image, appId, secretCode);

        // 使用 Gson 解析 JSON 字符串为 JsonObject
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);

        // 校验返回值
        int code = jsonObject.get("code").getAsInt();
        ThrowUtils.throwIf(!Objects.equals(code, 200), ErrorCode.SYSTEM_ERROR, TextlnErrorCodeEnum.getMessageByCode(code));

        // 获取 base 64 编码的文件字符串
        List<String> imageStrList = new ArrayList<>();
        JsonArray jsonArray = jsonObject.getAsJsonObject("result").getAsJsonArray("image_list");
        for (JsonElement jsonElement : jsonArray) {
            JsonObject asJsonObject = jsonElement.getAsJsonObject();
            String imageStr = asJsonObject.get("image").getAsString();
            imageStrList.add(imageStr);
        }

        return imageStrList;
    }

    /**
     * 图片转 pdf
     * @param image
     * @return  base 64 编码的文件数据
     */
    public String imageToPdf (MultipartFile image) {
        // 调用接口，发送请求
        String response = TextlnSendUtils.CallAIWithImageToPdf(TextlnConstant.IMAGE_TO_PDF_URL, image, appId, secretCode);

        // 使用 Gson 解析 JSON 字符串为 JsonObject
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);

        // 校验返回值
        int code = jsonObject.get("code").getAsInt();
        ThrowUtils.throwIf(!Objects.equals(code, 200), ErrorCode.SYSTEM_ERROR, TextlnErrorCodeEnum.getMessageByCode(code));

        // 获取 base 64 编码的文件字符串
        String fileStr = jsonObject.get("result").getAsString();

        return fileStr;
    }





}
