package file;

import com.alibaba.fastjson.JSONObject;
import entity.DataConfig;

import java.io.*;

/**
 * Created by Huppert on 2018/5/27.
 */
public class ConfigFileDao {

    public void saveDataInfo(DataConfig form) throws IOException {
        File fileDir = new File("D:\\Program Files\\haha point");
        File file = new File("D:\\Program Files\\haha point\\config.xml");
        file.setReadable(true);//设置可读权限
        file.setWritable(true);//设置可写权限
        if (!file.exists()) {
            try {
                fileDir.mkdirs();
                file.createNewFile(); // 创建文件
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        String data = JSONObject.toJSONString(form);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(data.getBytes());
        //最后不能忘记关闭流
        fileOutputStream.close();
    }


    public DataConfig loadDataInfo() throws IOException {
        //读取文件(缓存字节流)
        File fileDir = new File("D:\\Program Files\\haha point");
        File file = new File("D:\\Program Files\\haha point\\config.xml");
        file.setReadable(true);//设置可读权限
        file.setWritable(true);//设置可写权限
        if (!file.exists()) {
            try {
                fileDir.mkdirs();
                file.createNewFile(); // 创建文件
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        FileInputStream inputStream = new FileInputStream(file);

        BufferedInputStream in = new BufferedInputStream(inputStream);
        //一次性取多少字节
        byte[] bytes = new byte[2048];

        String data = "";
        int n = -1;
        //循环取出数据
        while ((n = in.read(bytes, 0, bytes.length)) != -1) {
            //转换成字符串
            String str = new String(bytes, 0, n, "GBK");
            data += str;
        }
        in.close();
        DataConfig dataInfo = JSONObject.parseObject(data, DataConfig.class);
        return dataInfo;
    }


}
