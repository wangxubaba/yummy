package com.wx.yummy.serviceImpl;

import com.wx.yummy.bean.user.Address;
import com.wx.yummy.service.MapService;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MapServiceImpl implements MapService {
    private JSONObject interfaceUtil(String path, String data) {
        JSONObject jsonObject=null;
        try {
            URL url = new URL(path);
            //打开和url之间的连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            PrintWriter out = null;
            //请求方式
            conn.setRequestMethod("GET");
//           //设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            //设置是否向httpUrlConnection输出，设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            //最常用的Http请求无非是get和post，get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，
            //post与get的 不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //发送请求参数即数据
            out.print(data);
            //缓冲数据
            out.flush();
            //获取URLConnection对象对应的输入流
            InputStream is = conn.getInputStream();

            //构造一个字符流缓存
            BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            String str = "";
            String result="";
            while ((str = br.readLine()) != null) {
                result=result+str;
            }
            jsonObject = JSONObject.fromObject(result);
            //关闭流
            is.close();
            //断开连接，最好写上，disconnect是在底层tcp socket链接空闲时才切断。如果正在被其他线程使用就不切断。
            //固定多线程的话，如果不disconnect，链接会增多，直到收发不出信息。写上disconnect后正常一些。
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;

    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    private String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    private String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public ArrayList<Address> getRecommendPlaces(String input, String province, String city, String area){
        String path="http://api.map.baidu.com/place/v2/search?query="+input+"&region="+city+"&output=json&ak=vPbdC77XMBx9tigRY6ivehak0qmGeRC8";
        JSONObject jsonObject=interfaceUtil(path,"");
        ArrayList<Address> result=new ArrayList<>();
        try {
            JSONArray jsonArray=jsonObject.getJSONArray("results");
            for(int i=0;i<jsonArray.size();i++){
                JSONObject info= (JSONObject) jsonArray.get(i);
                Address address=new Address();
                address.setProvince(province);
                address.setCity(city);
                address.setArea(area);
                address.setDetail(info.getString("name")+" "+info.getString("address"));
                JSONObject location=info.getJSONObject("location");
                address.setLat(location.getDouble("lat"));
                address.setLng(location.getDouble("lng"));
                result.add(address);
            }
        }catch (JSONException e){
            return result;
        }catch (NullPointerException e){
            return result;
        }
        return result;
    }

    @Override
    public double calculateDistance(Address address1, Address address2) {
        String url="http://api.map.baidu.com/direction/v2/riding";
        String param="origin="+address1.getLat()+","+address1.getLng()+"&destination="+address2.getLat()+","+address2.getLng()+"&ak=vPbdC77XMBx9tigRY6ivehak0qmGeRC8";
        String back=sendGet(url,param);
        JSONObject jsonObject=JSONObject.fromObject(back);
        try {
            JSONObject result=jsonObject.getJSONObject("result");
            JSONArray array=result.getJSONArray("routes");
            JSONObject temp= (JSONObject) array.get(0);
            double distance=temp.getDouble("distance")/1000.0;
            return distance;
        }catch (JSONException e){
            return 1000;
        }
    }
}
