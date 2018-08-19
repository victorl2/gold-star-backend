package domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;


/**
 * Entidade base para todos os objetos do domínio que serão persistidos.
 * 
 * @author Victor Silva
 * @since 21-04-2018
 * @version 1.1
 */

@MappedSuperclass
public abstract class BaseEntity implements Serializable{	
	private static final long serialVersionUID = -8953904135168796600L;
	
	/**
	 * identificador da entidade
	 */
	@Id @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid2")
	@Column(name = "ID")
	private String ID;
	
	public String getID() {
		return this.ID;
	}
	
	public void setID(String newID) {
		this.ID = newID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity other = (BaseEntity) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BaseEntity [ID=" + ID + "]";
	}
	
}
