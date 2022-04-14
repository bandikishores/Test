import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/*-
 * Output of each GC
 * 
 * 
Print current default GC configured in the system
	java -XX:+PrintCommandLineFlags -version
	
	
-XX:+UseG1GC
G1 Young Generation
java.lang:type=GarbageCollector,name=G1 Young Generation
G1 Old Generation
java.lang:type=GarbageCollector,name=G1 Old Generation



-XX:+UseParallelGC
PS MarkSweep
java.lang:type=GarbageCollector,name=PS MarkSweep
PS Scavenge
java.lang:type=GarbageCollector,name=PS Scavenge



-XX:+UseConcMarkSweepGC
ParNew
java.lang:type=GarbageCollector,name=ParNew
ConcurrentMarkSweep
java.lang:type=GarbageCollector,name=ConcurrentMarkSweep



-XX:+UseSerialGC
Copy
java.lang:type=GarbageCollector,name=Copy
MarkSweepCompact
java.lang:type=GarbageCollector,name=MarkSweepCompact


 * 
 * 
 * @author kishore
 *
 */
public class PrintJMX {
	public static void main(String[] args) throws Exception {
		String rmiHostname = "localhost";
		String rmiPort = "8989";
		String defaultUrl = "service:jmx:rmi:///jndi/rmi://" + rmiHostname + ":" + rmiPort + "/jmxrmi";
		JMXServiceURL jmxServiceURL = new JMXServiceURL(defaultUrl);

		JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxServiceURL);
		MBeanServerConnection mbsc = jmxConnector.getMBeanServerConnection();

		ObjectName gcName = new ObjectName(ManagementFactory.GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE + ",*");

		for (ObjectName name : mbsc.queryNames(gcName, null)) {
			GarbageCollectorMXBean gc = ManagementFactory.newPlatformMXBeanProxy(mbsc, name.getCanonicalName(),
					GarbageCollectorMXBean.class);

			System.out.println(gc.getName());
			System.out.println(gc.getObjectName());
		}

	}
}