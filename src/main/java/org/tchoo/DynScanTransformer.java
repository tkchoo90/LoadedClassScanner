package org.tchoo;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DynScanTransformer implements ClassFileTransformer {

    ClassLoadingInfo classLoadingInfo;

    public DynScanTransformer(ClassLoadingInfo classLoadingInfo) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {
        this.classLoadingInfo = classLoadingInfo;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {


        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

//        classLoadingInfo.addClassToBeanList(className);
//        classLoadingInfo.addClassLoadingInfoToBeanList(className, protectionDomain.getCodeSource().getLocation().toString(), dateFormat.format(new Date()));
        LoadedClassDetail loadedClass = new LoadedClassDetail(dateFormat.format(new Date()), className, protectionDomain.getCodeSource().getLocation().toString());
        classLoadingInfo.addClassToBeanList(loadedClass.toString());
//        /******* UNCOMMENT TO SET PRINTSTREAM TO FILE, FOR PRINTING LOGS OF WEBLOGIC SERVERS *******/
//        try {
//            File file = new File("/home/opc/Dynscan.txt");
//            FileOutputStream fos = new FileOutputStream(file);
//            PrintStream ps = new PrintStream(fos);                  // Create new print stream for file.
//            System.setOut(ps);                                      // Set file print stream.
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println(String.format(
//                "DYNSCAN LOG [%1$s]=> Classname: %2$s, path: [%3$s]",
//                dateFormat.format(new Date()),
//                className, // Note: in some cases, className may be null. These null are likely to be Anonymous classes, since they have no name
//                protectionDomain.getCodeSource().getLocation()
//                )
//        );
        return null;
    }




}
