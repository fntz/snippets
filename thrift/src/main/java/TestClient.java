import com.example.thriftify.TestService;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;


public class TestClient {

  private static final String host = "localhost";
  private static final int port = 9000;

  public static void main(String[] args) throws Exception {
    TTransport transport = new TFramedTransport(new TSocket(host, port));
    try {
      transport = new TFramedTransport(new TSocket(host, port));
      TProtocol protocol = new TBinaryProtocol(transport);

      TestService.Client client = new TestService.Client(protocol);
      transport.open();
      String result = client.getTestData();
      System.out.println("Result = " + result);

      transport.close();
    } catch (Exception ex) {
      System.out.println("-----> " + ex.getMessage());
    } finally {
      transport.close();
    }
  }



}