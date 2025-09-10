package usersService;

import jakarta.persistence.Entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;

@Entity
public class UserModel implements Serializable{ 

	
    private static final long serialVersionUID=1L;
	
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE,generator="my_seq")
	@SequenceGenerator(name="my_seq", sequenceName="my_seq", allocationSize=1, initialValue=3)
	
	private int id;
	
	@Column(nullable = false, unique=true)
	private String email;
	
	@Column(nullable= false)
	private String password;
	
	
	@Column(nullable = false, columnDefinition="VARCHAR(10) CHECK(role IN ('ADMIN','USER'))")
	private String role;
	
	public UserModel() {
	
	}
	public UserModel(String email, String password, String role) {
		super();
		
		this.email = email;
		this.password = password;
		this.role = role;
	}
	public UserModel(int id, String email, String password, String role) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}
