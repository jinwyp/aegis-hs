#!/bin/bash

rm -fr src/main/java/com/yimei/hs/entity/*.java
rm -fr src/main/java/com/yimei/hs/mapper/*.java
rm -fr src/main/resources/mapper/*.xml

mvn mybatis-generator:generate

(
  cd src/main/java/com/yimei/hs/entity/;
  perl -i -pe 's/java.util.Date/java.time.LocalDateTime/g' *.java;
  perl -i -pe 's/\(Date /\(LocalDateTime /g' *.java;
  perl -i -pe 's/ Date / LocalDateTime /g' *.java;
);


