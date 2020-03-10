docker-compose exec hive-server bash
/opt/hive/bin/beeline -u jdbc:hive2://localhost:10000 -f ../HqlScript.sql