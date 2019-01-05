package com.baidu.speech.restapi.common;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;



/**
 * 语音合成 WebAPI 接口调用示例
 * 
 * 运行方法：直接运行 main() 即可
 * 
 * 结果： 若合成成功，则音频保存在resouce目录，文件名为 sid； 若合成失败，控制台输出错误信息
 * 
 * @author iflytek
 * 
 */
public class WebTTs {
	private static final String WEBTTS_URL = "http://api.xfyun.cn/v1/service/v1/tts";
	// 应用ID
	private static final String APPID = "5c30acb5";
	// 接口密钥
	private static final String API_KEY = "ddbc2786747b84f22ee3ab76394e479b";
//	// 待合成文本
//	private static final String TEXT =null;
	// 音频编码
	private static final String AUE = "lame";
	// 采样率
	private static final String AUF = "audio/L16;rate=16000";
	// 语速
	private static final String SPEED = "50";
	// 音量
	private static final String VOLUME = "50";
	// 音调
	private static final String PITCH = "50";
	// 发音人
	private static final String VOICE_NAME = "aisxping";
	// 引擎类型
	private static final String ENGINE_TYPE = "intp65";
	// 文本类型
	private static final String TEXT_TYPE = "text";
	/**
	 * 合成 WebAPI 调用示例程序
	 */
	public static void kedaTTS(String name) throws IOException {
		// 合成webapi接口地址

		
		Map<String, String> header = buildHttpHeader();
		
		Map<String, Object> resultMap = HttpUtil.doPost2(WEBTTS_URL, header, "text=" + URLEncoder.encode(name, "utf-8"));
		System.out.println("占用内存大小： "+ URLEncoder.encode(name, "utf-8").getBytes().length);
		if ("audio/mpeg".equals(resultMap.get("Content-Type"))) { // 合成成功
			if ("raw".equals(AUE)) {
				FileUtil.save("D:\\name\\", MD5Util.MD5Encode(name,"utf8") + ".wav", (byte[]) resultMap.get("body"));
				System.out.println("合成 WebAPI 调用成功，音频保存位置：resource\\" + resultMap.get("sid") + ".wav");
			} else {
				FileUtil.save("D:\\name\\", MD5Util.MD5Encode(name,"utf8") + ".mp3", (byte[]) resultMap.get("body"));
				System.out.println("合成 WebAPI 调用成功，音频保存位置：resource\\" + MD5Util.MD5Encode(name,"utf8") + ".mp3");
			}
		} else { // 合成失败
			System.out.println("合成 WebAPI 调用失败，错误信息：" + resultMap.get("body").toString());
		}


	}

	/**
	 * 组装http请求头
	 */
	private static Map<String, String> buildHttpHeader() throws UnsupportedEncodingException {
		String curTime = System.currentTimeMillis() / 1000L + "";
		String param = "{\"auf\":\"" + AUF + "\",\"aue\":\"" + AUE + "\",\"voice_name\":\"" + VOICE_NAME + "\",\"speed\":\"" + SPEED + "\",\"volume\":\"" + VOLUME + "\",\"pitch\":\"" + PITCH + "\",\"engine_type\":\"" + ENGINE_TYPE + "\",\"text_type\":\"" + TEXT_TYPE + "\"}";
		String paramBase64 = new String(Base64.encodeBase64(param.getBytes("UTF-8")));
		String checkSum = DigestUtils.md5Hex(API_KEY + curTime + paramBase64);
		Map<String, String> header = new HashMap<String, String>();
		header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		header.put("X-Param", paramBase64);
		header.put("X-CurTime", curTime);
		header.put("X-CheckSum", checkSum);
		header.put("X-Appid", APPID);
		return header;
	}

}
