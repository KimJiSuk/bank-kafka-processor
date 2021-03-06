# 본 과제

## Overview
> 사전 준비 된 Kafka Topic[bank] -> 파이프 라인 구성 -> Insert MySQL
---
## 과제1
<img width="500" alt="pipe-line" src="https://user-images.githubusercontent.com/3543580/123251344-0e47eb80-d526-11eb-9075-931a3beaf4c5.png">

---
## 과제2

> SQL

```
select tr.age as '대',
       SUM(IF(tr.week = 2, tr.cost, 0)) as '월',
       SUM(IF(tr.week = 3, tr.cost, 0)) as '화',
       SUM(IF(tr.week = 4, tr.cost, 0)) as '수',
       SUM(IF(tr.week = 5, tr.cost, 0)) as '목',
       SUM(IF(tr.week = 6, tr.cost, 0)) as '금',
       SUM(IF(tr.week = 7, tr.cost, 0)) as '토',
       SUM(IF(tr.week = 1, tr.cost, 0)) as '일'
from (select t.*,
             CONCAT(FLOOR((CAST(REPLACE(CURRENT_DATE,'-','') AS UNSIGNED) - CAST(c.birth AS UNSIGNED)) / 100000), '0대') as age,
             DAYOFWEEK(t.reg_dttm) as 'week',
             IF(STRCMP(t.acno, LAG(t.acno) over (ORDER BY t.acno, t.seqno)) = 0,
                 ABS(t.aftr_bal - IFNULL(LAG(t.aftr_bal) over (ORDER BY t.acno, t.seqno), 0)), t.aftr_bal
                 ) AS 'cost'
    from tran t
    join acco a on t.acno = a.acno
    join customer c on a.cstno = c.cstno) tr
GROUP BY tr.age
ORDER BY tr.age
```

> SQL 실행계획
<table>
<tr><th>id</th><th>select_type</th><th>table</th><th>partitions</th><th>type</th><th>possible_keys</th><th>key</th><th>key_len</th><th>ref</th><th>rows</th><th>filtered</th><th>Extra</th></tr>
<tr><td>1</td><td>PRIMARY</td><td><derived2></td><td>NULL</td><td>ALL</td><td>NULL</td><td>NULL</td><td>NULL</td><td>NULL</td><td>300</td><td>100</td><td>Using temporary; Using filesort</td></tr>
<tr><td>2</td><td>DERIVED</td><td>t</td><td>NULL</td><td>ALL</td><td>PRIMARY</td><td>NULL</td><td>NULL</td><td>NULL</td><td>300</td><td>100</td><td>Using temporary; Using filesort</td></tr>
<tr><td>2</td><td>DERIVED</td><td>a</td><td>NULL</td><td>eq_ref</td><td>PRIMARY</td><td>PRIMARY</td><td>32</td><td>bank.t.acno</td><td>1</td><td>100</td><td>Using where</td></tr>
<tr><td>2</td><td>DERIVED</td><td>c</td><td>NULL</td><td>eq_ref</td><td>PRIMARY</td><td>PRIMARY</td><td>32</td><td>bank.a.cstno</td><td>1</td><td>100</td><td>NULL</td></tr>
</table>

> SQL 실행결과
<table>
<tr><th>대</th><th>월</th><th>화</th><th>수</th><th>목</th><th>금</th><th>토</th><th>일</th></tr>
<tr><td>10대</td><td>258234</td><td>335708</td><td>268321</td><td>161524</td><td>54559</td><td>207735</td><td>268236</td></tr>
<tr><td>20대</td><td>161743</td><td>322447</td><td>187916</td><td>376279</td><td>148248</td><td>313415</td><td>344742</td></tr>
<tr><td>30대</td><td>406280</td><td>912161</td><td>376850</td><td>188902</td><td>210543</td><td>405862</td><td>261309</td></tr>
<tr><td>40대</td><td>88704</td><td>211115</td><td>112369</td><td>131372</td><td>93740</td><td>302085</td><td>165480</td></tr>
<tr><td>50대</td><td>107377</td><td>527568</td><td>179432</td><td>253968</td><td>10805</td><td>207920</td><td>134220</td></tr>
<tr><td>60대</td><td>174084</td><td>318620</td><td>4458</td><td>58903</td><td>0</td><td>100024</td><td>124745</td></tr>
<tr><td>70대</td><td>61889</td><td>0</td><td>65960</td><td>33069</td><td>97316</td><td>218522</td><td>41497</td></tr>
</table>

---
## 과제3

> SQL

```
select tra.acno,
       tra.seqno,
       tra.reg_dttm,
       tra.tx_chnl,
       tra.aftr_bal,
       tra.atm_cd,
       tra.recv_nm,
       tra.auto_cycl
from (select tr.*,
           RANK() over (PARTITION BY tr.acno ORDER BY tr.cost DESC, tr.seqno) as 'rank'
    from (select t.*,
                 IF(STRCMP(t.acno, LAG(t.acno) over (ORDER BY t.acno, t.seqno)) = 0,
                     ABS(t.aftr_bal - IFNULL(LAG(t.aftr_bal) over (ORDER BY t.acno, t.seqno), 0)), t.aftr_bal
                     ) AS 'cost'
            from tran t
            where t.acno in (select a.link_acno
                             from acco a
                             where a.link_acno != '')) tr) tra
where tra.rank = 1
```

> SQL 실행계획

<table>
<tr><th>id</th><th>select_type</th><th>table</th><th>partitions</th><th>type</th><th>possible_keys</th><th>key</th><th>key_len</th><th>ref</th><th>rows</th><th>filtered</th><th>Extra</th></tr>
<tr><td>1</td><td>PRIMARY</td><td><derived2></td><td>NULL</td><td>ref</td><td><auto_key0></td><td><auto_key0></td><td>8</td><td>const</td><td>1</td><td>100</td><td>NULL</td></tr>
<tr><td>2</td><td>DERIVED</td><td><derived3></td><td>NULL</td><td>ALL</td><td>NULL</td><td>NULL</td><td>NULL</td><td>NULL</td><td>10</td><td>100</td><td>Using filesort</td></tr>
<tr><td>3</td><td>DERIVED</td><td><subquery4></td><td>NULL</td><td>ALL</td><td>NULL</td><td>NULL</td><td>NULL</td><td>NULL</td><td>NULL</td><td>100</td><td>Using where; Using temporary; Using filesort</td></tr>
<tr><td>3</td><td>DERIVED</td><td>t</td><td>NULL</td><td>ref</td><td>PRIMARY</td><td>PRIMARY</td><td>32</td><td>&lt;subquery4&gt;.link_acno</td><td>10</td><td>100</td><td>NULL</td></tr>
<tr><td>4</td><td>MATERIALIZED</td><td>a</td><td>NULL</td><td>ALL</td><td>NULL</td><td>NULL</td><td>NULL</td><td>NULL</td><td>30</td><td>90</td><td>Using where</td></tr>
</table>

> SQL 실행결과

<table>
<tr><th>acno</th><th>seqno</th><th>reg_dttm</th><th>tx_chnl</th><th>aftr_bal</th><th>atm_cd</th><th>recv_nm</th><th>auto_cycl</th></tr>
<tr><td>333306</td><td>7</td><td>2021-01-10 15:03:37</td><td>AUT</td><td>6089</td><td></td><td></td><td>월</td></tr>
<tr><td>333308</td><td>10</td><td>2021-01-11 15:03:56</td><td>AUT</td><td>81605</td><td></td><td></td><td>월</td></tr>
<tr><td>333309</td><td>1</td><td>2021-01-06 15:00:31</td><td>ATM</td><td>66468</td><td>ATM03</td><td></td><td></td></tr>
<tr><td>333310</td><td>15</td><td>2021-01-10 15:03:13</td><td>AUT</td><td>4231</td><td></td><td></td><td>월</td></tr>
<tr><td>333311</td><td>5</td><td>2021-01-09 15:02:23</td><td>ATM</td><td>27794</td><td>ATM01</td><td></td><td></td></tr>
<tr><td>333312</td><td>2</td><td>2021-01-09 15:02:20</td><td>ATM</td><td>73857</td><td>ATM06</td><td></td><td></td></tr>
</table>


## 환경
```
Java 11
Spring Boot 2.5.1

kafka, zookeeper, control-center
mysql 8.0.22

InteliJ, DataGrip
```

## Usage
Application 실행
```
./gradlew build && java -jar build/libs/processor-1.0.0.jar
```
