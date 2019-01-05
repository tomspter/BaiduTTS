package com.baidu.speech.restapi.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class TTSUtil {
    public static void download(String text) throws IOException, DemoException {
        //  填写网页上申请的appkey 如 $apiKey="g8eBUMSokVB1BHGmgxxxxxx"
        final String appKey = "hE6oPvpDd00ByN5qhwc27k2r";

        // 填写网页上申请的APP SECRET 如 $secretKey="94dc99566550d87f8fa8ece112xxxxx"
        final String secretKey = "1uYCNpGKR554G4VChe0QMvobh1ZIx7SX";

        // text 的内容为"欢迎使用百度语音合成"的urlencode,utf-8 编码
        // 可以百度搜索"urlencode"
//    private final String text = "欢迎使用百度语音";


        // 发音人选择, 0为普通女声，1为普通男生，3为情感合成-度逍遥，4为情感合成-度丫丫，默认为普通女声
        final int per = 1;
        // 语速，取值0-15，默认为5中语速
        final int spd = 1;
        // 音调，取值0-15，默认为5中语调
        final int pit = 5;
        // 音量，取值0-9，默认为5中音量
        final int vol = 9;

        // 下载的文件格式, 3：mp3(default) 4： pcm-16k 5： pcm-8k 6. wav
        final int aue = 6;

        final String url = "http://tsn.baidu.com/text2audio"; // 可以使用https

        final String cuid = "tomspter";

        TokenHolder holder = new TokenHolder(appKey, secretKey, TokenHolder.TTS_SCOPE);
        holder.refresh();
        String token = holder.getToken();

        // 此处2次urlencode， 确保特殊字符被正确编码
        String params = "tex=" + ConnUtil.urlEncode(ConnUtil.urlEncode(text));
        params += "&per=" + per;
        params += "&spd=" + spd;
        params += "&pit=" + pit;
        params += "&vol=" + vol;
        params += "&cuid=" + cuid;
        params += "&tok=" + token;
        params += "&aue=" + aue;
        params += "&lan=zh&ctp=1";
        System.out.println(url + "?" + params); // 反馈请带上此url，浏览器上可以测试
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setConnectTimeout(5000);
        PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
        printWriter.write(params);
        printWriter.close();
        String contentType = conn.getContentType();
        if (contentType.contains("audio/")) {
            byte[] bytes = ConnUtil.getResponseBytes(conn);

            //TODO
            String path="D:\\name\\";


            File dir=new File(path);
            if (!dir.exists()){
                dir.mkdir();
            }


            String format = "mp3";
            String in =MD5Util.MD5Encode(text,"utf8");
            File file = new File(path+in+"." + format);
            FileOutputStream os = new FileOutputStream(file);
            os.write(bytes);
            os.close();
            System.out.println("audio file write to " + file.getAbsolutePath());
        } else {
            System.err.println("ERROR: content-type= " + contentType);
            String res = ConnUtil.getResponseString(conn);
            System.err.println(res);
        }
    }

    // 下载的文件格式, 3：mp3(default) 4： pcm-16k 5： pcm-8k 6. wav
    public String getFormat(int aue) {
        String[] formats = {"mp3", "pcm", "pcm", "wav"};
        return formats[aue - 3];
    }

    private static void isDirExist(File dir){
        if(!dir.exists()){
            dir.mkdir();
        }
    }
}
