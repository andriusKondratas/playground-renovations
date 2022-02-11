package com.swedbank.playground;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class Kid {
  private String name;
  private int age;
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Kid kid = (Kid)o;
    return getAge() == kid.getAge() &&
      Objects.equals(getName(), kid.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getAge());
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("name", name)
      .add("age", age)
      .toString();
  }
}
