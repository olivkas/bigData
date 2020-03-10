cd docker-hive-master
docker-compose up -d
id=$(docker ps | grep hive-server | AWK '{printf $1}')
docker cp ../../input/000000 $id:/opt/hive/examples/sample.txt
docker cp ../HqlScript.sql $id:HqlScript.sql
