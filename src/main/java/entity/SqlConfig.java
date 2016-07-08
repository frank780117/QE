package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "QE_SQL_CONFIG")
public class SqlConfig{

    @Id
    @Column(name = "RID")
    private String id;

    @Column(name = "SQL_VALUE")
    private String sqlValue;

    @Column(name = "TITLE_NAME")
    private String titleName;

    @Column(name = "KEY_VALUE")
    private String keyValue;

    @Column(name = "USE_COMMENT")
    private String comment;

    public String getSqlValue(){
        return sqlValue;
    }

    public void setSqlValue(String sqlValue){
        this.sqlValue = sqlValue;
    }

    public String getTitleName(){
        return titleName;
    }

    public void setTitleName(String titleName){
        this.titleName = titleName;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getComment(){
        return comment;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public String getKeyValue(){
        return keyValue;
    }

    public void setKeyValue(String keyValue){
        this.keyValue = keyValue;
    }

    @Override
    public String toString(){
        return String.format(
                             "SqlConfig[id=%d, sqlValue='%s', titleName='%s']",
                             id, sqlValue, titleName);
    }
}