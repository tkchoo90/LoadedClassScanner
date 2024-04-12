package org.tchoo;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.util.Timer;
import java.util.TimerTask;

public class DynScanAgent {
    private static final DynScanTransformer classLoadInfoTransformer;

    static {
        try {
            classLoadInfoTransformer = new DynScanTransformer(createClassLoadingInfoMBean());
        } catch (MalformedObjectNameException | NotCompliantMBeanException | InstanceAlreadyExistsException | MBeanRegistrationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void premain(String agentArgs, Instrumentation instrumentation) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {
        System.out.println("Hello World! Java Agent");
        instrumentation.addTransformer(classLoadInfoTransformer);

//        agentArgs = "1"; // tk todo
        // TODO: Parse agentArgs to obtain shutdown timer
        if(agentArgs != null) {
            int delayInMins = Integer.parseInt(agentArgs);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    instrumentation.removeTransformer(classLoadInfoTransformer);
                    System.out.println("DYNSCAN LOG: Instrumentation transformer stopped...");
                }
            }, (long) delayInMins *60*1000);
        }
    }

    private static ClassLoadingInfo createClassLoadingInfoMBean() throws NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, MalformedObjectNameException {
        // Create MBean instance
        ClassLoadingInfo classLoadingInfo = new ClassLoadingInfo();
        // Register MBean
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("com.tchoo.dynscan:name=classLoadingInfo");
        server.registerMBean(classLoadingInfo, objectName);
        return classLoadingInfo;
    }


}
