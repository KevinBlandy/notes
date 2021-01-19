#!/bin/bash
# ------------------------------------- 2019年1月22日 20:56:03
# 生成SQL的文件
outfile='members.sql'
IFS=','
while read lname fname address city state zip
do
cat >> $outfile << EOF
INSERT INTO members (lname,fname,address,city,state,zip) VALUES
('$lname', '$fname', '$address', '$city', '$state', '$zip');
EOF
done < ${1}

:<<EOF
members.csv 文件内容
Blum,Richard,123 Main St.,Chicago,IL,60601
Blum,Barbara,123 Main St.,Chicago,IL,60601
Bresnahan,Christine,456 Oak Ave.,Columbus,OH,43201
Bresnahan,Timothy,456 Oak Ave.,Columbus,OH,43201

启动脚本
./demo.sh members.csv 

EOF