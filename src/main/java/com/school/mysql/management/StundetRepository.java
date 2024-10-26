package com.school.mysql.management;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * StrundetRepositoryは、MySQLのデータベースを操作するインターフェース
 * <p>
 * Mapper　→MyBatisが、管理するインターフェース
 * <p>
 * #{値} →MyBatis専用のSQLの書き方。
 * <p>
 * mysqlmanagement →MySQLのデータベースの名前
 */
@Mapper
public interface StundetRepository {

  /**
   * mysqlmanagementから名前を取得する
   *
   * @param name
   * @return
   */
  @Select("SELECT * FROM mysqlmanagement WHERE name = #{name}")
  Student searchByName(@Param("name") String name); // name→#{name}を指す。

  /**
   * mysqlmanagementのテーブルにid, name, age, sexの値を追加する
   *
   * @param name
   * @param sex
   * @param id
   * @param age
   */
  @Insert("INSERT INTO mysqlmanagement (id, name, age, sex) VALUES (#{id}, #{name}, #{age}, #{sex})")
  void registerStudent(String name, String sex, int id, int age);

  /**
   * mysqlmanagementのテーブルにid, name, age, sexの値を更新する
   *
   * @param name
   * @param age
   * @param sex
   * @param id
   */
  @Update("UPDATE mysqlmanagement SET name=#{name}, age=#{age}, sex=#{sex} WHERE id = #{id}")
  void updateStudentInfo(@Param("name") String name, @Param("age") int age,
      @Param("sex") String sex, @Param("id") int id);


  /**
   * mysqlmanagementテーブルのidの値を取得する
   *
   * @param id
   * @return
   */
  @Select("SELECT * FROM mysqlmanagement WHERE id = #{id}")
  Student searchById(@Param("id") int id);

  /**
   * mysqlmanagementのテーブルの値を全て取得する
   *
   * @return
   */
  @Select("SELECT * FROM mysqlmanagement")
  List<Student> getAllStudents();

  /**
   * mysqlmanagementの指定したidで削除する
   *
   * @param id
   */
  @Delete("DELETE FROM mysqlmanagement WHERE id = #{id}")
  void deleteStudentById(@Param("id") int id);
}
