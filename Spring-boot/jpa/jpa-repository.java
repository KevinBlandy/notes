----------------------
Repository			  |
----------------------
	# ���ĵ� Repository
		Repository		// ��ʶ�ӿڣ�spring�����
			|-CrudRepository // ������CRUD
				|PagingAndSortingRepository // ����ͷ�ҳ��ѯ
			|-QueryByExampleExecutor // ���Ը���Example������ѯ
					|-JpaRepository  // �̳з�ҳ,����,crud�Ľӿ�
						|-JpaRepositoryImplementation (ʵ���� JpaSpecificationExecutor �ӿ�) // �ӿ�
							|-SimpleJpaRepository // �������һ������һ��ʵ����
			
		QuerydslPredicateExecutor			// querydls�Ľӿ�
			|-QuerydslJpaPredicateExecutor	// querydls�Ľӿ�ʵ��
			|-QuerydslKeyValueRepository

		JpaSpecificationExecutor // Specification��̬��ѯ�ӿ�
	
	# SimpleJpaRepository
		* ����ʵ���� Repository �ӿ�, ��̬�������
		* ����ͨ������ EntityManager ����ɶ����ݿ�Ĳ���
	
	# JpaSpecificationExecutor
		* �Գ�һ�ŵ�Specification�����ӿ�
	
	# QuerydslJpaPredicateExecutor
		* querydls�Ľӿ�ʵ��
	

----------------------
CrudRepository		  |
----------------------

public interface CrudRepository<T, ID> extends Repository<T, ID> {
	<S extends T> S save(S entity);
		* ������޸�, ����ʹ�� sava ����
		* ������ǿ�ʵ��entity�Ƿ�������, �����Ǹ���, �����Ǵ���

		* ִ���޸�֮ǰ, ���ȸ���idִ��һ�ζ�, ���������, �����(???)

	<S extends T> Iterable<S> saveAll(Iterable<S> entities);
		* �־û�, ���������id, id��ֵ���д��������

	Optional<T> findById(ID id);
		* ����ID����

	boolean existsById(ID id);
		* ����ID�ж��Ƿ����

	Iterable<T> findAll();
	Iterable<T> findAllById(Iterable<ID> ids);
		* �������л��߸���ID����һ����¼

	long count();
		* ��ȡ��¼��������


	void deleteById(ID id);
	void delete(T entity);
	void deleteAll(Iterable<? extends T> entities);
	void deleteAll();
	
		* ִ����Щɾ����ʱ��,���ȸ���ID������¼ (deleteAll ���ȼ������м�¼)
		* �����¼������(���ִ�е���:deleteById,��ֱ���׳��쳣 EmptyResultDataAccessException ),���Ȳ���,��ɾ��
		* �����¼�Ѿ�����,ֱ��ɾ��, ɾ���������Ǹ���ID��ɾ��
	
	void deleteAllById(Iterable<? extends ID> ids);

}
----------------------
JpaRepository		  |
----------------------
	public interface JpaRepository<T, ID> extends PagingAndSortingRepository<T, ID>, QueryByExampleExecutor<T> {
		
		<S extends T> Optional<S> findOne(Example<S> example);
		T getOne(ID id);
		List<T> findAllById(Iterable<ID> ids);
			
		
		<S extends T> List<S> saveAll(Iterable<S> entities);

		void flush();
		<S extends T> S saveAndFlush(S entity);
			* ���沢������ˢ��
		
		
		List<T> findAll();
		List<T> findAll(Sort sort);
		Iterable<T> findAll(Sort sort);
		Page<T> findAll(Pageable pageable);
		<S extends T> List<S> findAll(Example<S> example);
		<S extends T> List<S> findAll(Example<S> example, Sort sort);
		<S extends T> Page<S> findAll(Example<S> example, Pageable pageable);

		
		<S extends T> long count(Example<S> example);
		<S extends T> boolean exists(Example<S> example);

		void deleteAllInBatch();
		void deleteInBatch(Iterable<T> entities);
			* ����ɾ��, ������ִ�в�ѯ,
			* deleteAllInBatch ֱ��ִ��: DELETE FROM `table`;
			* deleteInBatch ����ж������, ͨ��or����ɾ��: DELETE FRROM `table` WHERE `id` = ? OR `id` = ? OR `id` = ?;
		

		<S extends T> List<S> saveAllAndFlush(Iterable<S> entities);
		void deleteAllInBatch(Iterable<T> entities);
		void deleteAllByIdInBatch(Iterable<ID> ids);
		T getById(ID id);
			
	}
