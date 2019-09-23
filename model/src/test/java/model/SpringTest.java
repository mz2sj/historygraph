package model;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mz.bean.Category;
import com.mz.dao.CategoryMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml"})
public class SpringTest {
	@Autowired
	CategoryMapper mapper;
	@Test
	public void myTest() {
		List<Category> selectByExample = mapper.selectByExample(null);
		for (Category category : selectByExample) {
			System.out.println(category);
		}
	}
}
