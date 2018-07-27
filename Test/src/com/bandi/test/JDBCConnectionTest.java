package com.bandi.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.codahale.metrics.Timer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JDBCConnectionTest {

	private static final int concurrent_queries = 4;
	static ExecutorService threadPool = Executors.newFixedThreadPool(concurrent_queries);
	static List<Timer> timerList = new ArrayList<>();
	static List<String> queries = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("select count(*) from (select b.id as id,b.zone_id as zone,b.de_id as deId,b.order_count as order_count,b.sla as sla,b.merge_version as version, t.restaurant_customer_distance as last_mile, t.order_id as order_id,t.customer_lat_lng as customer_lat_lng, t.item_count as item_count,t.society_id as society_id,t.with_de as with_de,t.restaurant_id as restaurant_id, t.order_tags as order_tags, t.ordered_time as ordered_time,t.id as trip_id,t.sla as trip_sla,t.bill as bill, r.lat_long as rest_lat_long, t.last_mile_time_predicted as last_mile_time_predicted, t.area_id as area_id, t.city_id as city_id, di.de_type as de_type from batch b inner join trips t on t.batch_id = b.id inner join restaurant r on r.id = t.restaurant_id left join de_info as di on b.de_id = di.de_id where b.open = 1 and b.archived = 0 and t.archived = 0 and r.batching_enabled = 1 and t.is_verified >= 0  and b.zone_id in ( 1,2,3,4,5,6,7,8,9,10,11,12,13,31,32,33,34,48,49,51,68,69,70,84,97,111,135,229,235,242,243,252,260,269,306,307,308,323,324,339,340,341,345,357,358,359,360,393,400,498 ) and b.id = t.batch_id and t.city_id = 1 and b.cancelled = 0 and t.cancelled = 0 and t.restaurant_id = r.id and t.pickedup_time is null) tempTable");
			add("select count(*) from (select trips0_.de_id as col_0_0_, trips0_.order_id as col_1_0_ from trips trips0_ where (trips0_.assigned_time is not null) and (trips0_.confirmed_time is null) and trips0_.cancelled=0 and trips0_.ordered_time>now() and (trips0_.order_ack_time is null) and trips0_.assigned_time<'2018-07-17 14:26:59.966' and trips0_.city_id=1) temp1");
			add("select count(*) from (select z.id, count from (SELECT count(id) as count,zone_id as id from trips WHERE cancelled = 0 and archived =0 and ordered_time > now() and assigned_time is null group by zone_id) as e right join zone as z on z.id = e.id) temp2");
			add("select count(*) from (select z.id, e.orders from (select zone_id as id, GROUP_CONCAT(order_id) as orders from trips where cancelled = 0 and archived =0 and assigned_time is not null and confirmed_time is null and ordered_time < now() + interval 330 minute - interval 20 minute and ordered_time > now() group by zone_id ) as e right join zone as z on z.id = e.id) temp3");
			add("select count(*) from (select deliverybo0_.id as col_0_0_ from delivery_boys deliverybo0_ cross join de_rejected_orders derejected1_ cross join de_info deinfo2_ cross join zone zone3_ cross join city citysql4_ where deliverybo0_.id=derejected1_.de_id and citysql4_.id=zone3_.city_id and zone3_.id=deliverybo0_.zone_id and deinfo2_.de_id=deliverybo0_.id and citysql4_.id=8 and deinfo2_.de_type=2 and derejected1_.rejected_time>'2018-07-16 08:00:54.124' and (derejected1_.order_ack_time is not null) and deliverybo0_.enabled=1 and deliverybo0_.employment_status=1 and deliverybo0_.force_logout=0 and (deliverybo0_.id not in (5692 , 5189 , 5698 , 5362 , 5363 , 5364 , 5695 , 5696 , 5694 , 7602 , 7601 , 7599 , 5693 , 5694 , 216 , 99828)) group by derejected1_.de_id having count(*)>1) temp5");
			add("select count(*) from (select b.order_count as order_count,b.sla as sla,b.batch_version as batch_version,b.id as batch_id,b.open as open,b.cancelled as cancelled,t.id as trip_id, t.order_id as order_id, t.sla as trip_sla, r.lat_long as from_lat_long,r.jit_enabled, t.customer_lat_lng as to_lat_long, t.zone_id as zone, t.with_de as with_de, t.pay as pay, t.collect as collect, t.ordered_time as ordered_time, t.order_tags as order_tags,t.last_mile_time_predicted as last_mile_time_predicted, t.restaurant_customer_distance as last_mile, t.restaurant_id as restaurant_id, t.area_id as area_id, t.customer_zone as customer_zone, t.city_id as city_id  from trips t USE INDEX (ordered_time_idx) inner join batch b on t.batch_id = b.id inner join restaurant r on r.id = t.restaurant_id where t.ordered_time >=  NOW() - INTERVAL 210 MINUTE and b.archived = 0 and t.archived = 0 and b.cancelled = 0 and t.cancelled = 0 and b.de_id is null and t.delivered_time is null and t.city_id = 1  limit 2000) temp4");
			add("select count(*) from (select this_.id as id1_101_0_, this_.cancelled as cancelle2_101_0_, this_.date as date3_101_0_, this_.order_id as order_id4_101_0_, this_.processed as processe5_101_0_, this_.restaurant_id as restaura6_101_0_, this_.sla as sla7_101_0_, this_.slot_end_time as slot_end8_101_0_, this_.slot_start_time as slot_sta9_101_0_ from pre_order this_ where this_.processed=0 and this_.cancelled=0 and this_.slot_start_time<1531831500000) afjl");
			add("select count(*) from (select db.id as de_id, dzp.zone_id as zone, db.bicycle as bicycle, db.status as status, db.batch_id as batch_id, di.de_type as de_type from delivery_boys as db inner join de_zone_map as dzp on db.id=dzp.de_id left join de_info as di on db.id=di.de_id inner join zone as z on dzp.zone_id = z.id where db.employment_status=1 and db.enabled=1 and z.city_id= 5 and ((db.status='FR') or (db.status='BY' and db.id in (select b.de_id from batch as b where b.archived=0 and b.cancelled=0 and b.open=0 and b.id=db.batch_id) and db.id not in (select pab.de_id from pre_assigned_batches as pab where pab.archived=0)))) asfs");
			add("select count(*) from (SELECT area_id, restaurant_id, restaurant_customer_distance, ordered_time, order_id, if(placed_time is null,(TIME_TO_SEC(timediff(pickedup_time, arrived_time))/60),IF(TIME_TO_SEC(timediff(pickedup_time, arrived_time)) <30 ,null,abs(TIME_TO_SEC(timediff(pickedup_time, placed_time))/60))) as prep_time, if(placed_time is not null and timediff(placed_time, ordered_time)>0 ,LEAST(3,TIME_TO_SEC(timediff(placed_time, ordered_time))/60),null) as placement_delay, item_count, TIME_TO_SEC(timediff(assigned_time, received_time))/60 as assignment_delay, TIME_TO_SEC(timediff(arrived_time, assigned_time))/60 as first_mile_time, TIME_TO_SEC(timediff(delivered_time, pickedup_time))/60 as last_mile_time, placed_time is null as with_de, if(restaurant_customer_distance >3, TIME_TO_SEC(timediff(delivered_time, pickedup_time))/60/restaurant_customer_distance, null) as mins_per_km FROM `trips_monthly` where ordered_time >= DATE_SUB(NOW(), INTERVAL 21 DAY )) temp6");
			add("select count(*) from (select d.id as id, d.mobile as mobile, d.name as name, d.status as status, d.enabled as enabled, d.last_seen_lat as last_seen_lat, d.last_seen_lng as last_seen_lng, d.last_seen_at as last_seen_at, di.de_type as de_type from delivery_boys d , de_info di WHERE d.id = di.de_id and d.employment_status=1) temp234");
		}
	};

	public static void main(String[] args) {

		AtomicInteger atomicInteger = new AtomicInteger(-1);
		DropwizardMetricsTest.startReporter(5);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			log.error("Excpetion occured ", e);
			return;
		}

		for (int i = 0; i < queries.size(); i++) {
			timerList.add(DropwizardMetricsTest.getMetrics().timer("Query-Number-" + i));
			threadPool.submit(() -> {
				for (;;) {
					int queryNumber = atomicInteger.incrementAndGet();
					fireQueryAndMonitor(queryNumber % queries.size());
				}
			});
		}
	}

	private static void fireQueryAndMonitor(Integer queryNumber) {
		long startTime = System.currentTimeMillis();
		System.out.println("Query Executing");

		try (Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/swiggy",
				"root", "root")) {
			PreparedStatement preparedStatement = connection.prepareStatement(queries.get(queryNumber));
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				writeResultSet(resultSet);
			}
		} catch (SQLException e) {
			log.error("Excpetion occured ", e);
			return;
		}
		long endTime = System.currentTimeMillis();

		timerList.get(queryNumber).update((endTime - startTime), TimeUnit.MILLISECONDS);
	}

	private static void writeResultSet(ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {
			// System.out.println("Count: " + resultSet.getLong(1));
		}
	}

}
