package com.bms.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class MapTest {

	@Test
	public void test() {
		Map<Integer, String> map=new HashMap<Integer,String>();
		for(int i=0;i<10;i++){
			map.put(i, "第"+i+"条数据");
		}
		System.out.println(map.get(9));
	}

}
