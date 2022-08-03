----------------------------
binary
----------------------------

----------------------------
����
----------------------------
	const (
		MaxVarintLen16 = 3
		MaxVarintLen32 = 5
		MaxVarintLen64 = 10
	)

	* �����ֽ���
		var BigEndian bigEndian
		var LittleEndian littleEndian

----------------------------
type
----------------------------
	# type AppendByteOrder interface {
		AppendUint16([]byte, uint16) []byte
		AppendUint32([]byte, uint32) []byte
		AppendUint64([]byte, uint64) []byte
		String() string
	}

	# type ByteOrder interface {
			Uint16([]byte) uint16
			Uint32([]byte) uint32
			Uint64([]byte) uint64
			PutUint16([]byte, uint16)
			PutUint32([]byte, uint32)
			PutUint64([]byte, uint64)
			String() string
		}

		* �ֽ���ӿ�

----------------------------
����
----------------------------
	func AppendUvarint(buf []byte, x uint64) []byte
	func AppendVarint(buf []byte, x int64) []byte
	func PutUvarint(buf []byte, x uint64) int
	func PutVarint(buf []byte, x int64) int
	func Read(r io.Reader, order ByteOrder, data interface{}) error
		* ��ָ����Reader����ָ�����ֽ��򣬶�ȡ���ݵ�data��

	func ReadUvarint(r io.ByteReader) (uint64, error)
	func ReadVarint(r io.ByteReader) (int64, error)
		* �� ByteReader�ж�ȡ��ֵ
		* ���Ŀ��Readerû��ʵ��ByteReader�ӿڣ������� bufio��װ
			bufio.NewReader(reader)

	func Size(v interface{}) int
	func Uvarint(buf []byte) (uint64, int)
		* ����10 bytes ֮��ֹͣ��ȡ���Ա��������˷ѡ������Ҫ����10 bytes �����ص��ֽ�����-11 ��

	func Varint(buf []byte) (int64, int)
		* ��buf�н����һ��int64�������ظ�ֵ�Ͷ�ȡ���ֽ���n��>0��
		* ����������󣬸�ֵΪ0�����ֽ���n<=0���京������
			* n == 0: buf ̫С�ˡ�
			* n < 0: ֵ����64λ(���)

	func Write(w io.Writer, order ByteOrder, data interface{}) error 
		* ��data��ָ�����ֽ�����д�뵽 w
		* data������boo/[]bool/��ֵ/[]��ֵ
	


----------------------------
demo
----------------------------
	# ��ֵ�Ͷ����������ת��
		buffer := make([]byte, 4, 4)
		// ��һ����ֵ��ת��Ϊ�ֽ����飬�Ž�buffer
		binary.BigEndian.PutUint32(buffer, 0xFFFFFFFF)	// 0xFFFFFFFF = 4294967295
		log.Println(buffer)		// [255 255 255 255]

		// ��buffer�ж�ȡһ����ֵ
		val := binary.BigEndian.Uint32(buffer)
		log.Println(val)		// 4294967295