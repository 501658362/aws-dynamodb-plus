# aws-dynamodb-plus
>针对 aws DynamoDB 高级接口的增强，高级接口使用方法可以看<a href="https://docs.amazonaws.cn/amazondynamodb/latest/developerguide/HigherLevelInterfaces.html" target="_blank">官方文档</a>
## 提供的功能
> 1. 保存、选择性保存、批量保存
> 2. 删除、批量删除
> 3. count
> 4. 根据主键查询
> 5. query接口
> 6. 后续会提供更多，如果有好的建议可以联系我们
## 使用方法
>1 . 添加pom  
```
<dependency>
    <groupId>top.chenyanjin</groupId>
    <artifactId>aws-dynamodb-plus</artifactId>
    <version>1.1.0</version>
</dependency>
```
>2 . 在对应 mapper or repository 接口继承接口，这里提供了两个接口，一个是只有 hashKey，一个是有 hashKey 和 sortKey，根据自己的表选择继承对应的接口。
>泛型 ```T``` 是 ```entity```，```H``` 是 ```hashKey``` 的类型，```S``` 是 ```sortKey``` 的类型
```java
// 示例对于的  entity
@Data
@DynamoDBTable(tableName = "user_map")
public class UserMapPojo {

    @DynamoDBHashKey(attributeName = "user_id")
    private String userId;

    @DynamoDBRangeKey(attributeName = "map_id")
    private Integer mapId;

    @DynamoDBAttribute(attributeName = "map")
    private String map;
}
```
```java
// 只有 hashKey
public interface BaseHDdbMapper<T, H> extends BaseDdbMapper<T> {
}
// 同时有 hashKey 和 sortKey
public interface BaseHSDdbMapper<T, H, S> extends BaseDdbMapper<T> {
}
// 示例
public interface UserMapRepository extends BaseHSDdbMapper<UserMapPojo, String, Integer> {
}
```
> 3 . 在对应的实现类继承我们提供的抽象类，继承抽象类后需要重写```getMapper()```方法，返回```DynamoDBMapper```对象
```java
// 只有 hashKey 继承这个
public abstract class AbstractHDdbMapperImpl<T, H> extends AbstractDdbMapperImpl<T> implements BaseHDdbMapper<T, H> {
}
// 同时有 hashKey 和 sortKey 继承这个
public abstract class AbstractHSDdbMapperImpl<T, H, S> extends AbstractDdbMapperImpl<T> implements BaseHSDdbMapper<T, H, S> {
}
// 示例
@Repository
public class UserMapRepositoryImpl extends AbstractHSDdbMapperImpl<UserMapPojo, String, Integer> implements UserMapRepository {
    @Resource
    private AmazonDynamoDB amazonDynamoDB;
    @Override
    protected DynamoDBMapper getMapper() {
        // 这个很重要
        return new DynamoDBMapper(this.amazonDynamoDB);
    }
}
```
> 4 . 使用 
```java
@SpringBootTest
class ApplicationTests {
    @Resource
    private UserMapRepository userMapRepository;
    @Test
    void save() {
        // 保存
        UserMapPojo userMapPojo = new UserMapPojo();
        userMapPojo.setUserId("xxxx");
        userMapPojo.setMapId(1);
        // 保存
        userMapRepository.save(userMapPojo);
        // 选择性保存
        userMapRepository.saveSelective(userMapPojo);
        // 批量保存
        userMapRepository.save(Lists.newArrayList(userMapPojo));
    }
    @Test
    void getById() {
        // 根据主键查询
        UserMapPojo xxx = userMapRepository.getById("xxx", 1);
    }
    @Test
    void query() {
        final Map<String, AttributeValue> attributeValueMap = new HashMap<>();
        attributeValueMap.put(":PK", new AttributeValue().withS("xxx"));
        attributeValueMap.put(":SK", new AttributeValue().withN("1"));
        // 查询 
        String keyCondition = "PK = :PK and SK =:SK";
        List<UserMapPojo> list = this.userMapRepository.query(keyCondition, attributeValueMap);
    }
}
```