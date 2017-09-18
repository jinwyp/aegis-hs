#!/bin/bash

echo "drop database hsdb; create database hsdb;" | mysql -umysql -pmysql;
mysql -umysql -pmysql < src/main/resources/db/migration/V1__create_user.sql;
mysql -umysql -pmysql < src/main/resources/db/migration/V2__ying.sql;
mysql -umysql -pmysql < src/main/resources/db/migration/V3__cang.sql;

