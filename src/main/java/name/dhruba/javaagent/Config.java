package name.dhruba.javaagent;

import java.io.PrintStream;

public class Config {
    private String options;

    private String downloadAddress;

    private  String agentJar;

    public String getOptions() {
        return this.options;
    }

    public String getDownloadAddress() {
        return  this.downloadAddress;
    }

    public String getAgentJar() {
        return  this.agentJar;
    }

    private Config(String options,String downloadAddress,String agentJar){
        this.options = options;
        this.downloadAddress = downloadAddress;
        this.agentJar = agentJar;
    }

    static Config parse(String... args){
        String option = "";
        String downloadAddr= "";
        String currentArg = "";
        String agentJar= "";
        for(String arg : args){
            if (arg.startsWith("-")){
                currentArg =arg;
            }else {
                switch (currentArg){
                    case "-options":
                        option = arg;
                        break;
                    case "-download":
                        downloadAddr = arg;
                        break;
                    case "-agent-jar":
                         agentJar = arg;
                        break;
                    case "-h":
                    case "-help":
                        printOut();
                        break;

                }
            }
        }
        return new Config(option,downloadAddr,agentJar);
    }
    public static void printOut(){
        PrintStream out =   System.out;
        out.println("    java -jar agent-attach-java.jar [-options <dd options>]");
        out.println("                                    [-agent-jar <agent filepath>]");
        out.println("                                    [-h]");
        out.println("                                    [-help]");
        out.println(" [-options]:");
        out.println(" this is dd-java-agnet.jar env, example:");
        out.println("    dd.agent.port=9529,dd.agent.host=localhost,dd.service=serviceName");
        out.println(" [-agent-jar]:");
    }
}
