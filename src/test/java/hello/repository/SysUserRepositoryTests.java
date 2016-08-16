package hello.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import hello.domain.SysUser;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserRepositoryTests {

	@Autowired
	private SysUserRepository sysUserRepo;

	@Test
	public void deleteAll() {
		this.sysUserRepo.deleteAll();
	}

	@Test
	public void save() {
		SysUser entity = new SysUser();
		entity.setSn("431520");
		entity.setPwd("431520");
		entity.setFullName("Lewis Lu");
		this.sysUserRepo.save(entity);
	}

	@Test
	public void findAll() {
		assertEquals(this.sysUserRepo.findAll().size(), 1);
	}

}
