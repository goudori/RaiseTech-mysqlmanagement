package com.school.mysql.management;

/**
 * Studentクラスは、データベースの値をMyBatisが自動設定する為のクラス
 */
public class Student {

  private String name, sex;

  private int id, age;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
