package org.omg.demo.dds.shapes;

import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.core.policy.PolicyFactory;
import org.omg.dds.core.status.*;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.sub.*;
import org.omg.dds.topic.Topic;
import org.omg.dds.topic.TopicQos;
import org.omg.org.omg.dds.shapes.gen.ShapeType;

import java.util.Vector;

class ShapeTypeListener implements DataReaderListener<ShapeType> {
    @Override
    public void onRequestedDeadlineMissed(RequestedDeadlineMissedEvent<ShapeType> status) {
        System.out.println(">> onRequestedDeadlineMissed");
    }

    @Override
    public void onRequestedIncompatibleQos(RequestedIncompatibleQosEvent<ShapeType> status) {
        System.out.println(">> onRequestedIncompatibleQos");
    }

    @Override
    public void onSampleRejected(SampleRejectedEvent<ShapeType> status) {
        System.out.println(">> onSampleRejected");
    }

    @Override
    public void onLivelinessChanged(LivelinessChangedEvent<ShapeType> status) {
        System.out.println(">> onLivelinessChanged");
    }

    @Override
    public void onDataAvailable(DataAvailableEvent<ShapeType> evt) {
        Sample.Iterator<ShapeType> data = evt.getSource().read();
    }

    @Override
    public void onSubscriptionMatched(SubscriptionMatchedEvent<ShapeType> status) {
        System.out.println(">> onSubscriptionMatched");
    }

    @Override
    public void onSampleLost(SampleLostEvent<ShapeType> status) {
        System.out.println(">> onSampleLost");
    }
}

/**
 * This class provides a simple example of a DDS data reader using the new Java API.
 */
public class ShapesReader {
    public static void main(String args[]) throws Exception {

        if (args.length < 1) {
            System.out.println("USAGE:\n\tShapesWriter <topic>");
            System.out.println("\n\ttopic = [Circle, Square, Triangle]");
            System.exit(1);
        }

        final String shape =
                args[1].substring(0,1).toUpperCase() + args[1].substring(1,args[1].length()).toLowerCase();

        final ServiceEnvironment env =
                ServiceEnvironment.createInstance(ShapesWriter.class.getClassLoader());

        DomainParticipantFactory factory =
                DomainParticipantFactory.getInstance(env);
        DomainParticipant dp = factory.createParticipant();

        final PolicyFactory pf = env.getSPI().getPolicyFactory();
        TopicQos tqos = dp.getDefaultTopicQos();

        tqos.withPolicies(pf.Durability().withTransient(), pf.Reliability().withReliable());
        Topic<ShapeType> topic = dp.createTopic(shape, ShapeType.class);

        Subscriber sub = dp.createSubscriber();
        DataReaderQos drqos =
                sub.getDefaultDataReaderQos().withPolicies(
                        pf.Reliability().withBestEffort(),
                        pf.Durability().withTransient()
                );

        DataReader<ShapeType> dr =
                sub.createDataReader(topic, drqos, new ShapeTypeListener(), Status.allStatuses(env));

        Thread.currentThread().join();
    }
}
