package hello.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_SYS_USER")
public class SysUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "SN")
	private String sn;

	@Column(name = "password")
	private String pwd;

	@Column(name = "FULL_NAME")
	private String fullName;

	public SysUser() {
		super();
	}

	public SysUser(String sn, String pwd, String fullName) {
		super();
		this.sn = sn;
		this.pwd = pwd;
		this.fullName = fullName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return String.format("SysUser [id=%s, sn=%s, pwd=%s, fullName=%s]", id, sn, pwd, fullName);
	}

}
