package domain.exception;

import javax.persistence.PersistenceException;

/**
 * Sinaliza erros de persistencia dentro das classe <b>DAO</b>
 *
 */
public class DAOException extends PersistenceException{
	private static final long serialVersionUID = -5042935932108656696L;
	
	public String message;
	
	public DAOException(String msg) {
		this.message = msg;
	}
	
	@Override
    public String getMessage(){
        return message;
    }

}
