package searchproject.php.yisa.chengyansy.utils;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/7/25 0025.
 */

public class HttpConnectionUtils {
    public static boolean isNeedSubString=false;
    public static int subNumber=25;//截取图片长度
    public static String Ln="36.8667973600,118.7787162300";//经纬度
    public static Boolean isNormal=false;//是否为正常模式 true正常模式(能直接转ip） false非正常模式（需要转换IP）
    public static String  ipport="10.49.222.84:8088"; //本地 //192.168.2.86:3000 http://10.50.235.37:8088    10.52.195.9:38088 127.0.0.1:10058 烟台
    public static String  ip="10.49.222.84";
    public static String  bkport=":2124";
    public static String  zbport=":2125";
  //  public static String  ipport="192.168.20.2:10180"; //192.168.2.86:3000 http://10.50.235.37:8088    10.52.195.9:38088 127.0.0.1:10058
   // public static String  ip="192.168.20.2";
//    public static String  ipport="10.50.235.37:8088"; //192.168.2.86:3000 http://10.50.235.37:8088
//    public static String  ip="10.50.235.37";
    //post请求
    public  static String submitPostData(final String strUrlPath, Map<String, String> params, String encode) {

        byte[] data = getRequestData(params, encode).toString().getBytes();//获得请求体
        try {
            //String urlPath = "http://192.168.1.9:80/JJKSms/RecSms.php";
            URL url = new URL(strUrlPath);
            Log.i("url",strUrlPath);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(5000);     //设置连接超时时间
            httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
            httpURLConnection.setRequestMethod("POST");     //设置以Post方式提交数据
            httpURLConnection.setUseCaches(false);               //使用Post方式不能使用缓存
            //设置请求体的类型是文本类型
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求体的长度
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
            //获得输出流，向服务器写入数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);
            final int response = httpURLConnection.getResponseCode();//获得服务器的响应码
            Log.e("response", Integer.toString(response));
            if(response == HttpURLConnection.HTTP_OK) {

                Log.e("response", String.valueOf(response));

                InputStream inptStream = httpURLConnection.getInputStream();
                return dealResponseResult(inptStream);                     //处理服务器的响应结果
            }
        } catch (IOException e) {
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(context,"网络异常！",Toast.LENGTH_SHORT).show();
//                }
//            });
            e.printStackTrace();
            return null;
        }
        return null;
    }
    private static StringBuffer getRequestData(Map<String, String> params, String encode) {
        StringBuffer stringBuffer = new StringBuffer();        //存储封装好的请求体信息
        try {
            for(Map.Entry<String, String> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), encode))
                        .append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }
    private static String dealResponseResult(InputStream inputStream) {
        String resultData = null;      //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }
    private String toDateString(String json, boolean check){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(json, pos);
        Calendar calendar   =   new GregorianCalendar();
        calendar.setTime(strtodate);
        if(check==true) {
            calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
            calendar.add(calendar.HOUR,59);
            calendar.add(calendar.MINUTE,59);
        }else{
            calendar.add(calendar.DATE, -3);
//            calendar.add(calendar.HOUR,-1);
//            calendar.add(calendar.MINUTE,-1);
        }
        strtodate=calendar.getTime();   //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter1.format(strtodate);
        return dateString;
    }
    //get请求

    public static String sendGETRequest(String path, final Map<String, String> params,String token) {
         String result =null;


                byte[] data = null;
                InputStream is = null;
                ByteArrayOutputStream baos = null;
                OutputStream os = null;
                HttpURLConnection conn;
                try {
                    //发送地http://192.168.100.91:8080/videoService/login?username=abc&password=123
                    // StringBuilder是用来组拼请求地址和参数
                    StringBuilder sb = new StringBuilder();
                    sb.append(path).append("?");
                    if (params != null && params.size() != 0) {
                        for (Map.Entry<String, String> entry : params.entrySet()) {
//如果请求参数中有中文，需要进行URLEncoder编码
                            sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8"));
                            sb.append("&");
                        }
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    Log.e("url", sb.toString());
                    URL url = new URL("http://" + sb.toString());
                    conn = (HttpURLConnection) url.openConnection();
                    if(token!=null) {
                   //     conn.setRequestProperty("Authorization","token " +token);
                   conn.addRequestProperty("Authorization","token " +token);
                    }
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    conn.connect();
                    if (conn.getResponseCode() == 200) {
                        is = conn.getInputStream();
                        baos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024 * 8];
                        int size = 0;
                        while ((size = is.read(buffer)) >= 1) {
                            baos.write(buffer, 0, size);
                        }
                        data = baos.toByteArray();
                        result = new String(data);

                        return result;



                    }
                    else {
                     return Integer.toString(conn.getResponseCode());
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }


        return "2";
    }
    public static String sendGETRequestRight(String path, final Map<String, String> params,String token) {
        String result =null;


        byte[] data = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        OutputStream os = null;
        HttpURLConnection conn;
        try {
            //发送地http://192.168.100.91:8080/videoService/login?username=abc&password=123
            // StringBuilder是用来组拼请求地址和参数
            StringBuilder sb = new StringBuilder();
            sb.append(path).append("?");
            if (params != null && params.size() != 0) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
//如果请求参数中有中文，需要进行URLEncoder编码
                    sb.append(entry.getKey()).append("=").append(entry.getValue());
                    sb.append("&");
                }
                sb.deleteCharAt(sb.length() - 1);
            }
            Log.e("url", sb.toString());
            URL url = new URL("http://" + sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            if(token!=null) {
                //     conn.setRequestProperty("Authorization","token " +token);
                conn.addRequestProperty("Authorization","token " +token);
            }
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.connect();
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024 * 8];
                int size = 0;
                while ((size = is.read(buffer)) >= 1) {
                    baos.write(buffer, 0, size);
                }
                data = baos.toByteArray();
                result = new String(data);
                return result;
            }
            else {
                return Integer.toString(conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "2";
    }
    public static String sendGETRequestLoad(String path, final Map<String, String> params,String token) {
        String result =null;
        byte[] data = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        OutputStream os = null;
        HttpURLConnection conn;
        try {
            //发送地http://192.168.100.91:8080/videoService/login?username=abc&password=123
            // StringBuilder是用来组拼请求地址和参数
            StringBuilder sb = new StringBuilder();
            sb.append(path).append("?");
            if (params != null && params.size() != 0) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
//如果请求参数中有中文，需要进行URLEncoder编码
                    sb.append(entry.getKey()).append("=").append(entry.getValue());
                    sb.append("&");
                }
                sb.deleteCharAt(sb.length() - 1);
            }
            Log.e("url", sb.toString());
            URL url = new URL("http://" + sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            if(token!=null) {
                //     conn.setRequestProperty("Authorization","token " +token);
                conn.addRequestProperty("Authorization","token " +token);
            }
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.connect();
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024 * 8];
                int size = 0;
                while ((size = is.read(buffer)) >= 1) {
                    baos.write(buffer, 0, size);
                }
                data = baos.toByteArray();
                result = new String(data);

                return result;



            }
            else {
                return Integer.toString(conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }


        return "2";
    }
    public static String  sendGETRequestRightText(String path,final Map<String, String> params,String token) {
        String result =null;


        byte[] data = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        OutputStream os = null;
        HttpURLConnection conn;
        try {
            //发送地http://192.168.100.91:8080/videoService/login?username=abc&password=123
          // StringBuilder是用来组拼请求地址和参数
//            StringBuilder sb = new StringBuilder();
//            sb.append(path).append("?");
//            if (params != null && params.size() != 0) {
//                for (Map.Entry<String, String> entry : params.entrySet()) {
////如果请求参数中有中文，需要进行URLEncoder编码
//                    sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8"));
//                    sb.append("&");
//                }
//                sb.deleteCharAt(sb.length() - 1);
//            }
//            Log.e("url", sb.toString());
            StringBuilder sbb = new StringBuilder();
            sbb.append(path).append("?");
            if (params != null && params.size() != 0) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
//如果请求参数中有中文，需要进行URLEncoder编码
                    sbb.append(entry.getKey()).append("=").append(entry.getValue());
                    sbb.append("&");
                }
                sbb.deleteCharAt(sbb.length() - 1);
            }
           Log.e("url2",sbb.toString());
        } catch (Exception e) {
            e.printStackTrace();

        }


        return "2";
    }
    public static String uploadFile(File file, String RequestURL, Map<String, String> param, String token){
        String result = "";
        String  BOUNDARY =  UUID.randomUUID().toString();  //边界标识   随机生成
        String PREFIX = "--" , LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data";   //内容类型
        // 显示进度框
//      showProgressDialog();
        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setDoInput(true);  //允许输入流
            conn.setDoOutput(true); //允许输出流
            conn.setUseCaches(false);  //不允许使用缓存
            conn.setRequestMethod("POST");  //请求方式
            conn.setRequestProperty("Charset", "utf-8");  //设置编码
            conn.setRequestProperty("connection", "keep-alive");
            if(token!=null) {
                //     conn.setRequestProperty("Authorization","token " +token);
                conn.addRequestProperty("Authorization","token " +token);
            }
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            if(file!=null){
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                DataOutputStream dos = new DataOutputStream( conn.getOutputStream());
                StringBuffer sb = new StringBuffer();

                String params = "";
                if (param != null && param.size() > 0) {
                    Iterator<String> it = param.keySet().iterator();
                    while (it.hasNext()) {
                        sb = null;
                        sb = new StringBuffer();
                        String key = it.next();
                        String value = param.get(key);
                        sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
                        sb.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(LINE_END).append(LINE_END);
                        sb.append(value).append(LINE_END);
                        params = sb.toString();
                        Log.i("key", key+"="+params+"##");
                        dos.write(params.getBytes());
//                      dos.flush();
                    }
                }
                sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意：
                 * name里面的值为服务器端需要key   只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的   比如:abc.pngu767
                 */
                sb.append("Content-Disposition: form-data; name=\"file\";filename=\""+file.getName()+"\""+LINE_END);
                sb.append("Content-Type: image/pjpeg; charset="+"utf-8"+LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while((len=is.read(bytes))!=-1){
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
                dos.write(end_data);

                dos.flush();
                /**
                 * 获取响应码  200=成功
                 * 当响应成功，获取响应的流
                 */

                int res = conn.getResponseCode();
                System.out.println("res========="+res);
                ByteArrayOutputStream baos = null;
                if(res==200){
                    InputStream input =  conn.getInputStream();
//                    StringBuffer sb1= new StringBuffer();
//                    int ss ;
//                    while((ss=input.read())!=-1){
//                        sb1.append((char)ss);
//                    }
//
//                    result = sb1.toString();

//                    is = conn.getInputStream();
//                    baos = new ByteArrayOutputStream();
//                    byte[] buffer = new byte[1024 * 8];
//                    byte[] data = null;
//                    int size = 0;
//                    while ((size = is.read(buffer)) >= 1) {
//                        baos.write(buffer, 0, size);
//
//                    }
//
//                    data = baos.toByteArray();
//                    result = data.toString();
                    String resultData = null;      //存储处理结果
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] data = new byte[1024];

                    try {
                        while((len = input.read(data)) != -1) {
                            byteArrayOutputStream.write(data, 0, len);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    resultData = new String(byteArrayOutputStream.toByteArray());
                    return resultData;
//                 // 移除进度框
//                  removeProgressDialog();
                    //    finish();
                }
                else{
                    return "后台报错"+Integer.toString(res);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String newString(String src){
        if (isNeedSubString==true){
            return "http://"+ HttpConnectionUtils.ipport+src.substring(subNumber);
        }else {
            return src;
        }


    }
}
