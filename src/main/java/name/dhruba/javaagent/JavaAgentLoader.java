package name.dhruba.javaagent;

import java.util.List;
import com.sun.tools.attach.VirtualMachineDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.tools.attach.VirtualMachine;

public class JavaAgentLoader {
    static final Logger logger = LoggerFactory.getLogger(JavaAgentLoader.class);

    private static final String jarFilePath = "/usr/local/ddtrace/dd-java-agent.jar";

    public static void loadAgent(String agentJar ,String options) {
        logger.info("dynamically loading javaagent");
        logger.info("------------------options-------------"+options);
        logger.info("------------------agentJar-------------"+agentJar);
        try {
            List<VirtualMachineDescriptor> list = VirtualMachine.list();
            for (int i = 0; i < list.size(); i++) {
                VirtualMachineDescriptor virtualMachineDescriptor = list.get(i);
                String version = virtualMachineDescriptor.id();
                VirtualMachine attach = VirtualMachine.attach(version);
                if (agentJar != null && !agentJar.equals("")){
                    attach.loadAgent(agentJar, options);
                }else {
                    attach.loadAgent(jarFilePath, options);
                }
                attach.detach();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}