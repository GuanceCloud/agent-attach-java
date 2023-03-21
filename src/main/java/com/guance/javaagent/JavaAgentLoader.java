package com.guance.javaagent;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.guance.javaagent.Config.FILE_NAME;

public class JavaAgentLoader {
    static final Logger logger = LoggerFactory.getLogger(JavaAgentLoader.class);


    public static void loadAgent(Config config) {
        logger.info("dynamically loading javaagent");
        logger.info("config.options:"+ config.getOptions());
        logger.info("config.agentJar: "+config.getAgentJar());
        logger.info("config.pid: "+config.getPid());
        logger.info("config.displayName: "+config.getDisplayName());
        boolean idMode = false;
        boolean nameMode= false;
        if (config.getPid() != null && !config.getPid().equals("")){
            idMode = true;
        }
        if (config.getDisplayName()!= null && !config.getDisplayName().equals("")){
            nameMode =true;
        }
        if (!idMode && !nameMode){
            logger.warn("-pid or -displayName must have a non empty");
            return;
        }
        try {
            List<VirtualMachineDescriptor> list = VirtualMachine.list();
            for (int i = 0; i < list.size(); i++) {
                VirtualMachineDescriptor virtualMachineDescriptor = list.get(i);
                String pid = virtualMachineDescriptor.id();
		        logger.info(pid);
		    //    logger.info(virtualMachineDescriptor.displayName());
                VirtualMachine attach = null;
                if (idMode && config.getPid().equals(pid) ){
                     attach = VirtualMachine.attach(pid);
                }
                if (nameMode && virtualMachineDescriptor.displayName().equals(config.getDisplayName())){
                    attach = VirtualMachine.attach(pid);
                }
                if (attach == null){
                    continue;
                }
                if (config.getAgentJar() != null && !config.getAgentJar().equals("")){
                    attach.loadAgent(config.getAgentJar(), config.getOptions());
                }else {
                    attach.loadAgent(config.getDir()+"/"+ FILE_NAME, config.getOptions());
                }
                attach.detach();
                logger.info(String.format("attach agent into [%s]",virtualMachineDescriptor.displayName()));
                return;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
