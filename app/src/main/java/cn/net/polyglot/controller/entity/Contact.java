package cn.net.polyglot.controller.entity;

/**
 * 联系人好友
 */
public class Contact {

    private String id;
    private String nickName;
    private String newMsg;
    private String headImg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName==null?id:nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNewMsg() {
        return newMsg;
    }

    public void setNewMsg(String newMsg) {
        this.newMsg = newMsg;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
}
