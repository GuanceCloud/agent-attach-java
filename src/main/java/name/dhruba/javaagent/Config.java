package name.dhruba.javaagent;

public class Config {
    private String options;

    private String downloadAddress;

    private  String agentJar;

    public String getOptions() {
        return options;
    }

    public String getDownloadAddress() {
        return downloadAddress;
    }

    public String getAgentJar() {
        return agentJar;
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
                    case "options":
                        option = arg;
                    case "download":
                        downloadAddr = arg;
                    case "agent-jar":
                         agentJar = arg;
                }
            }
        }

        return new Config(option,downloadAddr,agentJar);
    }

}
