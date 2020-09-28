package cn.denghanxi.data.mapper;

import cn.denghanxi.data.Person;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PersonJavaMapper {
    @Insert("Insert into t_person(name, age, address) values (#{name}, #{age}, #{address})")
    public Integer save(Person person);

    @Select("Select * from t_person where id=#{personId}")
    public Person getPersonById(Integer personId);

    @Select("Select * from t_person where name=#{name}")
    public Person getPersonByName(String name);

    @Update("        CREATE TABLE IF NOT EXISTS t_person\n" +
            "        (\n" +
            "            id      INTEGER PRIMARY KEY AUTO_INCREMENT,\n" +
            "            name    VARCHAR(64) NOT NULL,\n" +
            "            age     INT         NOT NULL,\n" +
            "            address VARCHAR(64) NOT NULL\n" +
            "        );")
    public void initDatabase();
}
