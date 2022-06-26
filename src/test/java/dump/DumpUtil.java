package dump;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class DumpUtil {

    private static String path = "E:/document/documents.json";

    public static void dump(String content) {
        //路径
        File file = new File(path);
        //判断路径是否存在，不存在就创建
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdir();
        }
        try{
            file.createNewFile();
            //写入的路径 和 编码格式
            Writer writer = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
            //循环输出XXX条数据随意改（输出10000条json数据）
            writer.write(content + "\n");
            writer.flush();
            writer.close();
        }catch (Exception exception){
            System.out.println("dump error !");
        }
    }
}
