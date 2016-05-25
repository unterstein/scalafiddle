package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.WhenCreated;
import com.avaje.ebean.annotation.WhenModified;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public class AbstractModel extends Model {

  @Id
  public Long id;

  @WhenCreated
  public Date created;

  @WhenModified
  public Date lastModified;

  public boolean active;

}
