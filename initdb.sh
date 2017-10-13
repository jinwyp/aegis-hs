#!/bin/bash

echo "drop database hsdb; create database hsdb;" | mysql -umysql -pmysql;
mysql -umysql -pmysql < src/main/resources/db/migration/V1__user.sql;
mysql -umysql -pmysql < src/main/resources/db/migration/V2__same.sql;
mysql -umysql -pmysql < src/main/resources/db/migration/V3__ying.sql;
mysql -umysql -pmysql < src/main/resources/db/migration/V4__cang.sql;
mysql -umysql -pmysql < src/main/resources/db/migration/V5__data.sql;

