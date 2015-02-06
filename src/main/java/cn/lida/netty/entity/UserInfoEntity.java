package cn.lida.netty.entity;

/**
 * Description:TODO
 * Author:zhanglida
 * Date:15-1-22
 * Email:zhanglida@huoqiu.cn
 */
public class UserInfoEntity {
    Long id ;
    String userName ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserInfoEntity(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }
}
