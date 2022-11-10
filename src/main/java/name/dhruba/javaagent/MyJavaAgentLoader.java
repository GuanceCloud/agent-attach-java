package name.dhruba.javaagent;

import java.lang.management.ManagementFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.tools.attach.VirtualMachine;

public class MyJavaAgentLoader {

    static final Logger logger = LoggerFactory.getLogger(MyJavaAgentLoader.class);

    private static final String jarFilePath = "/home/dhruba/.m2/repository/"
            + "javaagent-examples/javaagent-examples/1.0/"
            + "javaagent-examples-1.0-jar-with-dependencies.jar";

    public static void loadAgent() {
        logger.info("dynamically loading javaagent");
        String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
        int p = nameOfRunningVM.indexOf('@');
        String pid = nameOfRunningVM.substring(0, p);

        try {
            VirtualMachine vm = VirtualMachine.attach(pid);
            vm.loadAgent(jarFilePath, "");
            vm.detach();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}