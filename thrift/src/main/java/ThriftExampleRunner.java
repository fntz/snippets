import com.example.thriftify.TestService;
import org.apache.thrift.TException;
import org.apache.thrift.server.AbstractNonblockingServer;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;

import java.util.UUID;

class MyTestServiceImpl implements TestService.Iface {

  public String getTestData() throws TException {
    return "{'result': " + UUID.randomUUID().toString() + "}";
  }
}


public class ThriftExampleRunner {

  private final static int port = 9000;

  private static MyTestServiceImpl service;
  private static TestService.Processor processor;

  public static void main(String args[]) throws Exception {
    System.out.println("Server run on " + port);
    service = new MyTestServiceImpl();
    processor = new TestService.Processor<>(service);

    TNonblockingServerTransport transport = new TNonblockingServerSocket(port);

    AbstractNonblockingServer.AbstractNonblockingServerArgs args1 =
        new TNonblockingServer.Args(transport).processor(processor);

    TServer server = new TNonblockingServer(args1);
    server.serve();
  }

}
