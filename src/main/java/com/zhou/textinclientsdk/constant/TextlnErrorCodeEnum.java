package com.zhou.textinclientsdk.constant;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Objects;

/**
 * 合合信息请求错误码
 */
public enum TextlnErrorCodeEnum {

    SUCCESS(200, "成功"),
    NULL_APP_ID_OR_SECRET_CODE(40101, "x-ti-app-id 或 x-ti-secret-code 为空"),
    INVALID_APP_ID_OR_SECRET_CODE(40102, "x-ti-app-id 或 x-ti-secret-code 无效，验证失败"),
    CLIENT_IP_NOT_IN_WHITE_LIST(40103, "客户端IP不在白名单"),
    INSUFFICIENT_BALANCE(40003, "余额不足，请充值后再使用"),
    INVALID_PARAMETERS(40004, "参数错误，请查看技术文档，检查传参"),
    INACTIVE_OR_NONEXISTENT_ROBOT(40007, "机器人不存在或未发布"),
    INACTIVE_ROBOT(40008, "机器人未开通，请至市场开通后重试"),
    UNSUPPORTED_IMAGE_TYPE(40301, "图片类型不支持"),
    EXCEEDED_FILE_SIZE_LIMIT(40302, "上传文件大小不符，文件大小不超过 10M"),
    UNSUPPORTED_FILE_TYPE(40303, "文件类型不支持"),
    INVALID_IMAGE_SIZE(40304, "图片尺寸不符，图像宽高须介于 20 和 10000（像素）之间"),
    FILE_NOT_UPLOADED(40305, "识别文件未上传"),
    SERVICE_UNAVAILABLE(30203, "基础服务故障，请稍后重试"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误");


    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 信息
     */
    private final String message;

    TextlnErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据 code 获取信息
     *
     * @param code
     * @return
     */
    public static String getMessageByCode(Integer code) {
        if (ObjectUtils.isEmpty(code)) {
            return null;
        }
        for (TextlnErrorCodeEnum errorCode : TextlnErrorCodeEnum.values()) {
            if (Objects.equals(errorCode.code, code)) {
                return errorCode.message;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
