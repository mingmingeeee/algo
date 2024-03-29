-- 코드를 입력하세요
SELECT FLAVOR
FROM (
SELECT F.TOTAL_ORDER + J.TOTAL AS TOTAL, F.FLAVOR
FROM FIRST_HALF F
JOIN (
    SELECT SUM(TOTAL_ORDER) TOTAL, FLAVOR
    FROM JULY
    GROUP BY FLAVOR
      ) J
ON J.FLAVOR = F.FLAVOR
ORDER BY TOTAL DESC
)
WHERE ROWNUM <= 3;