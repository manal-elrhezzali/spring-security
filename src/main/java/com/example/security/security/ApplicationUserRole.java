package com.example.security.security;

import static com.example.security.security.ApplicationUserPermission.*;

import com.google.common.collect.Sets;
import java.util.Set;

public enum ApplicationUserRole {
  STUDENT(Sets.newHashSet()),
  ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE)),
  ADMINTRAINEE(Sets.newHashSet(COURSE_READ, STUDENT_READ));

  private final Set<ApplicationUserPermission> permissions;

  ApplicationUserRole(
      Set<ApplicationUserPermission> permissions) {
    this.permissions = permissions;
  }

  public Set<ApplicationUserPermission> getPermissions() {
    return permissions;
  }
}
