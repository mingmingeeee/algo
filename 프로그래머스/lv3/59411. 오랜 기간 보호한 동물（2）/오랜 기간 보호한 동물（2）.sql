-- 코드를 입력하세요
SELECT i.ANIMAL_ID, i.NAME
FROM ANIMAL_INS i
JOIN ANIMAL_OUTS o
ON i.ANIMAL_ID = o.ANIMAL_ID
ORDER BY TIMESTAMPDIFF(SECOND, i.DATETIME, o.DATETIME) DESC
LIMIT 2;

-- 입양 간 동물 중, 보호 기간이 가장 길었던 동물 두 마리의 아이디와 이름 조회하는 SQL문
-- 보호 기간이 긴 순으로 조회 