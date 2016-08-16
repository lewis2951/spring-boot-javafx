package hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hello.domain.SysUser;

public interface SysUserRepository extends JpaRepository<SysUser, Long> {

}
