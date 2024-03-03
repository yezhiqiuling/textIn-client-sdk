package com.zhou.textinclientsdk.constant;

/**
 * 图片文字识别常量
 */
public interface TextlnConstant {

    /**
     * 图片文字识别 => 请求 url
     */
    String RECOGNIZE_URL = "https://api.textin.com/ai/service/v2/recognize";

    /**
     * 图片表格识别 => 请求 url
     */
    String TABLE_URL = "https://api.textin.com/ai/service/v2/recognize/table";

    /**
     * 图片切边增强 => 请求 url
     */
    String CROP_ENHANCE_IMAGE_URL = "https://api.textin.com/ai/service/v1/crop_enhance_image";

    /**
     * 图片转 PDF => 请求 url
     */
    String IMAGE_TO_PDF_URL = "https://api.textin.com/ai/service/v1/file-convert/image-to-pdf";


}
