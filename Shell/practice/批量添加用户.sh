#!/bin/bash
# ============================================= 2019年1月22日 13:56:43
:<<EOF
users.csv
rich,Richard Blum
christine,Christine Bresnahan
barbara,Barbara Blum
tim,Timothy Bresnahan
EOF

input="users.csv"

while IFS=',' read -r userid name ; do
	echo "adding $userid"
	# -r 表示系统用户 -c 表示说明  -m 表示创建家目录
	useradd -r -c "$name" -m "$userid"
done < "$input"