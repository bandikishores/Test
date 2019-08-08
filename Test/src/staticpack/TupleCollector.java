package staticpack;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import scala.Tuple2;

public class TupleCollector {
	public static <K, V, T extends Tuple2<K, V>> Collector<T, ?, Map<K, V>> toMap() {
		return Collectors.toMap(T::_1, T::_2);
	}
}
