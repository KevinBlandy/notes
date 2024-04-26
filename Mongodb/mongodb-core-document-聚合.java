------------------------------
�ۺϼ���
------------------------------
	# �ܵ��Ͳ���
		* �ۺϵĹ��̳�Ϊ�ܵ� pipeline
		* �ܵ��ɶ������ stage ���
		* ÿ�� stage ���ĵ����м���, ���ҰѼ����Ľ������һ��stage
	
	
		const pipeline = [$stage1, $stage2 .....]

		db.[collection].aggregate(pipeline, {
			...options
		});
	

	$match
		* ���ˣ��൱��SQL����� where
	
	$filter
		* ���������ֶ�
			rounds: $filter: {
				  input: "$funding_rounds",		// ָ���ĵ��������ֶ�
				  as: "round",					// Ϊ����ȡһ��������Ҳ�������Ϊ����
				  cond: { $gte: ["$$round.raised_amount", 100000000] }	// ���ڹ�����Ϊ������κ������������ѡ��һ���Ӽ�
			} 

			* $$ ���������ڱ��ʽ�ж���ı�����
			

	$arrayElemAt 
		* ѡ���������ض�λ�õ�Ԫ��
			first_round: { $arrayElemAt: [ "$funding_rounds", 0 ] },		// �ĵ� funding_rounds ����Ԫ���еĵ�һ��
			last_round: { $arrayElemAt: [ "$funding_rounds", -1 ] }			// �ĵ� funding_rounds ����Ԫ���е����һ��
		
	$slice
		* �������д�һ���ض���������ʼ��˳�򷵻ض��Ԫ��
			early_rounds: { $slice: [ "$funding_rounds", 1, 3 ] }		// �ĵ� funding_rounds ����Ԫ�ش����� 1 ��ʼ����ȡ 3 ��Ԫ��
	
	$size
		* ���������ĳ���
			total_rounds: { $size: "$funding_rounds" }

	$project
		* ͶӰ��ѯ��ָ��Ҫ�������ֶ�
		* ����ͶӰǶ���ֶ�
			db.companies.aggregate([
			  {$project: {
				_id: 0,
				name: 1,		// ���� name �ֶ�
				ipo: "$ipo.pub_year",					// ���� ipo ����� pub_year �ֶ�
				valuation: "$ipo.valuation_amount",		// ���� ipo ����� valuation_amount �ֶ�
				funders: "$funding_rounds.investments.financial_org.permalink"	// ���� funding_rounds �����µ� investments �����еĶ���� permalink ����
			  }}
			]).pretty()

	$sort
		* ����

	$group
		* ���飬������sql����� grou by
			$group: {
				_id: { founded_year: "$founded_year" },			// _id ָ���������ĸ��ֶν��з���
				average_number_of_employees: { $avg: "$number_of_employees" }		// ָ���ۺϼ�����
			} 

	$skip
		* offset

	$limit
		* �������

	$lookup
		* ��������

	$geoNear
		* ����ӽ�ĳһ����λ�õ������ĵ���

	$sum
		* �����ܺ�
			total_funding: { $sum: "$funding_rounds.raised_amount" }
			count: { $sum: 1 }

	$avg
		* ����ƽ��ֵ

	$min
		* ��ȡ�����������ĵ���Ӧֵ����Сֵ

	$max
		* ��ȡ�����������ĵ���Ӧֵ�����ֵ

	$push
		* �ڽ���ĵ��в���ֵ��һ�������У����������ֵ��ӵ��������й�������������������

	$first
		* ������Դ�ĵ��������ȡ��һ���ĵ�����

	$last
		* ������Դ�ĵ��������ȡ���һ���ĵ�����

	$mergeObjects
		* ������ĵ��ϲ�Ϊ�����ĵ�
	
	$unwind
		* չ����unwind����ָ�������ֶ��е�ÿ��Ԫ�ض��γ�һ������ĵ���
			
			// ԭʼ������һ�� 2 ��Ԫ�ص�����
			{
				"key1": 1,
				"key2": 2,
				"key3": ["a", "b"]
			}

			// ָ��Ҫչ��������
			{"$unwind": "$key3"},

			// չ��������������Ԫ�ض������ó�����ԭʼ�������һ���µ��ĵ�
			{
				"key1": 1,
				"key2": 2,
				"key3": "a"
			}
			{
				"key1": 1,
				"key2": 2,
				"key3": "b"
			}

		

	$merge 
		* ���ۺϹܵ����ɵ��ĵ�д�뼯���У������ǾۺϹܵ������һ���׶Ρ�
		