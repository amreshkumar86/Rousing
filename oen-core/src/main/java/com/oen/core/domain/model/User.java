package com.oen.core.domain.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.oen.core.domain.BaseModel;

@Entity
@Table(name = "user")
@JsonFilter("USER_FILTER")
public class User extends BaseModel {
	
	private static final long serialVersionUID = 790783626359984900L;
	
	private Customer customer;
	
	
	private String firstname;
	private String lastname;
	private String email;
	private String phone_number;
    private String username;
    private String password;
    private LocalDateTime registeredOn;
    private List<Role> authorities;
    private Boolean enabled;
    
    @OneToOne
	@JoinColumn(name="customer_id")
    public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@Column(name = "firstname", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Column(name = "lastname", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    @Column(name = "email", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Column(name = "phone_number", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	
	
	@Column(name = "username", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    
    @Column(name = "password", length = 100)
    @NotNull
    @Size(min = 4, max = 100)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    
    @Column(name = "registered_on")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	public LocalDateTime getRegisteredOn() {
		return registeredOn;
	}
	public void setRegisteredOn(LocalDateTime registeredOn) {
		this.registeredOn = registeredOn;
	}
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
          joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
          inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    public List<Role> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<Role> authorities) {
		this.authorities = authorities;
	}
	
	@Column(name = "enabled")
    @NotNull
    public Boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}