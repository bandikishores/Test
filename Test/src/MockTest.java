import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import org.junit.Assert;
import org.junit.Test;

class AnotherClass {

	public int anotherFun() {
		return 10;
		
	}
	
}

public class MockTest {
/*
    @InjectMocks
    private static TestClass testClass = new TestClass();*/
    
   private AnotherClass anotherMockClass = spy(new AnotherClass());
	
	@Test
	public void testCase()
	{
		// when(testClass.fun1()).thenReturn(new Integer(3));
		doReturn(200).when(anotherMockClass).anotherFun();
		int result = 0;//mockObj.fun();
		Assert.assertEquals(result, 200);
	}
	
}
