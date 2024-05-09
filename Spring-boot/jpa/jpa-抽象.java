-----------------------
respository����
-----------------------
	// ����һ�������ӿ�
	import org.springframework.data.jpa.repository.JpaRepository;
	import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
	import org.springframework.data.querydsl.QuerydslPredicateExecutor;
	import org.springframework.data.repository.NoRepositoryBean;

	@NoRepositoryBean
	public interface BaseRepository <T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor <T>, QuerydslPredicateExecutor<T> {

	}


-----------------------
Service����
-----------------------
	// ����һ��  BaseService
	import org.springframework.data.jpa.repository.JpaRepository;
	import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
	import org.springframework.data.querydsl.QuerydslPredicateExecutor;

	public interface BaseService <T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor <T>, QuerydslPredicateExecutor<T> {

	}

	
	// ����һ�� AbstractService ����ʵ����
	import java.util.List;
	import java.util.Optional;
	import java.util.function.Function;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.data.domain.Example;
	import org.springframework.data.domain.Page;
	import org.springframework.data.domain.Pageable;
	import org.springframework.data.domain.Sort;
	import org.springframework.data.jpa.domain.Specification;
	import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
	import org.springframework.transaction.annotation.Transactional;

	import com.querydsl.core.types.OrderSpecifier;
	import com.querydsl.core.types.Predicate;
	import com.querydsl.jpa.impl.JPAQueryFactory;

	import chatty.repository.BaseRepository;
	import jakarta.persistence.EntityManager;
	import jakarta.persistence.PersistenceContext;

	public class AbstractService<T, ID> implements BaseService<T, ID> {

		@Autowired
		protected BaseRepository<T, ID> baseRepository;

		@PersistenceContext
		protected EntityManager entityManager;

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public List<T> findAll() {
			return this.baseRepository.findAll();
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public List<T> findAll(Sort sort) {
			return this.baseRepository.findAll(sort);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public List<T> findAllById(Iterable<ID> ids) {
			return this.baseRepository.findAllById(ids);
		}

		@Override
		@Transactional(rollbackFor = Throwable.class)
		public <S extends T> List<S> saveAll(Iterable<S> entities) {
			return this.baseRepository.saveAll(entities);
		}

		@Override
		@Transactional(rollbackFor = Throwable.class)
		public void flush() {
			this.baseRepository.flush();
		}

		@Transactional(rollbackFor = Throwable.class)
		public <S extends T> S saveAndFlush(S entity) {
			return this.baseRepository.saveAndFlush(entity);
		}

		@Override
		@Transactional(rollbackFor = Throwable.class)
		@Deprecated
		public void deleteInBatch(Iterable<T> entities) {
			this.baseRepository.deleteInBatch(entities);
		}

		@Override
		@Transactional(rollbackFor = Throwable.class)
		public void deleteAllInBatch() {
			this.baseRepository.deleteAllInBatch();
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		@Deprecated
		public T getOne(ID id) {
			return this.baseRepository.getOne(id);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public <S extends T> List<S> findAll(Example<S> example) {
			return this.baseRepository.findAll(example);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
			return this.baseRepository.findAll(example, sort);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public Page<T> findAll(Pageable pageable) {
			return this.baseRepository.findAll(pageable);
		}

		@Override
		@Transactional(rollbackFor = Throwable.class)
		public <S extends T> S save(S entity) {
			return this.baseRepository.save(entity);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public Optional<T> findById(ID id) {
			return this.baseRepository.findById(id);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public boolean existsById(ID id) {
			return this.baseRepository.existsById(id);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public long count() {
			return this.baseRepository.count();
		}

		@Override
		@Transactional(rollbackFor = Throwable.class)
		public void deleteById(ID id) {
			this.baseRepository.deleteById(id);
		}

		@Override
		@Transactional(rollbackFor = Throwable.class)
		public void delete(T entity) {
			this.baseRepository.delete(entity);
		}

		@Override
		@Transactional(rollbackFor = Throwable.class)
		public void deleteAll(Iterable<? extends T> entities) {
			this.baseRepository.deleteAll(entities);
		}

		@Override
		@Transactional(rollbackFor = Throwable.class)
		public void deleteAll() {
			this.baseRepository.deleteAll();
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public <S extends T> Optional<S> findOne(Example<S> example) {
			return this.baseRepository.findOne(example);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
			return this.baseRepository.findAll(example, pageable);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public <S extends T> long count(Example<S> example) {
			return this.baseRepository.count(example);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public <S extends T> boolean exists(Example<S> example) {
			return this.baseRepository.exists(example);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public Optional<T> findOne(Specification<T> spec) {
			return this.baseRepository.findOne(spec);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public List<T> findAll(Specification<T> spec) {
			return this.baseRepository.findAll(spec);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public Page<T> findAll(Specification<T> spec, Pageable pageable) {
			return this.baseRepository.findAll(spec, pageable);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public List<T> findAll(Specification<T> spec, Sort sort) {
			return this.baseRepository.findAll(spec, sort);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public long count(Specification<T> spec) {
			return this.baseRepository.count(spec);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public Optional<T> findOne(Predicate predicate) {
			return this.baseRepository.findOne(predicate);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public Iterable<T> findAll(Predicate predicate) {
			return this.baseRepository.findAll(predicate);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public Iterable<T> findAll(Predicate predicate, Sort sort) {
			return this.baseRepository.findAll(predicate, sort);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public Iterable<T> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
			return this.baseRepository.findAll(predicate, orders);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public Iterable<T> findAll(OrderSpecifier<?>... orders) {
			return this.baseRepository.findAll(orders);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public Page<T> findAll(Predicate predicate, Pageable pageable) {
			return this.baseRepository.findAll(predicate, pageable);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public long count(Predicate predicate) {
			return this.baseRepository.count(predicate);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public boolean exists(Predicate predicate) {
			return this.baseRepository.exists(predicate);
		}

		@Override
		@Transactional(rollbackFor = Throwable.class)
		public <S extends T> List<S> saveAllAndFlush(Iterable<S> entities) {
			return this.baseRepository.saveAllAndFlush(entities);
		}

		@Override
		@Transactional(rollbackFor = Throwable.class)
		public void deleteAllInBatch(Iterable<T> entities) {
			this.baseRepository.deleteAllInBatch(entities);
		}

		@Override
		@Transactional(rollbackFor = Throwable.class)
		public void deleteAllByIdInBatch(Iterable<ID> ids) {
			this.baseRepository.deleteAllByIdInBatch(ids);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		@Deprecated
		public T getById(ID id) {
			return this.baseRepository.getById(id);
		}

		@Override
		@Transactional(rollbackFor = Throwable.class)
		public void deleteAllById(Iterable<? extends ID> ids) {
			this.baseRepository.deleteAllById(ids);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public <S extends T, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
			return this.baseRepository.findBy(example, queryFunction);
		}

		@Override
		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public <S extends T, R> R findBy(Predicate predicate, Function<FetchableFluentQuery<S>, R> queryFunction) {
			return this.baseRepository.findBy(predicate, queryFunction);
		}

		@Override
		public T getReferenceById(ID id) {
			return this.baseRepository.getReferenceById(id);
		}

		@Override
		public boolean exists(Specification<T> spec) {
			return this.baseRepository.exists(spec);
		}

		@Override
		public long delete(Specification<T> spec) {
			return this.baseRepository.delete(spec);
		}

		@Override
		public <S extends T, R> R findBy(Specification<T> spec, Function<FetchableFluentQuery<S>, R> queryFunction) {
			return this.baseRepository.findBy(spec, queryFunction);
		}

		// ----------------------- �Զ��巽��

		public JPAQueryFactory newQuery() {
			return new JPAQueryFactory(this.entityManager);
		}

		@SuppressWarnings({ "unchecked", "hiding" })
		public <T> T repository() {
			return (T) this.baseRepository;
		}

		@Transactional(rollbackFor = Throwable.class)
		public <R> R apply(Function<JPAQueryFactory, R> function) {
			return function.apply(this.newQuery());
		}

		@Transactional(readOnly = true, rollbackFor = Throwable.class)
		public <R> R applyReadOnly(Function<JPAQueryFactory, R> function) {
			return function.apply(this.newQuery());
		}
	}