import org.apache.thrift.TException;

import com.bandi.thrift.client.ThriftClient;
import com.bandi.thrift.data.ComplexStruct;

public class TestSimpleThrift {

	public static void main(String[] args) throws TException {
		ThriftClient client = new ThriftClient();
		ComplexStruct complexStruct = new ComplexStruct();
		complexStruct.setSomeString("123");
		complexStruct.setOptionalString(null);
		client.test(complexStruct);
	}

}
