package name.dhruba.javaagent;

import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Properties;

import com.sun.tools.attach.VirtualMachineDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.tools.attach.VirtualMachine;

public class JavaAgentLoader {
    static final Logger logger = LoggerFactory.getLogger(JavaAgentLoader.class);

    private static final String jarFilePath = "/usr/local/ddtrace/dd-java-agent.jar";

    public static void loadAgent(String agentJar ,String options) {
        logger.info("dynamically loading javaagent");
        String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
        int p = nameOfRunningVM.indexOf('@');
        String pid = nameOfRunningVM.substring(0, p);
        System.out.println("------------------into-------------");
        System.out.println("------------------p-------------"+p);
        System.out.println("------------------pid-------------"+pid);
        System.out.println("------------------args-------------"+options);

        try {
            List<VirtualMachineDescriptor> list = VirtualMachine.list();
            for (int i = 0; i < list.size(); i++) {
                VirtualMachineDescriptor virtualMachineDescriptor = list.get(i);
                String version = virtualMachineDescriptor.id();
                System.out.println(version);
                System.out.println(virtualMachineDescriptor.id());
                System.out.println(virtualMachineDescriptor.displayName());
                System.out.println(virtualMachineDescriptor.toString());

                VirtualMachine attach = VirtualMachine.attach(version);
                if (agentJar != null && !agentJar.equals("")){
                    attach.loadAgent(jarFilePath, options);
                }else {
                    attach.loadAgent(agentJar, options);
                }


                Properties properties = attach.getAgentProperties();
                Properties sysproperties = attach.getSystemProperties();
                System.out.println( properties.toString());
                System.out.println( sysproperties.toString());
                System.out.println("DdTrace attach agent to displayName is:"+virtualMachineDescriptor.displayName());

                attach.detach();
            }

            // -------------
//            VirtualMachine vm = VirtualMachine.attach(pid);
//            vm.loadAgent(jarFilePath, "Ddd.env:test,Ddd.service.name:demo001,Ddd.agent.port:9529");// 参数
//            vm.detach();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}