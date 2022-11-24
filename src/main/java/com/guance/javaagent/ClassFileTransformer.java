package com.guance.javaagent;

import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassFileTransformer implements java.lang.instrument.ClassFileTransformer, Opcodes {

    static final Logger logger = LoggerFactory.getLogger(ClassFileTransformer.class);

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException {
        logger.info("class file transformer invoked for className: {}", className);

        return classfileBuffer;
    }

}