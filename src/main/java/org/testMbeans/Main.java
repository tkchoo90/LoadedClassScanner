//package org.testMbeans;
//
//import javax.management.InstanceAlreadyExistsException;
//import javax.management.InstanceNotFoundException;
//import javax.management.MBeanException;
//import javax.management.MBeanRegistrationException;
//import javax.management.MBeanServer;
//import javax.management.MBeanServerConnection;
//import javax.management.MalformedObjectNameException;
//import javax.management.NotCompliantMBeanException;
//import javax.management.ObjectName;
//import javax.management.ReflectionException;
//import javax.management.loading.MLet;
//import javax.management.remote.JMXConnector;
//import javax.management.remote.JMXConnectorFactory;
//import javax.management.remote.JMXServiceURL;
//import java.io.IOException;
//import java.lang.management.ManagementFactory;
//import java.util.Properties;
//
//import com.sun.tools.attach.AttachNotSupportedException;
//import com.sun.tools.attach.VirtualMachine;
//
//public class Main {
//    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanException, IOException, AttachNotSupportedException, ReflectionException, InstanceNotFoundException {
//        System.out.println("Hello main");
//
//        // attach to target VM
//        VirtualMachine vm = VirtualMachine.attach("3941");
//
//        // start management agent
//        vm.startLocalManagementAgent();
//        String connectorAddress = vm.getAgentProperties().getProperty("com.sun.management.jmxremote.localConnectorAddress");
//        JMXServiceURL url = new JMXServiceURL(connectorAddress);
//        JMXConnector connector = JMXConnectorFactory.connect(url);
//        MBeanServerConnection connection = connector.getMBeanServerConnection();
//        connection.getDefaultDomain();
//        String domain = connection.getDefaultDomain();
//
//        String className = "org.testMbeans.ClassPrinter";
//        ObjectName name = new ObjectName(domain + ":name=" + className);
////        connection.getObjectInstance()
//
//        connection.createMBean(className, name, ObjectName.getInstance("java.lang:type=ClassLoading"));
//
//
////        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
////
////        // Create an instance of ClassPrinter
////        ClassPrinterMBean classPrinter = new ClassPrinter();
////
////        // Define the object name for our MBean
////        ObjectName objectName = new ObjectName("com.example:type=ClassPrinter");
////
////        // Register the MBean
////        mBeanServer.registerMBean(classPrinter, objectName);
//    }
//}
