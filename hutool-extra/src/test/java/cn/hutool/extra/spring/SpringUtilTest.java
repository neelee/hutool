package cn.hutool.extra.spring;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.map.MapUtil;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {SpringUtil.class, SpringUtilTest.Demo2.class})
//@Import(cn.hutool.extra.spring.SpringUtil.class)
public class SpringUtilTest {

	/**
	 * 注册bean
	 *
	 * 为了保证顺序采用顺序abcdef...形式命名方法
	 */
	@Test
	public void a() {
		Demo2 registerBean = new Demo2();
		registerBean.id = 123;
		registerBean.name = "222";
		SpringUtil.registerBean("registerBean", registerBean);
	}

	/**
	 * 验证注册的bean
	 */
	@Test
	public void b() {
		Demo2 registerBean = SpringUtil.getBean("registerBean");
		Assert.assertEquals(123, registerBean.id);
		Assert.assertEquals("222", registerBean.name);
	}

	@Test
	public void getBeanTest(){
		final Demo2 testDemo = SpringUtil.getBean("testDemo");
		Assert.assertEquals(12345, testDemo.id);
		Assert.assertEquals("test", testDemo.name);
	}

	@Test
	public void getBeanWithTypeReferenceTest() {
		Map<String, Object> mapBean = SpringUtil.getBean(new TypeReference<Map<String, Object>>() {});
		Assert.assertNotNull(mapBean);
		Assert.assertEquals("value1", mapBean.get("key1"));
		Assert.assertEquals("value2", mapBean.get("key2"));
	}

	@Data
	public static class Demo2{
		private long id;
		private String name;

		@Bean(name="testDemo")
		public Demo2 generateDemo() {
			Demo2 demo = new Demo2();
			demo.id = 12345;
			demo.name= "test";
			return demo;
		}

		@Bean(name="mapDemo")
		public Map<String, Object> generateMap() {
			HashMap<String, Object> map = MapUtil.newHashMap();
			map.put("key1", "value1");
			map.put("key2", "value2");
			return map;
		}
	}
}
