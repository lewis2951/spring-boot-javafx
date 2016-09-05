package hello.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hello.domain.SysUser;

public interface SysUserRepository extends JpaRepository<SysUser, Long> {

	public List<SysUser> findBySn(String sn);

}
