------------------------------
OffsetAndMetadata			  |
------------------------------
	# 用于描述分区信息 
	# 源码
		package org.apache.kafka.clients.consumer;

		import org.apache.kafka.common.requests.OffsetFetchResponse;

		import java.io.Serializable;
		import java.util.Objects;
		import java.util.Optional;
		public class OffsetAndMetadata implements Serializable {
			private static final long serialVersionUID = 2019555404968089681L;

			private final long offset;			// 消费位移
			private final String metadata;

			private final Integer leaderEpoch;

			public OffsetAndMetadata(long offset, Optional<Integer> leaderEpoch, String metadata) {
				if (offset < 0)
					throw new IllegalArgumentException("Invalid negative offset");

				this.offset = offset;
				this.leaderEpoch = leaderEpoch.orElse(null);

				if (metadata == null)
					this.metadata = OffsetFetchResponse.NO_METADATA;
				else
					this.metadata = metadata;
			}

			public OffsetAndMetadata(long offset, String metadata) {
				this(offset, Optional.empty(), metadata);
			}

			public OffsetAndMetadata(long offset) {
				this(offset, "");
			}

			public long offset() {
				return offset;
			}

			public String metadata() {
				return metadata;
			}

			public Optional<Integer> leaderEpoch() {
				return Optional.ofNullable(leaderEpoch);
			}

			@Override
			public boolean equals(Object o) {
				if (this == o) return true;
				if (o == null || getClass() != o.getClass()) return false;
				OffsetAndMetadata that = (OffsetAndMetadata) o;
				return offset == that.offset &&
						Objects.equals(metadata, that.metadata) &&
						Objects.equals(leaderEpoch, that.leaderEpoch);
			}

			@Override
			public int hashCode() {
				return Objects.hash(offset, metadata, leaderEpoch);
			}

			@Override
			public String toString() {
				return "OffsetAndMetadata{" +
						"offset=" + offset +
						", leaderEpoch=" + leaderEpoch +
						", metadata='" + metadata + '\'' +
						'}';
			}

		}
