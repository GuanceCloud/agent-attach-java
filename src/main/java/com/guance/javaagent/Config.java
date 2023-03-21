package com.guance.javaagent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Config {
    public static final String LINUX_DIR = "/usr/local/ddtrace";

    public static final String WIN_DIR = "D:/usr/local/ddtrace";

    public static final String FILE_NAME="dd-java-agent.jar";

    public String getDir(){
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
           return WIN_DIR;
        } else {
            return LINUX_DIR;
        }
    }
    private String options;

    private String downloadAddress;

    private  String agentJar;

    private String pid;

    private String displayName;

    public String getOptions() {
        return this.options;
    }

    public String getDownloadAddress() {
        return  this.downloadAddress;
    }

    public String getAgentJar() {
        return  this.agentJar;
    }

    public String getPid(){return this.pid;}
    public String getDisplayName(){return this.displayName;}

    private Config(String options,String downloadAddress,String agentJar,String pid,String displayName){
        this.options = options;
        this.downloadAddress = downloadAddress;
        this.agentJar = agentJar;
        this.pid = pid;
        this.displayName = displayName;
    }

    static Config parse(String... args){
        String option = "";
        String downloadAddr= "";
        String currentArg = "";
        String agentJar= "";
        String pid = "";
        String displayName = "";
        for(String arg : args){
            if (arg.startsWith("-")){
                currentArg =arg;
                if (arg.equals("-h") || arg.equals("-help")){
                    printOut();
                    System.exit(1);
                }
            }else {
                switch (currentArg){
                    case "-options":
                        option = arg;
                        break;
                    case "-download":
                        downloadAddr = arg;
                        download(arg,createDir());
                        break;
                    case "-agent-jar":
                         agentJar = arg;
                        break;
                    case "-pid":
                        pid = arg;
                        break;
                    case "-displayName":
                        displayName = arg;
                        break;
                }
            }
        }
        return new Config(option,downloadAddr,agentJar,pid,displayName);
    }

    private static void download(String arg,String path)  {
        try{
            File file = new File(path+"/"+FILE_NAME);
            //创建新文件
            if(file!=null && !file.exists()){
                file.createNewFile();
            }
              //输出流
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file));
            URL url = new URL(arg);
            //获取链接
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true
            uc.connect();
            //获取输入流，读取文件
            InputStreamReader in = new InputStreamReader(uc.getInputStream());
            char[] buffer = new char[4*1024];
            int length;
            //读取文件
            while((length=in.read(buffer))!= -1){
                //写出
                out.write(buffer, 0, length);
            }
            out.flush();
            in.close();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // 生成文件夹
    public static String createDir() {
        // if win else linux
        String path = "";
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            path = WIN_DIR;
        } else {
            path = LINUX_DIR;
        }
        File folder = new File(path);
        if (!folder.exists() && !folder.isDirectory()) {
            folder.setWritable(true, false);
            try{
                folder.mkdirs();
            }catch (SecurityException e){
                e.printStackTrace();
            }
            System.out.println("mkdir");
        } else {
            System.out.println("文件夹已存在");
        }
        return path;
    }

    public static void printOut(){
        PrintStream out =   System.out;
        out.println("java -jar agent-attach-java.jar [-options <dd options>]");
        out.println("                                [-agent-jar <agent filepath>]");
        out.println("                                [-pid <pid>]");
        out.println("                                [-displayName <service name/displayName>]");
        out.println("                                [-h]");
        out.println("                                [-help]");
        out.println("[-options]:");
        out.println("   this is dd-java-agnet.jar env, example:");
        out.println("       dd.agent.port=9529,dd.agent.host=localhost,dd.service=serviceName,...");
        out.println("[-agent-jar]:");
        out.println("   default is: /usr/local/ddtrace/dd-java-agent.jar");
        out.println("[-pid]:");
        out.println("   service PID String");
        out.println("[-displayName]:");
        out.println("   service name");
        out.println("Note: -pid or -displayName must have a non empty !!!");
        out.println("");
        out.println("example command line:");
        out.println("java -jar agent-attach-java.jar -options 'dd.service=test,dd.tag=v1'\\");
        out.println(" -displayName tmall.jar \\");
        out.println(" -agent-jar /usr/local/ddtrace/dd-java-agent.jar");
    }
}
