package com.guance.javaagent;

import java.io.PrintStream;

public class Config {
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
                        download(arg);
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

    private static void download(String addr) {
        // download to /usr/local/ddtrace/dd-java-agent.jar

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
