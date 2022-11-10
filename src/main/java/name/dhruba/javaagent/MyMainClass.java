package name.dhruba.javaagent;

import java.util.Arrays;

import name.dhruba.user.MyUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyMainClass {

    static final Logger logger = LoggerFactory.getLogger(MyMainClass.class);

    static {
        MyJavaAgent.initialize();
    }

    /**
     * Main method.
     * 
     * @param args
     */
    public static void main(String[] args) {
        logger.info("main method invoked with args: {}", Arrays.asList(args));
        logger.info("userName: {}", new MyUser().getName());
    }

}