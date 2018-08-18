package domain.dao;

import javax.persistence.EntityManager;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import org.hibernate.Session;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import domain.entity.BaseEntity;
import domain.repository.Repository;


public abstract class BaseDAO<E extends BaseEntity> implements Repository<E>{
	protected static final Integer IN_MAX_ELEMENTS = 1000;
	public static final Integer JDBC_BATCH_SIZE = 50;
	
	private EntityManager em;
	protected Class<E> clazz;
	
	protected EntityManager getEntityManager() {
		return em;
	}
	
	@PersistenceContext
	protected void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	@PostConstruct
	public void postConstruct() {
		clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected Session getSession() {
		return (Session) getEntityManager().getDelegate();
	}
	

	public Optional<E> buscarPorID(String oid) {
		return Optional.ofNullable(getEntityManager().find(clazz, oid));		
	}
	

	public List<E> buscarTodos() {
		return getEntityManager().createQuery("from " + clazz.getName()).getResultList();
	}

	public Optional<E> persist(E entity) {
		try {
			getEntityManager().persist(entity);
			getEntityManager().flush();
			return Optional.of(entity);
		} catch (Exception exception) {;
			return Optional.empty();
		}
	}
	

	public Optional<E> merge(E entity) {
		try {
			E persistedEntity = getEntityManager().merge(entity);
			getEntityManager().flush();
			return Optional.of(persistedEntity);
		} catch (PersistenceException exception) {
			return Optional.empty();
		}
	}
	
	public void refresh(E entity) {
		getEntityManager().refresh(entity);
	}
	

	public Optional<E> salvar(E entity) {
		try {
			E persistedEntity = entity;
			if (entity.getID() != 0) {
				getEntityManager().persist(entity);
			} else {
				persistedEntity = getEntityManager().merge(entity);
			}
			getEntityManager().flush();
			return Optional.of(persistedEntity);
		} catch (PersistenceException exception) {
			return Optional.empty();
		}
	}
	

	public void deletar(E entity) {
		try {
			getEntityManager().remove(entity);
			getEntityManager().flush();
		} catch (PersistenceException exception) {
		}
	}
	
	
}
