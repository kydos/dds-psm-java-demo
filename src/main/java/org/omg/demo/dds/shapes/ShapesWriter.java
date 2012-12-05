package org.omg.demo.dds.shapes;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.PolicyFactory;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.DataWriterQos;
import org.omg.dds.pub.Publisher;
import org.omg.dds.pub.PublisherQos;
import org.omg.dds.topic.Topic;
import org.omg.org.omg.dds.shapes.gen.ShapeType;

/**
 * This class provides a simple example of a DDS data writer using the new Java API.
 */
public class ShapesWriter {
    public static void main(String args[]) throws Exception {

        if (args.length < 4) {
            System.out.println("USAGE:\n\tShapesWriter <topic> <circle> <samples> <period ms>");
            System.out.println("\n\ttopic = [Circle, Square, Triangle]");
            System.out.println("\tcolor = [Red, Blue, Green, Magenta, Cyan]");
            System.exit(1);
        }

        final String shape =
                args[1].substring(0,1).toUpperCase() + args[1].substring(1,args[1].length()).toLowerCase();
        final String color = args[1].toUpperCase();

        final int samples = Integer.parseInt(args[2]);

        final int period = Integer.parseInt(args[3]);

        final int bound = 100;

        final ServiceEnvironment env =
                ServiceEnvironment.createInstance(ShapesWriter.class.getClassLoader());

        final PolicyFactory pf = env.getSPI().getPolicyFactory();

        DomainParticipantFactory factory =
                DomainParticipantFactory.getInstance(env);

        DomainParticipant dp = factory.createParticipant();


        Topic<ShapeType> topic = dp.createTopic(shape, ShapeType.class);

        Publisher pub =dp.createPublisher();


        DataWriterQos dwqos =
                pub.getDefaultDataWriterQos().withPolicies(
                        pf.Reliability().withBestEffort(),
                        pf.Durability().withTransient());

        DataWriter<ShapeType> dw = pub.createDataWriter(topic);

        final ShapeType sample = new ShapeType(color, 0, 0, 0);

        for (int i = 0; i < samples; ++i) {
            sample
                .setX(((int)Math.random()%bound))
                .setY(((int)Math.random()%bound))
                .setShapesize(((int)Math.random()%bound));

            dw.write(sample);
            Thread.sleep(period);
        }

    }
}
