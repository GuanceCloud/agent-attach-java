package name.dhruba.javaagent;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyMainClass {

    static final Logger logger = LoggerFactory.getLogger(MyMainClass.class);

    /**
     * Main method.
     *
     * @param args
     */
    public static void main(String[] args) {
        Config config = Config.parse(args);
        // todo load agent ddtrace

    //    MyJavaAgent.initialize(config.getAgentJar(),config.getOptions());
        JavaAgentLoader.loadAgent(config.getAgentJar(),config.getOptions());
        logger.info("main method invoked with args: {}", Arrays.asList(args));
     //   logger.info("userName: {}", new MyUser().getName());
    }

}