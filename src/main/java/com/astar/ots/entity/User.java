package com.astar.ots.entity;

import com.astar.ots.util.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User implements Serializable {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "username", unique = true, nullable = false)
  private String username;

  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @ElementCollection(fetch = FetchType.EAGER)
  List<Role> roles;
}
