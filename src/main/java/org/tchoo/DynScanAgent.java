package org.tchoo;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class DynScanAgent {
    private static final DynScanTransformer classLoadInfoTransformer;
    private static final ClassLoadingInfo classLoadingInfoBean;

    static {
        try {
            classLoadingInfoBean = createClassLoadingInfoMBean();
            classLoadInfoTransformer = new DynScanTransformer(classLoadingInfoBean);
        } catch (MalformedObjectNameException | NotCompliantMBeanException | InstanceAlreadyExistsException | MBeanRegistrationException e) {
            throw new RuntimeException(e);
        }
    }

    //To pass arguments to a Java agent, append them after the equals sign: java -javaagent:/path/to/agent.jar=argumentstring
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        System.out.println("Hello pre main!");

        /**
         *  In contrast to AgentMain, since premain is loaded during initial start, we can immediately add transformer and get all loaded classes
         *  along with a timestamp
         */

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
                }
            }, (long) delayInMins*60*1000);
        }


    }

    public static void agentmain(String agentArgs, Instrumentation instrumentation) {
        System.out.println("Hello agent main!");

        /**
         *  In contrast to preMain, since agent main is loaded while JVM is running, adding transformer will only apply to classes loaded
         *  after agent main has been attached. We will miss all the classes that has already been loaded.
         *
         *  To get information about these earlier loaded classes, we can look into the instrumentation.getAllLoadedClasses(). This gives us
         *  all currently loaded class, but unfortunately without timestamp.
         */
        printExistingClassLoadingInfo(instrumentation);
        instrumentation.addTransformer(classLoadInfoTransformer); // Classes loaded subsequently will have timestamps

        //        agentArgs = "1"; // tk todo
        // TODO: Parse agentArgs to obtain shutdown timer
        if(agentArgs != null) {
            int delayInMins = Integer.parseInt(agentArgs);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    instrumentation.removeTransformer(classLoadInfoTransformer);
                }
            }, (long) delayInMins*60*1000);
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

    private static void printExistingClassLoadingInfo(Instrumentation instrumentation) {

        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(10000); // Adding a sleep here, in case we need to test with premain
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Set<String> paths = new HashSet<>();
            Set<Class> classes = Arrays.stream(instrumentation.getAllLoadedClasses())
                    .collect(Collectors.toSet());
            if (classes.isEmpty()) {
                System.out.println("No classes loaded in JVM");
            }
            Set<String> packages = new HashSet<>();
            for (Class aClass : classes) {
                try {
                    String pkg = aClass.getPackage().getSpecificationTitle() + ":" +
                            ((aClass.getPackage().getSpecificationVendor() == null) ? aClass.getPackage().getImplementationVendor() : aClass.getPackage()
                                    .getSpecificationVendor()) + ":" + aClass.getPackage().getSpecificationVersion();
                    paths.add("className: " + aClass + " File: " + aClass.getProtectionDomain().getCodeSource().getLocation().getFile());
                    packages.add(pkg);
                    LoadedClassDetail loadedClass = new LoadedClassDetail("No-info", aClass.toString(), aClass.getProtectionDomain().getCodeSource().getLocation().getFile());
                    classLoadingInfoBean.addClassToBeanList(loadedClass.toString());
                } catch (final SecurityException | NullPointerException e) {
                    //NOOP
                }
            }
            System.out.println("paths:");
            paths.stream().forEach(System.out::println);
        });
        t1.start();

    }


}
