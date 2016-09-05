package hello.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import hello.domain.SysUser;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SysUserRepositoryTests {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private SysUserRepository sysUserRepo;

	@Test
	public void save() {
		SysUser entity = new SysUser("431520", "431520", "Lewis Lu");
		entityManager.persist(entity);

		List<SysUser> findBySn = sysUserRepo.findBySn(entity.getSn());

		assertThat(findBySn).extracting(SysUser::getSn).containsOnly(entity.getSn());
	}

}
