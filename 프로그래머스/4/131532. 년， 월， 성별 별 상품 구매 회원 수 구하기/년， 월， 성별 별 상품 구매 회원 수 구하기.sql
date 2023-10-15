-- 코드를 입력하세요
SELECT YEAR, MONTH, GENDER, COUNT(DISTINCT(INFO.USER_ID)) USERS
FROM USER_INFO INFO
JOIN (
SELECT LEFT(SALES_DATE, 4) YEAR, SUBSTR(SALES_DATE, 6, 2) - 0 MONTH, USER_ID
FROM ONLINE_SALE) SALES
ON INFO.USER_ID = SALES.USER_ID
GROUP BY YEAR, MONTH, GENDER
HAVING GENDER is not null
ORDER BY YEAR, MONTH, GENDER;

-- USER_INFO 테이블, ONLINE_SALE 테이블
-- 년, 월, 성별 별 상품 구매한 회원수 집계 SQL문 작성