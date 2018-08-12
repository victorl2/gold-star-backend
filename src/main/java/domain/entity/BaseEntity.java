package domain.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * Entidade base para todos os objetos do domínio que serão persistidos.
 * 
 * @author Victor Silva
 * @since 21-04-2018
 * @version 1.0
 */

@MappedSuperclass
public abstract class BaseEntity {	
	/**
	 * identificador da entidade
	 */
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private long ID;
	
	public long getID() {
		return this.ID;
	}
	
	public void setID(long newID) {
		this.ID = newID;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (ID ^ (ID >>> 32));
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
		if (ID != other.ID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BaseEntity [ID=" + ID + "]";
	}

}
