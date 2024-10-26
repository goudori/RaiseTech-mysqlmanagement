package com.school.mysql.management;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学生情報を管理するためのSpring Bootアプリケーション。 学生の名前と年齢をHashMapで管理し、GETとPOSTリクエストで情報を取得・更新します。 「curl
 * http://localhost:8080/URL名」で、学生情報を操作するコマンド。
 */
@SpringBootApplication
@RestController
public class Application {

  @Autowired
  private StundetRepository repository;

  //  HashMapを使って、学生の名前と年齢を管理
  private Map<String, String> studentInfoMap = new HashMap<>();

  /**
   * アプリケーションのエントリーポイント。
   *
   * @param args コマンドライン引数
   */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  /**
   * 学生情報の全てを取得するメソッド。
   *
   * @return 全学生の名前と年齢のマップ
   * @RequestParam 送信したいデータを使えるようにするもの
   */
  @GetMapping("/student")
  public Map<String, String> getStudentInfo(@RequestParam("name") String name) {
    Student student = repository.searchByName(name);
    if (student != null) {
      Map<String, String> resultMap = new HashMap<>();
      resultMap.put("name", student.getName());
      resultMap.put("age", String.valueOf(student.getAge()));
      resultMap.put("sex", student.getSex());
      resultMap.put("id", String.valueOf(student.getId()));
      return resultMap;
    } else {
      return Collections.emptyMap();
    }
  }

  /**
   * 学生情報を表示するメソッド（名前と年齢をフォーマットして表示）。
   *
   * @return 学生の名前と年齢の文字列
   * <p>
   * curl "http://localhost:8080/studentInfo/display" →全ての学生情報を表示するコマンド
   */
  @GetMapping("/studentInfo/display")
  public String getStudentInfoDisplay() {
    List<Student> students = repository.getAllStudents();
    StringBuilder sb = new StringBuilder();
    for (Student student : students) {
      sb.append(
          "名前：" + student.getName() + " 年齢：" + student.getAge() + "歳 性別：" + student.getSex()
              + " ID：" + student.getId() + "\n");
    }
    return sb.toString();
  }

  /**
   * 指定した名前の学生が存在するかを確認し、名前のみを返すメソッド。
   *
   * @param name 学生の名前
   * @return 名前が存在すればその名前、なければ「学生が見つかりません」と表示
   * <p>
   * curl "http://localhost:8080/studentInfo/name/名前" →学生情報の名前だけが表示されるコマンド
   */
  @GetMapping("/studentInfo/name/{name}")
  public String getStudentNameOnly(@PathVariable String name) {
    if (studentInfoMap.containsKey(name)) {
      return "名前：" + name;
    } else {
      return "学生が見つかりません。";
    }
  }


  /**
   * @param id
   * @return 名前、年齢、性別、IDを取得する、なければ、学生が見つかりませんと取得する。
   * <p>
   * curl "http://localhost:8080/studentInfo/id/id番号"　→学生情報のidだけを表示するコマンド
   */
  @GetMapping("/studentInfo/id/{id}")
  public String getStudentInfoById(@PathVariable int id) {
    Student student = repository.searchById(id);
    if (student != null) {
      return "名前：" + student.getName() + " 年齢：" + student.getAge() + "歳 性別："
          + student.getSex() + " ID：" + student.getId();
    } else {
      return "学生が見つかりません。";
    }
  }


  /**
   * 指定した名前の学生の年齢を取得するメソッド。
   *
   * @param name 学生の名前
   * @return 名前が存在すれば年齢、なければ「学生が見つかりません」と表示
   * <p>
   * curl "http://localhost:8080/studentInfo/age/名前"　→学生情報の年齢だけを表示するコマンド
   */
  @GetMapping("/studentInfo/age/{name}")
  public String getStudentAge(@PathVariable String name) {
    Student student = repository.searchByName(name); // データベースから学生情報を取得
    if (student != null) {
      return "年齢：" + student.getAge() + "歳"; // 学生の年齢を返す
    } else {
      return "学生が見つかりません。"; // データが存在しない場合のエラーメッセージ
    }
  }


  /**
   * 新しい学生情報を登録するメソッド。
   *
   * @param name 学生の名前
   * @param age  学生の年齢
   * @param sex 学生の性別
   * @param  id 学生のid
   *
   * curl -X POST "http://localhost:8080/student" -d "name=山田" -d "sex=男" -d "age=20" -d "id=1"　→名前、年齢、性別、idを新規登録
   */
  @PostMapping("/student")
  public void registerStudent(@RequestParam String name, @RequestParam String sex,
      @RequestParam int age, @RequestParam int id) {
    repository.registerStudent(name, sex, age, id);
  }


  /**
   * 学生の名前と年齢を更新する
   *
   * @param name 学生の名前
   * @param age  学生の年齢
   * @param sex 学生の性別
   * @param  id 学生のid
   *
   * curl -X PATCH "http://localhost:8080/student" -d "name=名前"　-d"age=年齢" -d "sex=性別" -d "id=id名　→名前と年齢と性別とidを更新するコマンド
   */
  @PatchMapping("/student")
  public void updateStudentInfo(@RequestParam String name, @RequestParam int age,
      @RequestParam String sex, @RequestParam int id) {
    repository.updateStudentInfo(name, age, sex, id);
  }

  /**
   *  指定したidで指定の学生情報を削除する
   *
   * @param id
   */
  @DeleteMapping("/studentdelete")
  public void  deleteStudent(@RequestParam int id) {
    repository.deleteStudentById(id);
  }
}
