package ma.cirestechnologies.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import ma.cirestechnologies.model.Role;


public class UserDTO {

  @ApiModelProperty(position = 0)
  private String firstName ;
  @ApiModelProperty(position = 1)
  private String lastName;
  @ApiModelProperty(position = 2)
  private Date birthDate;
  @ApiModelProperty(position = 3)
  private String city;
  @ApiModelProperty(position = 4)
  private String country;
  @ApiModelProperty(position = 5)
  private String avatar;
  @ApiModelProperty(position = 6)
  private String company;
  @ApiModelProperty(position = 7)
  private String jobPosition;
  @ApiModelProperty(position = 8)
  private String mobile;
  @ApiModelProperty(position = 9)
  private String username;
  @ApiModelProperty(position = 10)
  private String email;
  @ApiModelProperty(position = 11)
  private String password;
  @ApiModelProperty(position = 12)
  private Role role;


  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getJobPosition() {
    return jobPosition;
  }

  public void setJobPosition(String jobPosition) {
    this.jobPosition = jobPosition;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
